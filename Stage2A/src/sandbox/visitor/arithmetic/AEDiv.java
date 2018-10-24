package sandbox.visitor.arithmetic;

public class AEDiv extends AEBinOp implements ArithmeticExpression{

	public AEDiv(ArithmeticExpression lhs, ArithmeticExpression rhs) {
		super(lhs, rhs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ArithmeticExpressionVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitDiv(this);
	}

}
