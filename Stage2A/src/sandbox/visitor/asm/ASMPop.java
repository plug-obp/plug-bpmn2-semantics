package sandbox.visitor.asm;

public class ASMPop implements ASMOperator{

	@Override
	public void accept(ASMVisitor visitor) {
		visitor.visitPop(this);
		
	}

}
