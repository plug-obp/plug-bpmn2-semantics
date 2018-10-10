package sandbox.visitor.asm;

public class ASMSub implements ASMOperator {

	@Override
	public void accept(ASMVisitor visitor) {
		visitor.visitSub(this);
		
	}

}
