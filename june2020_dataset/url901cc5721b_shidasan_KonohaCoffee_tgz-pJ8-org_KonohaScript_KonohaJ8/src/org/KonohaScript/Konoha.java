/**
 * 
 */
package org.KonohaScript;

/**
 * @author kiki
 *
 */

import java.util.ArrayList;
import java.util.HashMap;

import org.KonohaScript.GrammarSet.MiniKonoha;

/* konoha util */



// Runtime

// #define kContext_Debug ((khalfflag_t)(1<<0))
// #define kContext_Interactive ((khalfflag_t)(1<<1))
// #define kContext_CompileOnly ((khalfflag_t)(1<<2))
// #define kContext_Test ((khalfflag_t)(1<<3))
// #define kContext_Trace ((khalfflag_t)(1<<4))
//
// #define KonohaContext_Is(P, X) (KFlag_Is(khalfflag_t,(X)->stack->flag,
// kContext_##P))
// #define KonohaContext_Set(P, X) KFlag_Set1(khalfflag_t, (X)->stack->flag,
// kContext_##P)

class KKeyIdMap {
	int GetId(String key) {
		return 0;
	}

	void SetId(String key, int id) {

	}
}

class KParamMap {
	int GetId(int hash) {
		return 0;
	}

	void SetId(int hash, int id) {

	}
}

class KSymbolTable implements KonohaParserConst {
	ArrayList<KClass> ClassList;
	HashMap<String, KClass> ClassNameMap;

	ArrayList<KPackage> PackageList;
	KKeyIdMap PackageMap;

	ArrayList<String> FileIdList;
	HashMap<String,Integer> FileIdMap;
	
	ArrayList<String> SymbolList;
	HashMap<String,Integer> SymbolMap;
	
	ArrayList<KParam> ParamList;
	KParamMap ParamMap;
	ArrayList<KParam> SignatureList;
	KParamMap SignatureMap;

	KSymbolTable() {
		this.ClassList = new ArrayList<KClass>(64);
		this.ClassNameMap = new HashMap<String, KClass>();
		
		this.FileIdList = new ArrayList<String>(16);
		this.FileIdMap = new HashMap<String, Integer>();

		this.SymbolList = new ArrayList<String>(64);
		this.SymbolMap = new HashMap<String, Integer>();

		this.PackageList = new ArrayList<KPackage>(16);
		this.ParamList = new ArrayList<KParam>(64);
		this.SignatureList = new ArrayList<KParam>(64);
		this.PackageMap = new KKeyIdMap();
		this.ParamMap = new KParamMap();
		this.SignatureMap = new KParamMap();
	}

	void Init(Konoha kctx) {
		KPackage defaultPackage = NewPackage(kctx, "Konoha");
//		NewClass(kctx, defaultPackage, "void");
	}

	long GetFileId(String file, int linenum) {
		Integer fileid = this.FileIdMap.get(file);
		if (fileid == null) {
			int id = FileIdList.size();
			FileIdList.add(file);
			FileIdMap.put(file, new Integer(id));
			return ((long)id << 32) | linenum;
		}
		return (fileid.longValue() << 32) | linenum;
	}

	String GetFileName(long uline) {
		int id = (int)(uline >> 32);
		return FileIdList.get(id);
	}
	
	// Symbol 
	
	public final static int MaskSymbol(int n, int mask) {
		return (n << 2) | mask;
	}

	public final static int UnmaskSymbol(int id) {
		return id >> 2;
	}

	public String StringfySymbol(int Symbol) {
		String key = SymbolList.get(UnmaskSymbol(Symbol));
		if((Symbol & GetterSymbol) == GetterSymbol) {
			return "Get" + key;
		}
		if((Symbol & SetterSymbol) == SetterSymbol) {
			return "Get" + key;
		}
		if((Symbol & MetaSymbol) == MetaSymbol) {
			return "\\" + key;
		}
		return key;
	}

