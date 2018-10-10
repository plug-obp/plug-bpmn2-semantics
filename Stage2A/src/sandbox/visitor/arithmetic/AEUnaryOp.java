package sandbox.visitor.arithmetic;

public abstract class AEUnaryOp implements ArithmeticExpression {

	private ArithmeticExpression argument;

	public AEUnaryOp(ArithmeticExpression argument) {
		this.argument = argument;
	}

	public ArithmeticExpression getArgument() {
		return argument;
	}

	public void setArgument(ArithmeticExpression argument) {
		this.argument = argument;
	}
	
}
