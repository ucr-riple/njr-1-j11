package org.KonohaScript;

public class KPackage {
	String PackageName;
	KNameSpace PackageNameSpace;
	int kickoutFileId;

	public KPackage(Konoha kctx, int packageId, String name) {
		this.PackageName = name;
		this.PackageNameSpace = new KNameSpace(kctx, kctx.DefaultNameSpace);
	}
}