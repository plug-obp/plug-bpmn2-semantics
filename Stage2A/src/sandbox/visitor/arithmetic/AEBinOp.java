package sandbox.visitor.arithmetic;

public abstract class AEBinOp implements ArithmeticExpression {
	
	private ArithmeticExpression lhs;
	private ArithmeticExpression rhs;
	
	public AEBinOp(ArithmeticExpression lhs, ArithmeticExpression rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public ArithmeticExpression getLhs() {
		return lhs;
	}

	public void setLhs(ArithmeticExpression lhs) {
		this.lhs = lhs;
	}

	public ArithmeticExpression getRhs() {
		return rhs;
	}

	public void setRhs(ArithmeticExpression rhs) {
		this.rhs = rhs;
	}
	
}
