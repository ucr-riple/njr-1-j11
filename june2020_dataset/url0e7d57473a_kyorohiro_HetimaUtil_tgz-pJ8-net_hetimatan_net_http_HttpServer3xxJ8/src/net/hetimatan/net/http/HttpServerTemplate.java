package net.hetimatan.net.http;

import java.io.IOException;

import net.hetimatan.io.file.KyoroFile;
import net.hetimatan.io.file.KyoroFileForFiles;
import net.hetimatan.io.filen.ByteKyoroFile;
import net.hetimatan.io.filen.CashKyoroFile;
import net.hetimatan.util.event.net.io.KyoroSocket;
import net.hetimatan.util.http.HttpRequest;
import net.hetimatan.util.io.ByteArrayBuilder;
import net.hetimatan.util.log.Log;


//
// response "this is test"
//
public class HttpServerTemplate extends HttpServer {
	public static final String TAG = "";
	public HttpServerTemplate() {
		super();
	}


	@Override
	public KyoroFile createResponse(HttpServerFront front) throws IOException {
		return createResponse(front, front.getSocket(), front.getHttpRequest());
	}

	/**
	 * this method is overrided
	 */
	public KyoroFile createResponse(HttpServerFront front, KyoroSocket socket, HttpRequest uri) throws IOException {
		
		HttpHistory.get().pushMessage(sId+"#createResponse:"+front.sId+"\n");
		KyoroFile responce = createContent(socket, uri);
		ByteArrayBuilder builder = createHeader(socket, uri, responce);
		KyoroFile[] files = new KyoroFile[2];
		files[0] = new ByteKyoroFile(builder);
		files[0].seek(0);
		files[1] = responce;
		files[1].seek(0);
		KyoroFileForFiles kfiles = new KyoroFileForFiles(files);
		kfiles.seek(0);
		return kfiles;
	}

	/**
	 * this method is overrided
	 */
	public ByteArrayBuilder createHeader(KyoroSocket socket, HttpRequest uri, KyoroFile responce) throws IOException {
		if(Log.ON){Log.v(TAG, "HttpServer#createHeader");}
		try {
			ByteArrayBuilder builder = new ByteArrayBuilder();
			builder.append(("HTTP/1.1 200 OK\r\n").getBytes());
			builder.append(("Content-Length: "+responce.length()+"\r\n").getBytes());
			builder.append(("Content-Type: text/plain\r\n").getBytes());
			builder.append(("Connection: close\r\n").getBytes());
			builder.append(("\r\n").getBytes());
			return builder;
		} finally {
			if(Log.ON){Log.v(TAG, "/HttpServer#createHeader");}
		}
	}

	/**
	 * this method is overrided
	 */
	public KyoroFile createContent(KyoroSocket socket, HttpRequest uri) throws IOException {
		if(Log.ON){Log.v(TAG, "HttpServer#createResponse");}
		try {
			return new CashKyoroFile("hello world".getBytes());
		} finally {
			if(Log.ON){Log.v(TAG, "/HttpServer#createResponse");}
		}
	}


}