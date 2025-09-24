package xx;

/**
 */
public class XpathValidator implements Compiler {

	/**
	 * Method number.
	 * @param value String
	 * @return Object
	 * @see xx.Compiler#number(String)
	 */
	@Override
	public Object number(String value) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method literal.
	 * @param value String
	 * @return Object
	 * @see xx.Compiler#literal(String)
	 */
	@Override
	public Object literal(String value) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method qname.
	 * @param prefix String
	 * @param name String
	 * @return Object
	 * @see xx.Compiler#qname(String, String)
	 */
	@Override
	public Object qname(String prefix, String name) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method sum.
	 * @param arguments Object[]
	 * @return Object
	 * @see xx.Compiler#sum(Object[])
	 */
	@Override
	public Object sum(Object[] arguments) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method minus.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#minus(Object, Object)
	 */
	@Override
	public Object minus(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method multiply.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#multiply(Object, Object)
	 */
	@Override
	public Object multiply(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method divide.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#divide(Object, Object)
	 */
	@Override
	public Object divide(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method mod.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#mod(Object, Object)
	 */
	@Override
	public Object mod(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method lessThan.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#lessThan(Object, Object)
	 */
	@Override
	public Object lessThan(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method setPredicate.
	 * @param isPredicate boolean
	 * @see xx.Compiler#setPredicate(boolean)
	 */
	@Override
	public void setPredicate(boolean isPredicate) {
		// TODO Auto-generated method stub

	}

	/**
	 * Method lessThanOrEqual.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#lessThanOrEqual(Object, Object)
	 */
	@Override
	public Object lessThanOrEqual(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method greaterThan.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#greaterThan(Object, Object)
	 */
	@Override
	public Object greaterThan(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method greaterThanOrEqual.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#greaterThanOrEqual(Object, Object)
	 */
	@Override
	public Object greaterThanOrEqual(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method equal.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#equal(Object, Object)
	 */
	@Override
	public Object equal(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method notEqual.
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#notEqual(Object, Object)
	 */
	@Override
	public Object notEqual(Object left, Object right) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method minus.
	 * @param argument Object
	 * @return Object
	 * @see xx.Compiler#minus(Object)
	 */
	@Override
	public Object minus(Object argument) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method variableReference.
	 * @param qName Object
	 * @return Object
	 * @see xx.Compiler#variableReference(Object)
	 */
	@Override
	public Object variableReference(Object qName) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method function.
	 * @param code int
	 * @param args Object[]
	 * @return Object
	 * @see xx.Compiler#function(int, Object[])
	 */
	@Override
	public Object function(int code, Object[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method function.
	 * @param name Object
	 * @param args Object[]
	 * @return Object
	 * @see xx.Compiler#function(Object, Object[])
	 */
	@Override
	public Object function(Object name, Object[] args) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method and.
	 * @param arguments Object[]
	 * @return Object
	 * @see xx.Compiler#and(Object[])
	 */
	@Override
	public Object and(Object[] arguments) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method or.
	 * @param arguments Object[]
	 * @return Object
	 * @see xx.Compiler#or(Object[])
	 */
	@Override
	public Object or(Object[] arguments) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method union.
	 * @param arguments Object[]
	 * @return Object
	 * @see xx.Compiler#union(Object[])
	 */
	@Override
	public Object union(Object[] arguments) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method nodeNameTest.
	 * @param qname Object
	 * @return Object
	 * @see xx.Compiler#nodeNameTest(Object)
	 */
	@Override
	public Object nodeNameTest(Object qname) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method nodeTypeTest.
	 * @param nodeType int
	 * @return Object
	 * @see xx.Compiler#nodeTypeTest(int)
	 */
	@Override
	public Object nodeTypeTest(int nodeType) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method processingInstructionTest.
	 * @param instruction String
	 * @return Object
	 * @see xx.Compiler#processingInstructionTest(String)
	 */
	@Override
	public Object processingInstructionTest(String instruction) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method step.
	 * @param axis int
	 * @param nodeTest Object
	 * @param predicates Object[]
	 * @return Object
	 * @see xx.Compiler#step(int, Object, Object[])
	 */
	@Override
	public Object step(int axis, Object nodeTest, Object[] predicates) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method locationPath.
	 * @param absolute boolean
	 * @param steps Object[]
	 * @return Object
	 * @see xx.Compiler#locationPath(boolean, Object[])
	 */
	@Override
	public Object locationPath(boolean absolute, Object[] steps) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Method expressionPath.
	 * @param expression Object
	 * @param predicates Object[]
	 * @param steps Object[]
	 * @return Object
	 * @see xx.Compiler#expressionPath(Object, Object[], Object[])
	 */
	@Override
	public Object expressionPath(Object expression, Object[] predicates,
			Object[] steps) {
		// TODO Auto-generated method stub
		return true;
	}

}
