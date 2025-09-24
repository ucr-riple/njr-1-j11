package br.com.bit.ideias.reflection.scanner;

import static java.lang.String.*;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import br.com.bit.ideias.reflection.exceptions.pkgscanner.PackageScannerException;

/**
 * This class is based on Spring's Package Scanner, its difference is that it was simplified and has no dependencies with other libraries.
 * @author Leonardo Campos
 * @date 16/08/2009
 */
public class PackageScanner {
    private static final boolean FULL_MATCH = true;
	private static final boolean NOT_FULL_MATCH = false;
	private static final int CLASS_EXTENSION_LENGTH = 6;
	private static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
    private char pathSeparator = File.separatorChar;
    
    /** URL prefix for loading from the file system: "file:" */
    public static final String FILE_URL_PREFIX = "file:";

    /** URL protocol for a file in the file system: "file" */
    public static final String URL_PROTOCOL_FILE = "file";

    /** URL protocol for an entry from a jar file: "jar" */
    public static final String URL_PROTOCOL_JAR = "jar";

    /** URL protocol for an entry from a zip file: "zip" */
    public static final String URL_PROTOCOL_ZIP = "zip";

    /** URL protocol for an entry from a JBoss jar file: "vfszip" */
    public static final String URL_PROTOCOL_VFSZIP = "vfszip";

    /** URL protocol for an entry from a WebSphere jar file: "wsjar" */
    public static final String URL_PROTOCOL_WSJAR = "wsjar";

    /** URL protocol for an entry from an OC4J jar file: "code-source" */
    public static final String URL_PROTOCOL_CODE_SOURCE = "code-source";

    
    private String path;
    private String packagePath;

    private PackageScanner(String packagePath) {
    	this.packagePath = packagePath;
        this.path = CLASSPATH_ALL_URL_PREFIX + convertClassNameToResourcePath(packagePath) + "**" + File.separator + "*.class";
    }

    public static PackageScanner forPackage(String path) {
    	//We don't want to allow outside use of these special characters
    	if(path.contains("*") || path.contains("?")) throw new PackageScannerException("Characters not allowed in pattern");
    	
        return new PackageScanner(path);
    }

    public ScannerResult scan() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		try {
			Resource[] resources = getResources(path);

			for (Resource resource : resources)
				addClass(classes, resourceToClassName(resource));
		} catch (IOException e) {
			throw new PackageScannerException(e);
		}

