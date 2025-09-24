package net.hetimatan.net.http;

import java.io.IOException;

import net.hetimatan.io.file.KyoroFile;

public interface HttpServerListener {
	public KyoroFile onRequest(HttpServerFront front) throws IOException;
}
