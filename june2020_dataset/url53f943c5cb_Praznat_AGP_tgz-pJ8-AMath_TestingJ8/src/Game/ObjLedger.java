package Game;

public class ObjLedger {
	private static ObjLedger[] AllObjLedgers;
	
	
	public static void newObjLedger() {
		ObjLedger[] tmp = new ObjLedger[AllObjLedgers.length + 1];
		System.arraycopy(AllObjLedgers, 0, tmp, 0, AllObjLedgers.length);
		tmp[AllObjLedgers.length] = new ObjLedger();
		AllObjLedgers = tmp;
	}
	
	
}
