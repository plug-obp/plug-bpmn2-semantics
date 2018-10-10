package sandbox.visitor.arithmetic;

public class AEEvaluator implements ArithmeticExpressionVisitor {

	
	private int value = 0;
	
	public int eval(ArithmeticExpression exp) {
		exp.accept(this);
		
		return getValue();
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public void visitNumber(AENumber number) {
		// TODO Auto-generated method stub
		value = number.getValue();
		
	}
	
	private int[] visitBinOp(AEBinOp binOp) {
		int[] args = new int[2];
		binOp.getLhs().accept(this);
		args[0] = value;
		binOp.getRhs().accept(this);
		args[1] = value;
		return args;
	}
	
	@Override
	public void visitMult(AEMult mult) {
	
		int[] val = visitBinOp(mult);
		value = val[0] * val[1];
	}

	@Override
	public void visitAdd(AEAdd add) {

		int[] val = visitBinOp(add);
		value = val[0] + val[1];
		
	}

	@Override
	public void visitNeg(AENeg neg) {
	
		neg.getArgument().accept(this);
		value = -value;
	}

	@Override
	public void visitDiv(AEDiv div) {

		int[] val = visitBinOp(div);
		value = val[0]/val[1];
		
	}

	@Override
	public void visitMod(AEMod mod) {
	
		int[] val = visitBinOp(mod);
		value = val[0]%val[1];
	}

	@Override
	public void visitLeftShif(AELeftShift lshift) {

		int[] val = visitBinOp(lshift);
		value = val[0]<<val[1];
		
	}

	@Override
	public void visitRightShift(AERightShift rshift) {

		int[] val = visitBinOp(rshift);
		value = val[0]>>val[1];
		
	}
	
}
