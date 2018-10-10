package sandbox.visitor.arithmetic;

public class AELeftShift extends AEBinOp implements ArithmeticExpression {

	

	public AELeftShift(ArithmeticExpression lhs, ArithmeticExpression rhs) {
		super(lhs, rhs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ArithmeticExpressionVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitLeftShif(this);
	}

}
