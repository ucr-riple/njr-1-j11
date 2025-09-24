package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;

public class XTypeDataConstPool extends XTypeData {

	public static final XTypeDataFactory FACTORY = new XTypeDataFactory(){

		@Override
		protected XTypeData create(XRuntime runtime, XObject obj, String name, Object[] args) {
			return new XTypeDataConstPool(runtime, obj);
		}

		@Override
		protected XTypeData load(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException {
			return new XTypeDataConstPool(runtime, obj);
		}
		
	};
	
	public XTypeDataConstPool(XRuntime runtime, XObject obj) {
		super(runtime, obj, "ConstPool");
	}

	@Override
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException {
		XConstPool constPool = new XConstPoolImpl(in);
		return new XObjectDataConstPool(constPool);
	}

	@Override
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args) {
		return new XObjectDataConstPool((XConstPool)args[0]);
	}
	
}
