package sandbox.visitor.arithmetic;

public class AEMult extends AEBinOp implements ArithmeticExpression {

	public AEMult(ArithmeticExpression lhs, ArithmeticExpression rhs) {
		super(lhs, rhs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ArithmeticExpressionVisitor visitor) {
		visitor.visitMult(this);
	}

}
