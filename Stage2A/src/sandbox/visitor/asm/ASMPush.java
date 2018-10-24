package sandbox.visitor.asm;


public class ASMPush implements ASMOperator {
	
	private final int argument ;
	
	public ASMPush(int argument) {
		this.argument= argument;
	}

	@Override
	public void accept(ASMVisitor visitor) {
		visitor.visitPush(this);
		
	}

	public int getArgument() {
		return argument;
	}

}
