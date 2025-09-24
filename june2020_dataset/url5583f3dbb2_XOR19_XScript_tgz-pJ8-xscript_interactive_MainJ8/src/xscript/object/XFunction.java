package xscript.object;

import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.values.XValue;

public interface XFunction {
	
	public static final XValue NO_PUSH = new XValue() {
		
		@Override
		public Object toJava(XRuntime runtime) {
			return null;
		}
		
		@Override
		public boolean noneZero() {
			return false;
		}
		
		@Override
		public int hashCode() {
			return 0;
		}
		
		@Override
		public XValue getType(XRuntime runtime) {
			return null;
		}
		
		@Override
		public boolean equals(Object obj) {
			return false;
		}
	};

	public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable;
	
}
