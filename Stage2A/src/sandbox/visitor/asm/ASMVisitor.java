package sandbox.visitor.asm;



public interface ASMVisitor {
	
	void visitSequence(ASMSequence sequence);
	void visitPush(ASMPush push);
	void visitPop(ASMPop pop);
	void visitAdd(ASMAdd add);
	void visitSub(ASMSub sub);
	
	default void visitAlternative(ASMAlternative alternative) {}

	public static class Stub implements ASMVisitor {

		@Override
		public void visitSequence(ASMSequence sequence) {}

		@Override
		public void visitPush(ASMPush push) {}

		@Override
		public void visitPop(ASMPop pop) {}

		@Override
		public void visitAdd(ASMAdd add) {}

		@Override
		public void visitSub(ASMSub sub) {}
		
	}
	
	/*void visitMult(Registre reg);
	void visitDiv(Registre reg);
	void visitDivU(Registre reg);
	void visitInc(Registre reg);
	void visitDec(Registre reg);
	void visitNeg(Registre reg);
	void visitOr(Registre reg);
	void visitAnd(Registre reg);
	void visitNot(Registre reg);
	void visitShl(Registre reg);
	void visitShr(Registre reg);
	void visitJump(Registre reg);
	void visitBrand(Registre reg);
	*/
	
	
}
