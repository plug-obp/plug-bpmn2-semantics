package sandbox.visitor.arithmetic;

public class AENeg extends AEUnaryOp implements ArithmeticExpression {

	public AENeg(ArithmeticExpression argument) {
		super(argument);
	}

	@Override
	public void accept(ArithmeticExpressionVisitor visitor) {
	
		visitor.visitNeg(this);
	}

}
