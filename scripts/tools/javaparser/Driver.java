package tools.javaparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
/*
 * This program just prints the list of methods in every source file
 *
 */

public class Driver{
public static void main(String[] args) throws IOException {
	String projectDir = args[0];
	String srcFilesListFile = args[1];
	boolean extraSrcAdded = false;
	String projectSrcDir;
	try (BufferedReader f = new BufferedReader(new FileReader(srcFilesListFile))){
		String line = f.readLine();
		// Check if the file names use "src" and set the
		// project source directory accordingly.
		if (line.substring(0, 4).equals("src/")) {
			extraSrcAdded = true;
		}
		if (extraSrcAdded) {
			projectSrcDir = projectDir + "/src";
		} else {
			projectSrcDir = projectDir;
		}
		// Read the rest of the files
		for (; line != null; line = f.readLine() ) {
			String filename;
			if (extraSrcAdded) {
				filename = line.substring(4);  // skip the src/
			} else {
				filename = line;
			}
			String srcFile = projectSrcDir + "/" + filename;
			ParseResult<CompilationUnit> pr = new JavaParser().parse(new File(srcFile));
			CompilationUnit cu = pr.getResult().get();
			printMethods(cu);
		}
	}
}

public static void printMethods(CompilationUnit cu) {
	for (TypeDeclaration typeDec : cu.getTypes()) {
		List<BodyDeclaration> members = typeDec.getMembers();
		if (members != null) {
			for (BodyDeclaration member : members) {
				if (member.isMethodDeclaration()) {
					MethodDeclaration md = (MethodDeclaration) member;
					System.out.println("Method name: " + md.getNameAsString());
				}
			}
		}
	}
}
}
