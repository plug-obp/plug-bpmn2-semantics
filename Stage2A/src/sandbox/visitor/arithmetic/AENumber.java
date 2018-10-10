package sandbox.visitor.arithmetic;

public class AENumber implements ArithmeticExpression {

	private int value;
	
	
	
	public int getValue() {
		return value;
	}



	public void setValue(int value) {
		this.value = value;
	}



	public AENumber(int value) {
		this.value = value;
	}



	@Override
	public void accept(ArithmeticExpressionVisitor visitor) {
		visitor.visitNumber(this);

	}

}
