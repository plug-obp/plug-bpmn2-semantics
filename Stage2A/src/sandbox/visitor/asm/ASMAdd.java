package sandbox.visitor.asm;

public class ASMAdd implements ASMOperator {

	
	@Override
	public void accept(ASMVisitor visitor) {
		visitor.visitAdd(this);
		
	}
	
}
