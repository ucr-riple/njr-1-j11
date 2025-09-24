package net.hetimatan.tool;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.hetimatan.io.file.KyoroFile;
import net.hetimatan.io.file.KyoroFileForFiles;
import net.hetimatan.io.filen.CashKyoroFile;
import net.hetimatan.net.http.HttpHistory;
import net.hetimatan.net.http.HttpServerFront;
import net.hetimatan.net.http.HttpServerTemplate;
import net.hetimatan.util.event.net.io.KyoroSocket;
import net.hetimatan.util.http.HttpRequest;


public class SimpleHttpServer extends HttpServerTemplate {

	private static SimpleHttpServer server = null;
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("java "+SimpleHttpServer.class.getSimpleName());
			return;
		}
		server = new SimpleHttpServer(new File(args[0]));
		server.setPort(18080);
		server.startServer(null);
	}

	private File mRootDir = null;
	public SimpleHttpServer(File rootDir) {
		mRootDir = rootDir;
	}

	@Override
	public KyoroFile createResponse(HttpServerFront front, KyoroSocket socket, HttpRequest uri) throws IOException {
		HttpHistory.get().pushMessage(sId+"#createResponse:"+front.sId+","+uri.getLine().getRequestURI().getMethod()+"\n");
		File targetFile = new File(mRootDir, uri.getLine().getRequestURI().getMethod());
		if(!targetFile.exists()) {
			return createErrorResponse(uri);
		} else if(targetFile.isDirectory()) {
			return createDirResponse(uri,targetFile);			
		} else {
			return createReturnFileResponse(uri, targetFile);
		}
	}

	private KyoroFile createErrorResponse(HttpRequest uri) throws IOException {
		CashKyoroFile header = new CashKyoroFile(1024);
		header.addChunk(("HTTP/1.1 404 OK\r\n").getBytes());
		header.addChunk(("Content-Length: 0 \r\n").getBytes());
		header.addChunk(("Connection: close\r\n").getBytes());
		header.addChunk(("\r\n").getBytes());
		return header;
	}

	public KyoroFile createReturnFileResponse(HttpRequest uri, File path) throws IOException {
		CashKyoroFile header = new CashKyoroFile(1024);
		header.addChunk(("HTTP/1.1 200 OK\r\n").getBytes());
		header.addChunk(("Content-Length: "+path.length()+"\r\n").getBytes());
		header.addChunk(("Content-Type: "+getContentType(path.toString())+"\r\n").getBytes());
		header.addChunk(("Connection: close\r\n").getBytes());
		header.addChunk(("\r\n").getBytes());

		CashKyoroFile file = new CashKyoroFile(path, 1024, 3);
		KyoroFile[] files = new KyoroFile[]{header, file};
		return new KyoroFileForFiles(files);
	}

	public KyoroFile createDirResponse(HttpRequest uri, File path) throws IOException {
		CashKyoroFile content = new CashKyoroFile(1024);
		content.addChunk(("<html><head><title>"+uri.getLine().getMethod()+"</title></head><body>").getBytes());
		
		for(File f:path.listFiles()) {
			String l = "http://"+uri.getHeaderValue("Host")+f.getCanonicalPath().substring(mRootDir.getCanonicalPath().length());
			content.addChunk(("<div><a href="+l+">"+f.getName()+"</a></div>").getBytes());
		}
		content.addChunk("</body></html>".getBytes());
		CashKyoroFile header = new CashKyoroFile(1024);
		header.addChunk(("HTTP/1.1 200 OK\r\n").getBytes());
		header.addChunk(("Content-Length: "+content.length()+"\r\n").getBytes());
		header.addChunk(("Content-Type: "+getContentType(path.toString())+"\r\n").getBytes());
		header.addChunk(("Connection: close\r\n").getBytes());
		header.addChunk(("\r\n").getBytes());

		KyoroFile[] files = new KyoroFile[]{header, content};
		return new KyoroFileForFiles(files);
	}

	public static HashMap<String, String> sMimeType= new HashMap();
	static {
		sMimeType.put("html", "text/html");
		sMimeType.put("htm", "text/html");
		sMimeType.put("txt", "text/plain");
		sMimeType.put("gif", "image/gif");
		sMimeType.put("png", "image/png");
	}
	public static String getContentType(String s) {
		int pos = s.lastIndexOf('.');
		if(pos != -1) {
			String type = sMimeType.get(s.substring(pos+1));
			type = sMimeType.get(type);
			if(type != null) {
				return type;
			}
		}
		return "*/*";
	}
}
