package xscript.compiler;
import static xscript.compiler.XOperator.Type.INFIX;
import static xscript.compiler.XOperator.Type.PREFIX;
import static xscript.compiler.XOperator.Type.SUFFIX;
import xscript.XOpcode;

public enum XOperator {

	NONE(null, null, -1, false, XOpcode.NOP),
	
	ADD("+", INFIX, 11, XOpcode.ADD) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)+XWrapper.asLong(right);
			}else if(XWrapper.isNumber(left) && XWrapper.isNumber(right)){
				return XWrapper.asNumber(left)+XWrapper.asNumber(right);
			}else if(XWrapper.isString(left) || XWrapper.isString(right)){
				return XWrapper.asString(left)+XWrapper.asString(right);
			}
			return super.calc(left, right);
		}
	},
	SUB("-", INFIX, 11, XOpcode.SUB) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)-XWrapper.asLong(right);
			}else if(XWrapper.isNumber(left) && XWrapper.isNumber(right)){
				return XWrapper.asNumber(left)-XWrapper.asNumber(right);
			}
			return super.calc(left, right);
		}
	},
	MUL("*", INFIX, 12, XOpcode.MUL) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)*XWrapper.asLong(right);
			}else if(XWrapper.isNumber(left) && XWrapper.isNumber(right)){
				return XWrapper.asNumber(left)*XWrapper.asNumber(right);
			}
			return super.calc(left, right);
		}
	},
	DIV("/", INFIX, 12, XOpcode.DIV) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isNumber(left) && XWrapper.isNumber(right)){
				return XWrapper.asNumber(left)/XWrapper.asNumber(right);
			}
			return super.calc(left, right);
		}
	},
	IDIV("\\", INFIX, 12, XOpcode.IDIV) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)/XWrapper.asLong(right);
			}else if(XWrapper.isNumber(left) && XWrapper.isNumber(right)){
				return XWrapper.asNumber(left)/XWrapper.asNumber(right);
			}
			return super.calc(left, right);
		}
	},
	MOD("%", INFIX, 12, XOpcode.MOD) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)%XWrapper.asLong(right);
			}else if(XWrapper.isNumber(left) && XWrapper.isNumber(right)){
				return XWrapper.asNumber(left)%XWrapper.asNumber(right);
			}
			return super.calc(left, right);
		}
	},
	POW("**", INFIX, 13, XOpcode.POW) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return (long)Math.pow(XWrapper.asLong(left), XWrapper.asLong(right));
			}else if(XWrapper.isNumber(left) && XWrapper.isNumber(right)){
				return Math.pow(XWrapper.asNumber(left), XWrapper.asNumber(right));
			}
			return super.calc(left, right);
		}
	},
	SHR(">>", INFIX, 10, XOpcode.SHR) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)>>XWrapper.asLong(right);
			}
			return super.calc(left, right);
		}
	},
	ISHR(">>>", INFIX, 10, XOpcode.ISHR) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)>>>XWrapper.asLong(right);
			}
			return super.calc(left, right);
		}
	},
	SHL("<<", INFIX, 10, XOpcode.SHL) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)<<XWrapper.asLong(right);
			}
			return super.calc(left, right);
		}
	},
	
	POS("+", PREFIX, -1, XOpcode.POS) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isNumber(left)){
				return left;
			}
			return super.calc(left, right);
		}
	},
	NEG("-", PREFIX, -1, XOpcode.NEG) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isLong(left)){
				return -XWrapper.asLong(left);
			}else if(XWrapper.isNumber(left)){
				return -XWrapper.asNumber(left);
			}
			return super.calc(left, right);
		}
	},
	
	BOR("|", INFIX, 5, XOpcode.OR) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isBool(left) && XWrapper.isBool(right)){
				return XWrapper.asBool(left)||XWrapper.asBool(right);
			}else if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)|XWrapper.asLong(right);
			}
			return super.calc(left, right);
		}
	},
	BAND("&", INFIX, 7, XOpcode.AND) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isBool(left) && XWrapper.isBool(right)){
				return XWrapper.asBool(left)&&XWrapper.asBool(right);
			}else if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)&XWrapper.asLong(right);
			}
			return super.calc(left, right);
		}
	},
	XOR("^", INFIX, 6, XOpcode.XOR) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isBool(left) && XWrapper.isBool(right)){
				return XWrapper.asBool(left)!=XWrapper.asBool(right);
			}else if(XWrapper.isLong(left)&&XWrapper.isLong(right)){
				return XWrapper.asLong(left)^XWrapper.asLong(right);
			}
			return super.calc(left, right);
		}
	},
	OR("||", INFIX, 3, XOpcode.OR) {
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asBool(left)||XWrapper.asBool(right);
		}
	},
	AND("&&", INFIX, 4, XOpcode.AND) {
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asBool(left)&&XWrapper.asBool(right);
		}
	},
	
	NOT("!", PREFIX, -1, XOpcode.NOT) {
		@Override
		public Object calc(Object left, Object right) {
			return !XWrapper.asBool(left);
		}
	},
	BNOT("~", PREFIX, -1, XOpcode.INVERT) {
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isBool(left)){
				return !XWrapper.asBool(left);
			}else if(XWrapper.isLong(left)){
				return ~XWrapper.asLong(left);
			}
			return super.calc(left, right);
		}
	},
	
	EQ("==", INFIX, 8, false, XOpcode.SAME) {
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asLong(COMP.calc(left, right))==0;
		}
	},
	REQ("===", INFIX, 8, XOpcode.EQUAL) {
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asLong(COMP.calc(left, right))==0;
		}
	},
	NEQ("!=", INFIX, 8, false, XOpcode.NOT_SAME) {
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asLong(COMP.calc(left, right))!=0;
		}
	},
	RNEQ("!==", INFIX, 8, XOpcode.NOT_EQUAL) {
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asLong(COMP.calc(left, right))!=0;
		}
	},
	BIG(">", INFIX, 9, XOpcode.GREATER){
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asLong(COMP.calc(left, right))>0;
		}
	},
	BEQ(">=", INFIX, 9, XOpcode.GREATER_EQUAL){
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asLong(COMP.calc(left, right))>=0;
		}
	},
	SMA("<", INFIX, 9, XOpcode.SMALLER){
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asLong(COMP.calc(left, right))<0;
		}
	},
	SEQ("<=", INFIX, 9, XOpcode.SMALLER_EQUAL){
		@Override
		public Object calc(Object left, Object right) {
			return XWrapper.asLong(COMP.calc(left, right))<=0;
		}
	},
	COMP("<=>", INFIX, 8, XOpcode.COMPARE){
		@Override
		public Object calc(Object left, Object right) {
			if((XWrapper.isLong(left) || XWrapper.isBool(left)) && (XWrapper.isLong(right) || XWrapper.isBool(right))){
				return XWrapper.asLong(left)-XWrapper.asLong(right);
			}else if(XWrapper.isNumber(left) && XWrapper.isNumber(right)){
				double l = XWrapper.asNumber(left);
				double r = XWrapper.asNumber(right);
				return l>r?1:l<r?-1:0;
			}else if(XWrapper.isString(left) && XWrapper.isString(right)){
				return XWrapper.asString(left).compareTo(XWrapper.asString(right));
			}
			return super.calc(left, right);
		}
	},
	
	LET("=", INFIX, 0, false, XOpcode.NOP),
	LETADD("+=", INFIX, 0, XOpcode.LADD),
	LETSUB("-=", INFIX, 0, XOpcode.LSUB),
	LETMUL("*=", INFIX, 0, XOpcode.LMUL),
	LETPOW("**=", INFIX, 0, XOpcode.LPOW),
	LETDIV("/=", INFIX, 0, XOpcode.LDIV),
	LETIDIV("\\=", INFIX, 0, XOpcode.LIDIV),
	LETMOD("%=", INFIX, 0, XOpcode.LMOD),
	LETOR("|=", INFIX, 0, XOpcode.LOR),
	LETAND("&=", INFIX, 0, XOpcode.LAND),
	LETXOR("^=", INFIX, 0, XOpcode.LXOR),
	LETSHR(">>=", INFIX, 0, XOpcode.LSHR),
	LETSHL("<<=", INFIX, 0, XOpcode.LSHL),
	LETISHR(">>>=", INFIX, 0, XOpcode.LISHR),
	COPY("<:", INFIX, 0, false, XOpcode.NOP),
	
	INC("++", PREFIX, -1, XOpcode.INC){
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isNumber(left)){
				return XWrapper.asLong(left)+1;
			}
			return super.calc(left, right);
		}
	},
	DEC("--", PREFIX, -1, XOpcode.DEC){
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isNumber(left)){
				return XWrapper.asLong(left)-1;
			}
			return super.calc(left, right);
		}
	},
	INCS("++", SUFFIX, -1, XOpcode.SINC){
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isNumber(left)){
				return left;
			}
			return super.calc(left, right);
		}
	},
	DECS("--", SUFFIX, -1, XOpcode.SDEC){
		@Override
		public Object calc(Object left, Object right) {
			if(XWrapper.isNumber(left)){
				return left;
			}
			return super.calc(left, right);
		}
	},
	COPYS("<:", PREFIX, -1, XOpcode.COPY){
		@Override
		public Object calc(Object left, Object right) {
			return left;
		}
	},
	
	ELEMENT(".", INFIX, 14, false, XOpcode.NOP),
	
	IF("?", INFIX, 2, false, XOpcode.NOP),
	
	COMMA(",", INFIX, 1, false, XOpcode.NOP),
	
	UNPACKLIST("*", PREFIX, -1, false, XOpcode.NOP),
	UNPACKMAP("**", PREFIX, -1, false, XOpcode.NOP),
	
	;
	
	public final static boolean[] L2R = {false, false, false, true, true, true, true, true, true, true, true, true, true, true, true};
	
	public final String op;
	public final Type type;
	public final int priority;
	public final boolean canBeOverwritten;
	public final XOpcode opcode;
	
	XOperator(String op, Type type, int priority, XOpcode opcode){
		this.op = op;
		this.type = type;
		this.priority = priority;
		canBeOverwritten = true;
		this.opcode = opcode;
	}
	
	XOperator(String op, Type type, int priority, boolean canBeOverwritten, XOpcode opcode){
		this.op = op;
		this.type = type;
		this.priority = priority;
		this.canBeOverwritten = canBeOverwritten;
		this.opcode = opcode;
	}
	
	public Object calc(Object left, Object right){
		throw new UnsupportedOperationException();
	}
	
	public static enum Type{
		PREFIX, INFIX, SUFFIX
	}
	
}
