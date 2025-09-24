package nz.net.initial3d.test;

import nz.net.initial3d.*;
import nz.net.initial3d.renderer.Initial3DImpl;

public class Test {

	public static void main(String[] args) {

		Initial3D i3d = new Initial3DImpl();

		Initial3D.Method flipZSign = i3d.queryMethod("flipZSign");

		flipZSign.call();

		Initial3D.Method initFog = i3d.queryMethod("initFog");

		initFog.call();

		final int FOG_A = i3d.queryEnum("I3DX_FOG_A");
		System.out.println(FOG_A);

		i3d.isEnabled(-42);

	}

}
