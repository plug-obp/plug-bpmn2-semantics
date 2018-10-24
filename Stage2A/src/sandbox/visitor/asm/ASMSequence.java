package sandbox.visitor.asm;

public class ASMSequence extends ASMBlock {

	public ASMSequence(String name,ASMProgram... asmPrograms) {
		super(name, asmPrograms);
	}
	
	@Override
	public void accept(ASMVisitor visitor) {
		visitor.visitSequence(this);
	}


	
}
