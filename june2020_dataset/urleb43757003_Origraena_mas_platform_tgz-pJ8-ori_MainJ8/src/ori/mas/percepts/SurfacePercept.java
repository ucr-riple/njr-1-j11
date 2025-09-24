package ori.mas.percepts;

import ori.mas.core.Scene;
import ori.mas.core.Sensor;

import ori.ogapi.geometry.Surface;

public class SurfacePercept extends AbstractPercept {

	public SurfacePercept(Scene scene) {
		super(TYPE.POSITIVE);
		_scene = scene;
	}

	public SurfacePercept(Sensor source, Scene scene) {
		super(TYPE.POSITIVE,source);
		_scene = scene;
	}
	
	public Scene sceneView() {
		return _scene;
	}

	private final Scene _scene;

};

