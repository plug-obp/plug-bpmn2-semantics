package sandbox.visitor.arithmetic;

public class AEMod extends AEBinOp implements ArithmeticExpression{

	public AEMod(ArithmeticExpression lhs, ArithmeticExpression rhs) {
		super(lhs, rhs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(ArithmeticExpressionVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visitMod(this);
	}

}
