package sandbox.visitor.arithmetic;

public class AEAdd extends AEBinOp implements ArithmeticExpression {

	public AEAdd(ArithmeticExpression lhs, ArithmeticExpression rhs) {
		super(lhs, rhs);
	}

	@Override
	public void accept(ArithmeticExpressionVisitor visitor) {
		visitor.visitAdd(this);
	}

}
