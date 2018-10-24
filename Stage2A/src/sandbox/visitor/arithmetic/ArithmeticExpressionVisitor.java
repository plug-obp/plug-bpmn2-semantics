package sandbox.visitor.arithmetic;

public interface ArithmeticExpressionVisitor {

	void visitNumber(AENumber number);
	void visitMult(AEMult mult);
	void visitAdd(AEAdd add);
	void visitNeg(AENeg neg);
	void visitDiv(AEDiv div);
	void visitMod(AEMod mod);
	void visitLeftShif(AELeftShift lshift);
	void visitRightShift(AERightShift rshift);
	
	
}

