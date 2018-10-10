package sandbox.visitor.arithmetic;

public class AERightShift extends AEBinOp implements ArithmeticExpression{

	

	public AERightShift(ArithmeticExpression lhs, ArithmeticExpression rhs) {
		super(lhs, rhs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ArithmeticExpressionVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitRightShift(this);
	}

}
