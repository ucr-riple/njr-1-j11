package xscript;

public enum XOpcode {

	NOP(0),
	
	LINE1(0),
	LINE2(0),
	LINE4(0),
	
	LOADN(1),
	LOADB(1),
	LOADS(1),
	LOADI(1),
	LOADL(1),
	LOADF(1),
	LOADD(1),
	LOADT(1),
	
	LOAD_TRUE(1),
	LOAD_FALSE(1),
	
	LOADI_M1(1),
	LOADI_0(1),
	LOADI_1(1),
	LOADI_2(1),
	
	LOADD_M1(1),
	LOADD_0(1),
	LOADD_1(1),
	LOADD_2(1),
	
	LOADT_E(1),
	
	SWAP(0),
	DUP(1),
	
	GETTOP1(1),
	SETTOP1(-1),
	GETTOP2(1),
	SETTOP2(-1),
	
	GETBOTTOM1(1),
	SETBOTTOM1(-1),
	GETBOTTOM2(1),
	SETBOTTOM2(-1),
	
	POP(-1){
		@Override
		public int getStackChange(int i){
			return -i;
		}
	},
	POP_1(-1),
	
	RET(0),
	RETN(1),
	
	JUMP(0),
	JUMP_IF_ZERO(-1),
	JUMP_IF_NON_ZERO(-1),
	
	GET_GLOBAL(1),
	SET_GLOBAL(-1),
	DEL_GLOBAL(0),
	
	GET_CLOSURE(1),
	SET_CLOSURE(-1),
	
	GET_ATTR(0),
	SET_ATTR(-1),
	DEL_ATTR(-1),
	
	GET_INDEX(-1),
	SET_INDEX(-2),
	DEL_INDEX(-2),
	
	ADD(-1),//[..., a, b]=>[..., a+b]
	LADD(-1),//[..., a, b]=>[..., a+=b]
	
	SUB(-1),
	LSUB(-1),
	
	MUL(-1),
	LMUL(-1),
	
	DIV(-1),
	LDIV(-1),
	
	IDIV(-1),
	LIDIV(-1),
	
	MOD(-1),
	LMOD(-1),
	
	POW(-1),
	LPOW(-1),
	
	OR(-1),
	LOR(-1),
	
	AND(-1),
	LAND(-1),
	
	XOR(-1),
	LXOR(-1),
	
	SHL(-1),
	LSHL(-1),
	
	SHR(-1),
	LSHR(-1),
	
	ISHR(-1),
	LISHR(-1),
	
	POS(0),//[..., a]=>[..., +a]
	NEG(0),//[..., a]=>[..., -a]
	NOT(0),//[..., a]=>[..., !a]
	INVERT(0),//[..., a]=>[..., ~a]
	
	EQUAL(-1),//[..., a, b]=>[..., a===b]
	NOT_EQUAL(-1),//[..., a, b]=>[..., a!==b]
	SAME(-1),//[..., a, b]=>[..., a==b]
	NOT_SAME(-1),//[..., a, b]=>[..., a!=b]
	SMALLER(-1),//[..., a, b]=>[..., a==b]
	GREATER(-1),//[..., a, b]=>[..., a==b]
	SMALLER_EQUAL(-1),//[..., a, b]=>[..., a==b]
	GREATER_EQUAL(-1),//[..., a, b]=>[..., a==b]
	
	COMPARE(-1),//[..., a, b]=>[..., a<=>b]
	
	COPY(0),//[..., a]=>[..., <:a]
	INC(0),//[..., a]=>[..., ++a]
	SINC(0),//[..., a]=>[..., a++]
	DEC(0),//[..., a]=>[..., --a]
	SDEC(0),//[..., a]=>[..., a--]
	
	CALL(-1),
	CALL_LIST(-1),
	CALL_MAP(-1),
	CALL_LIST_MAP(-1),
	CALL_KW(-1),
	CALL_LIST_KW(-1),
	CALL_MAP_KW(-1),
	CALL_LIST_MAP_KW(-1),
	
	MAKE_LIST(1){
		@Override
		public int getStackChange(int i){
			return -i+1;
		}
	},
	MAKE_TUPLE(1){
		@Override
		public int getStackChange(int i){
			return -i+1;
		}
	},
	MAKE_MAP(1){
		@Override
		public int getStackChange(int i){
			return -i*2+1;
		}
	},
	MAKE_CLASS(0),
	MAKE_FUNC(0),
	MAKE_METH(-1),
	
	TYPEOF(0),
	INSTANCEOF(-1),
	ISDERIVEDOF(-1),
	
	YIELD(0),
	THROW(-1),
	
	START_TRY(0),
	END_TRY(0),
	
	IMPORT(1),
	IMPORT_SAVE(-2),

	SWITCH(-1), 
	
	END_FINALLY(-2), 
	
	SUPER(1);
	
	private int stackChange;
	
	XOpcode(int stackChange){
		this.stackChange = stackChange;
	}
	
	public int getStackChange(){
		return stackChange;
	}
	
	public int getStackChange(int i){
		return stackChange;
	}
	
}