		return new ScannerResult(classes);
	}
    
    /**
     * Retrieves all resources, regardless it is inside a jar file or in FileSystem
     * @param locationPattern
     * @return
     * @throws IOException
     */
    public Resource[] getResources(String locationPattern) throws IOException {
        String location = locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length());
        
		if (isPattern(location))
            return findPathMatchingResources(locationPattern);
        
        return findAllClassPathResources(location);
    }

    //Converts a resource into a fully qualified class name
	protected String resourceToClassName(Resource resource) {
		String pth = resource.toString().replace(File.separator, ".");
		pth = pth.substring(pth.indexOf(packagePath));
		
		//This final piece removes the ".class" part
		return pth.substring(0, pth.length() - CLASS_EXTENSION_LENGTH);
	}

	private void addClass(Set<Class<?>> classes, String className) {
		try {
			classes.add(Class.forName(className));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

    protected ClassLoader getClassLoader() {
        return this.getClass().getClassLoader();
    }
    
    public static void main(String[] args) {
        new PackageScanner("br.com.bit.ideias.reflection.test.artefacts").scan();
    }
    
    /**
     * Convert a "."-based fully qualified class name to a "/"-based resource path.
     * @param className the fully qualified class name
     * @return the corresponding resource path, pointing to the class
     */
    protected String convertClassNameToResourcePath(String className) {
        return className.replace('.', File.separatorChar);
    }
    
    /**
     * Find all resources that match the given location pattern via the
     * Ant-style PathMatcher. Supports resources in jar files and zip files
     * and in the file system.
     * @param locationPattern the location pattern to match
     * @return the result as Resource array
     * @throws IOException in case of I/O errors
     * @see #doFindPathMatchingJarResources
     * @see #doFindPathMatchingFileResources
     * @see org.springframework.util.PathMatcher
     */
    protected Resource[] findPathMatchingResources(String locationPattern) throws IOException {
        String rootDirPath = determineRootDir(locationPattern);
        String subPattern = locationPattern.substring(rootDirPath.length());
        
        Resource[] rootDirResources = getResources(rootDirPath);
        
        Set<Resource> result = new LinkedHashSet<Resource>(16);
        for (int i = 0; i < rootDirResources.length; i++) {
            if (isJarResource(rootDirResources[i]))
                result.addAll(findPathMatchingJarResources(rootDirResources[i], subPattern));
            else
                result.addAll(findPathMatchingFileResources(rootDirResources[i], subPattern));
        }
       
        return (Resource[]) result.toArray(new Resource[result.size()]);
    }
    
    /**
     * Find all resources in the file system that match the given location pattern
     * via the Ant-style PathMatcher.
     * @param rootDirResource the root directory as Resource
     * @param subPattern the sub pattern to match (below the root directory)
     * @return the Set of matching Resource instances
     * @throws IOException in case of I/O errors
     * @see #retrieveMatchingFiles
     * @see org.springframework.util.PathMatcher
     */
    protected Set<Resource> findPathMatchingFileResources(Resource rootDirResource, String subPattern) throws IOException {
        return findMatchingFileSystemResources(rootDirResource.getFile().getAbsoluteFile(), subPattern);
    }
    
    /**
     * Find all resources in the file system that match the given location pattern
     * via the Ant-style PathMatcher.
     * @param rootDir the root directory in the file system
     * @param subPattern the sub pattern to match (below the root directory)
     * @return the Set of matching Resource instances
     * @throws IOException in case of I/O errors
     * @see #retrieveMatchingFiles
     */
    protected Set<Resource> findMatchingFileSystemResources(File rootDir, String subPattern) throws IOException {
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir, subPattern);
        
        Set<Resource> result = new LinkedHashSet<Resource>(matchingFiles.size());
        for (Iterator<File> it = matchingFiles.iterator(); it.hasNext();) {
            File file = (File) it.next();
            result.add(new FileSystemResource(file));
        }
        
        return result;
    }
    
    /**
     * Retrieve files that match the given path pattern,
     * checking the given directory and its subdirectories.
     * @param rootDir the directory to start from
     * @param pattern the pattern to match against,
     * relative to the root directory
     * @return the Set of matching File instances
     * @throws IOException if directory contents could not be retrieved
     */
    protected Set<File> retrieveMatchingFiles(File rootDir, String pattern) throws IOException {
        if (!rootDir.isDirectory()) throw new IllegalArgumentException(format("Resource path [%s] does not denote a directory", rootDir));
        
        Set<File> result = new LinkedHashSet<File>(8);
        
        retrieveMatchingFiles(fullPattern(rootDir, pattern), rootDir, result);
        
        return result;
    }

    /**
     * It concatenates rootDir + pattern (it guarantees that a file separator is between them)
     * @param rootDir
     * @param pattern
     * @return
     */
	protected String fullPattern(File rootDir, String pattern) {
		return rootDir.getAbsolutePath() + (pattern.startsWith(File.separator)?"":File.separator) + pattern;
	}
    
    /**
     * Recursively retrieve files that match the given pattern,
     * adding them to the given result list.
     * @param fullPattern the pattern to match against,
     * with preprended root directory path
     * @param dir the current directory
     * @param result the Set of matching File instances to add to
     * @throws IOException if directory contents could not be retrieved
     */
    protected void retrieveMatchingFiles(String fullPattern, File dir, Set<File> result) throws IOException {
        File[] dirContents = dir.listFiles();
        
        if (dirContents == null) throw new IOException(format("Could not retrieve contents of directory [%s]", dir.getAbsolutePath()));
        
        for (File content : dirContents) {
        	String currPath = content.getAbsolutePath();
        	
            if (content.isDirectory() && match(fullPattern, currPath + File.separator, NOT_FULL_MATCH))
                retrieveMatchingFiles(fullPattern, content, result);
            
            if (match(fullPattern, currPath, FULL_MATCH))
                result.add(content);
		}
    }
    
    /**
     * Actually match the given <code>path</code> against the given <code>pattern</code>.
     * @param pattern the pattern to match against
     * @param path the path String to test
     * @param fullMatch whether a full pattern match is required
     * (else a pattern match as far as the given base path goes is sufficient)
     * @return <code>true</code> if the supplied <code>path</code> matched,
     * <code>false</code> if it didn't
     */
    protected boolean match(String pattern, String path, boolean fullMatch) {
        String separator = "" + this.pathSeparator;
        if (path.startsWith(separator) != pattern.startsWith(separator)) return false;

        String[] pattDirs = null;
        String[] pathDirs = null;
        
        String osSpecificSeparatorRegex = isWindows()?"[\\\\]":"/";

        pattDirs = pattern.split(osSpecificSeparatorRegex);
        pathDirs = path.split(osSpecificSeparatorRegex);

        int pattIdxStart = 0;
        int pattIdxEnd = pattDirs.length - 1;
        
        int pathIdxStart = 0;
        int pathIdxEnd = pathDirs.length - 1;

        // Match all elements up to the first **
        while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            String patDir = pattDirs[pattIdxStart];
            if ("**".equals(patDir)) break;
            
            if (!matchStrings(patDir, pathDirs[pathIdxStart])) return false;
            
            pattIdxStart++;
            pathIdxStart++;
        }

        if (pathIdxStart > pathIdxEnd) {
            // Path is exhausted, only match if rest of pattern is * or **'s
            if (pattIdxStart > pattIdxEnd) return (pattern.endsWith(separator) ?path.endsWith(separator) : !path.endsWith(separator));
            
            if (!fullMatch) return true;
            
            if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(separator)) return true;
            
            for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
                if (!pattDirs[i].equals("**")) return false;
            }
            
            return true;
        } else if (pattIdxStart > pattIdxEnd) {
            // String not exhausted, but pattern is. Failure.
            return false;
        } else if (!fullMatch && "**".equals(pattDirs[pattIdxStart])) {
            // Path start definitely matches due to "**" part in pattern.
            return true;
        }

        // up to last '**'
        while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            String patDir = pattDirs[pattIdxEnd];
            if (patDir.equals("**")) break;
            
            if (!matchStrings(patDir, pathDirs[pathIdxEnd])) return false;
            
            pattIdxEnd--;
            pathIdxEnd--;
        }
        
        if (pathIdxStart > pathIdxEnd) {
            // String is exhausted
            for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
                if (!pattDirs[i].equals("**")) return false;
            }
            
            return true;
        }

        while (pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            int patIdxTmp = -1;
            for (int i = pattIdxStart + 1; i <= pattIdxEnd; i++) {
                if (pattDirs[i].equals("**")) {
                    patIdxTmp = i;
                    break;
                }
            }
            if (patIdxTmp == pattIdxStart + 1) {
                // '**/**' situation, so skip one
                pattIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - pattIdxStart - 1);
            int strLength = (pathIdxEnd - pathIdxStart + 1);
            int foundIdx = -1;

            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    String subPat = (String) pattDirs[pattIdxStart + j + 1];
                    String subStr = (String) pathDirs[pathIdxStart + i + j];
                    
                    if (!matchStrings(subPat, subStr)) continue strLoop;
                }
                
                foundIdx = pathIdxStart + i;
                break;
            }

            if (foundIdx == -1) return false;

            pattIdxStart = patIdxTmp;
            pathIdxStart = foundIdx + patLength;
        }

        for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
            if (!pattDirs[i].equals("**")) return false;
        }
        
        return true;
    }

    /**
     * Find all resources in jar files that match the given location pattern
     * via the Ant-style PathMatcher.
     * @param rootDirResource the root directory as Resource
     * @param subPattern the sub pattern to match (below the root directory)
     * @return the Set of matching Resource instances
     * @throws IOException in case of I/O errors
     * @see java.net.JarURLConnection
     * @see org.springframework.util.PathMatcher
     */
    protected Set<Resource> findPathMatchingJarResources(Resource rootDirResource, String subPattern) throws IOException {
        URLConnection con = rootDirResource.getURL().openConnection();
        JarFile jarFile = null;
        String jarFileUrl = null;
        String rootEntryPath = null;
        boolean newJarFile = false;

        if (con instanceof JarURLConnection) {
            // Should usually be the case for traditional JAR files.
            JarURLConnection jarCon = (JarURLConnection) con;
            jarCon.setUseCaches(false);
            jarFile = jarCon.getJarFile();
            jarFileUrl = jarCon.getJarFileURL().toExternalForm();
            JarEntry jarEntry = jarCon.getJarEntry();
            rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
        }
        else {
            // No JarURLConnection -> need to resort to URL file parsing.
            // We'll assume URLs of the format "jar:path!/entry", with the protocol
            // being arbitrary as long as following the entry format.
            // We'll also handle paths with and without leading "file:" prefix.
            String urlFile = rootDirResource.getURL().getFile();
            int separatorIndex = urlFile.indexOf(getJarUrlSeparator());
            if (separatorIndex != -1) {
                jarFileUrl = urlFile.substring(0, separatorIndex);
                rootEntryPath = urlFile.substring(separatorIndex + getJarUrlSeparator().length());
                jarFile = getJarFile(jarFileUrl);
            }
            else {
                jarFile = new JarFile(urlFile);
                jarFileUrl = urlFile;
                rootEntryPath = "";
            }
            newJarFile = true;
        }

        try {
            if (!"".equals(rootEntryPath) && !rootEntryPath.endsWith(File.separator)) {
                // Root entry path must end with slash to allow for proper matching.
                // The Sun JRE does not return a slash here, but BEA JRockit does.
                rootEntryPath = rootEntryPath + File.separator;
            }
            Set<Resource> result = new LinkedHashSet<Resource>(8);
            for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
                JarEntry entry = (JarEntry) entries.nextElement();
                String entryPath = entry.getName();
                if (entryPath.startsWith(rootEntryPath)) {
                    String relativePath = entryPath.substring(rootEntryPath.length());
                    if (match(subPattern, relativePath, true)) {
                        result.add(rootDirResource.createRelative(relativePath));
                    }
                }
            }
            return result;
        }
        finally {
            // Close jar file, but only if freshly obtained -
            // not from JarURLConnection, which might cache the file reference.
            if (newJarFile) {
                jarFile.close();
            }
        }
    }
    
    /**
     * Determine the root directory for the given location.
     * <p>Used for determining the starting point for file matching,
     * resolving the root directory location to a <code>java.io.File</code>
     * and passing it into <code>retrieveMatchingFiles</code>, with the
     * remainder of the location as pattern.
     * <p>Will return "/WEB-INF" for the pattern "/WEB-INF/*.xml",
     * for example.
     * @param location the location to check
     * @return the part of the location that denotes the root directory
     * @see #retrieveMatchingFiles
     */
    protected String determineRootDir(String location) {
        int prefixEnd = location.indexOf(":") + 1;
        int rootDirEnd = location.length();
        
        while (rootDirEnd > prefixEnd && isPattern(location.substring(prefixEnd, rootDirEnd)))
            rootDirEnd = location.lastIndexOf(File.separatorChar, rootDirEnd - 2) + 1;
        
        if (rootDirEnd == 0)
            rootDirEnd = prefixEnd;
        
        return location.substring(0, rootDirEnd);
    }
    
    /**
     * Find all class location resources with the given location via the ClassLoader.
     * @param location the absolute path within the classpath
     * @return the result as Resource array
     * @throws IOException in case of I/O errors
     * @see java.lang.ClassLoader#getResources
     * @see #convertClassLoaderURL
     */
    protected Resource[] findAllClassPathResources(String location) throws IOException {
        String path = location;
        if (path.startsWith(File.separator)) {
            path = path.substring(1);
        }
        
        Enumeration<URL> resourceUrls = getClassLoader().getResources(path);
        Set<Resource> result = new LinkedHashSet<Resource>(16);
        while (resourceUrls.hasMoreElements()) {
            URL url = (URL) resourceUrls.nextElement();
            result.add(convertClassLoaderURL(url));
        }
        
        return (Resource[]) result.toArray(new Resource[result.size()]);
    }
    
    /**
     * Verifies if the path has a * or a ?, meaning it is a patter
     */
    public boolean isPattern(String path) {
        return (path.indexOf('*') != -1 || path.indexOf('?') != -1);
    }
    
    /**
     * Convert the given URL as returned from the ClassLoader into a Resource object.
     * <p>The default implementation simply creates a UrlResource instance.
     * @param url a URL as returned from the ClassLoader
     * @return the corresponding Resource object
     * @see java.lang.ClassLoader#getResources
     * @see org.springframework.core.io.Resource
     */
    protected Resource convertClassLoaderURL(URL url) {
        return new UrlResource(url);
    }

    /**
     * Return whether the given resource handle indicates a jar resource
     * that the <code>doFindPathMatchingJarResources</code> method can handle.
     * <p>The default implementation checks against the URL protocols
     * "jar", "zip" and "wsjar" (the latter are used by BEA WebLogic Server
     * and IBM WebSphere, respectively, but can be treated like jar files).
     * @param resource the resource handle to check
     * (usually the root directory to start path matching from)
     * @see #doFindPathMatchingJarResources
     * @see org.springframework.util.ResourceUtils#isJarURL
     */
    protected boolean isJarResource(Resource resource) throws IOException {
        URL url = resource.getURL();
        String protocol = url.getProtocol();
        return (URL_PROTOCOL_JAR.equals(protocol) ||
                URL_PROTOCOL_ZIP.equals(protocol) ||
                URL_PROTOCOL_VFSZIP.equals(protocol) ||
                URL_PROTOCOL_WSJAR.equals(protocol) ||
                (URL_PROTOCOL_CODE_SOURCE.equals(protocol) && url.getPath().indexOf(getJarUrlSeparator()) != -1));
    }
    
    /**
     * Tests whether or not a string matches against a pattern.
     * The pattern may contain two special characters:<br>
     * '*' means zero or more characters<br>
     * '?' means one and only one character
     * @param pattern pattern to match against.
     * Must not be <code>null</code>.
     * @param toBeMatched string which must be matched against the pattern.
     * Must not be <code>null</code>.
     * @return <code>true</code> if the string matches against the
     * pattern, or <code>false</code> otherwise.
     */
    private boolean matchStrings(String pattern, String toBeMatched) {
        char[] patternChars = pattern.toCharArray();
        char[] toBeMatchedChars = toBeMatched.toCharArray();
        
        int patternStart = 0;
        int patternEnd = patternChars.length - 1;
        
        int toBeMatchedStart = 0;
        int toBeMatchedEnd = toBeMatchedChars.length - 1;
        
        char ch;

        boolean patternContainsStar = pattern.indexOf("*") > -1;

        if (!patternContainsStar) {
            if (patternEnd != toBeMatchedEnd) return false;
            
            for (int i = 0; i <= patternEnd; i++) {
                ch = patternChars[i];
                char toBeMatchedChar = toBeMatchedChars[i];
                
				if (!matchChar(ch, toBeMatchedChar)) return false;
            }
            
            return true;
        }

        if (patternEnd == 0) return true; // Pattern contains only '*', which matches anything

        // Process characters before first star
        while ((ch = patternChars[patternStart]) != '*' && toBeMatchedStart <= toBeMatchedEnd) {
            char toBeMatchedChar = toBeMatchedChars[toBeMatchedStart];
            
			if (!matchChar(ch, toBeMatchedChar)) return false;
            
            patternStart++;
            toBeMatchedStart++;
        }
        
        if (toBeMatchedStart > toBeMatchedEnd) {
            // All characters in the string are used. Check if only '*'s are
            // left in the pattern. If so, we succeeded. Otherwise failure.
            for (int i = patternStart; i <= patternEnd; i++) {
                if (patternChars[i] != '*') {
                    return false;
                }
            }
            return true;
        }

        // Process characters after last star
        while ((ch = patternChars[patternEnd]) != '*' && toBeMatchedStart <= toBeMatchedEnd) {
            char toBeMatchedChar = toBeMatchedChars[toBeMatchedEnd];
			if (!matchChar(ch, toBeMatchedChar)) return false;// Character mismatch
            
            patternEnd--;
            toBeMatchedEnd--;
        }
        
        if (toBeMatchedStart > toBeMatchedEnd) {
            // All characters in the string are used. Check if only '*'s are
            // left in the pattern. If so, we succeeded. Otherwise failure.
            for (int i = patternStart; i <= patternEnd; i++) {
                if (patternChars[i] != '*') return false;
            }
            
            return true;
        }

        // process pattern between stars. patternStart and patternEnd point
        // always to a '*'.
        while (patternStart != patternEnd && toBeMatchedStart <= toBeMatchedEnd) {
            int patIdxTmp = -1;
            for (int i = patternStart + 1; i <= patternEnd; i++) {
                if (patternChars[i] == '*') {
                    patIdxTmp = i;
                    break;
                }
            }
            if (patIdxTmp == patternStart + 1) {
                // Two stars next to each other, skip the first one.
                patternStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - patternStart - 1);
            int strLength = (toBeMatchedEnd - toBeMatchedStart + 1);
            int foundIdx = -1;
            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    ch = patternChars[patternStart + j + 1];
                    if (ch != '?') {
                        if (ch != toBeMatchedChars[toBeMatchedStart + i + j]) {
                            continue strLoop;
                        }
                    }
                }

                foundIdx = toBeMatchedStart + i;
                break;
            }

            if (foundIdx == -1) {
                return false;
            }

            patternStart = patIdxTmp;
            toBeMatchedStart = foundIdx + patLength;
        }

        // All characters in the string are used. Check if only '*'s are left
        // in the pattern. If so, we succeeded. Otherwise failure.
        for (int i = patternStart; i <= patternEnd; i++) {
            if (patternChars[i] != '*') {
                return false;
            }
        }

        return true;
    }

	private boolean matchChar(char ch, char toBeMatchedChar) {
		return ch == '?' || ch == toBeMatchedChar;
	}
    
    /**
     * Resolve the given jar file URL into a JarFile object.
     */
    protected JarFile getJarFile(String jarFileUrl) throws IOException {
        if (jarFileUrl.startsWith(FILE_URL_PREFIX)) {
            try {
                return new JarFile(toURI(jarFileUrl).getSchemeSpecificPart());
            }
            catch (URISyntaxException ex) {
                // Fallback for URLs that are not valid URIs (should hardly ever happen).
                return new JarFile(jarFileUrl.substring(FILE_URL_PREFIX.length()));
            }
        }
        else {
            return new JarFile(jarFileUrl);
        }
    }
    
    /**
     * Create a URI instance for the given URL,
     * replacing spaces with "%20" quotes first.
     * <p>Furthermore, this method works on JDK 1.4 as well,
     * in contrast to the <code>URL.toURI()</code> method.
     * @param url the URL to convert into a URI instance
     * @return the URI instance
     * @throws URISyntaxException if the URL wasn't a valid URI
     * @see java.net.URL#toURI()
     */
    public static URI toURI(URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    /**
     * Create a URI instance for the given location String,
     * replacing spaces with "%20" quotes first.
     * @param location the location String to convert into a URI instance
     * @return the URI instance
     * @throws URISyntaxException if the location wasn't a valid URI
     */
    public static URI toURI(String location) throws URISyntaxException {
        return new URI(location.replaceAll(" ", "%20"));
    }
    
    private String getJarUrlSeparator() {
    	final String JAR_URL_SEPARATOR = "!".concat(File.separator);
    	
    	return JAR_URL_SEPARATOR;
    }
    
    private boolean isWindows() {
		return this.pathSeparator == '\\';
	}
}