	public int GetSymbol(String Symbol, int DefaultValue) {
		String key = Symbol;
		int mask = 0;
		if(Symbol.length() >= 3 && Symbol.charAt(1) == 'e' && Symbol.charAt(2) == 't') {
			if(Symbol.charAt(0) == 'g' && Symbol.charAt(0) == 'G') {
				key = Symbol.substring(3);
				mask = GetterSymbol;
			}
			if(Symbol.charAt(0) == 's' && Symbol.charAt(0) == 'S') {
				key = Symbol.substring(3);
				mask = SetterSymbol;
			}
		}
		if(Symbol.startsWith("\\")) {
			mask = MetaSymbol;
		}
		Integer id = SymbolMap.get(key);
		if(id == null) {
			if(DefaultValue == AllowNewId) {
				int n = SymbolList.size();
				SymbolList.add(key);
				SymbolMap.put(key, new Integer(n));
				return MaskSymbol(n, mask);
			}
			return DefaultValue;
		}
		return MaskSymbol(id.intValue(), mask);
	}

	public static String CanonicalSymbol(String Symbol) {
		return Symbol.toLowerCase().replaceAll("_", "");
	}

	public int GetCanonicalSymbol(String Symbol, int DefaultValue) {
		return GetSymbol(CanonicalSymbol(Symbol), DefaultValue);
	}
	
	
	
	int GetSymbol(String symbol, boolean isnew) {
		Integer id = SymbolMap.get(symbol);
		if(id == null && isnew) {
			
		}
		return id;
	}

	KPackage NewPackage(Konoha kctx, String name) {
		int packageId = this.PackageList.size();
		KPackage p = new KPackage(kctx, packageId, name);
		this.PackageList.add(p);
		return p;
	}

//	KClass NewClass(Konoha kctx, KPackage p, String name) {
//		int classId = this.ClassList.size();
//		KClass c = new KClass(kctx, p, classId, name);
//		this.ClassList.add(c);
//		this.LongClassNameMap.SetId(p.PackageName + "." + name, classId);
//		return c;
//	}
}

public class Konoha implements KonohaParserConst {

	KNameSpace RootNameSpace;
	KNameSpace DefaultNameSpace;
	KSymbolTable SymbolTable;

	public final KClass VoidType;
	public final KClass BooleanType;
	
	public Konoha(MiniKonoha defaultSyntax) {
		this.SymbolTable = new KSymbolTable();
		this.SymbolTable.Init(this);
		RootNameSpace = new KNameSpace(this, null);
		VoidType = RootNameSpace.LookupTypeInfo(Void.class);
		BooleanType = RootNameSpace.LookupTypeInfo(Boolean.class);
		defaultSyntax.LoadDefaultSyntax(RootNameSpace);
		this.DefaultNameSpace = new KNameSpace(this, RootNameSpace);
	}

	final KClass LookupTypeInfo(Class<?> ClassInfo) {
		KClass TypeInfo = SymbolTable.ClassNameMap.get(ClassInfo.getName());
		if(TypeInfo == null) {
			TypeInfo = new KClass(this, ClassInfo);
			SymbolTable.ClassNameMap.put(ClassInfo.getName(), TypeInfo);
		}
		return TypeInfo;
	}
	
//	public int GetSymbol(String key, boolean isnew) {
//		return this.SymbolTable.GetSymbol(key, isnew);
//	}

	public void Define(String symbol, Object Value) {
		RootNameSpace.DefineSymbol(symbol, Value);
	}
	
	public void Eval(String text, long uline) {
		DefaultNameSpace.Eval(text, uline);
	}

	public void Load(String fileName) {
		// System.out.println("Eval: " + text);
		// DefaultNameSpace.Tokenize(text, uline);
	}

	public static void main(String[] argc) {
		MiniKonoha Grammar = new MiniKonoha();
		Konoha KonohaContext = new Konoha(Grammar);
		//konoha.Eval("int ++ fibo(int n) { return n == 1; }", 1);
		KonohaContext.Eval("a == b + C; D + e == F", 2);
		KonohaContext.Eval(
			"int fibo(int n) {\n" +
			"\tif(n < 3) return 1;\n"+
			"\treturn fibo(n-1)+fibo(n-2);\n"+
			"}", 1000);
	}
}
