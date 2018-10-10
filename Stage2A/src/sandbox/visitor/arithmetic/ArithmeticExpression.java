package sandbox.visitor.arithmetic;

public interface ArithmeticExpression {
	
	public void accept(ArithmeticExpressionVisitor visitor);

}
