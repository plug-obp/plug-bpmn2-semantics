package sandbox.visitor.asm;

public class ASMAlternative extends ASMBlock {

	public ASMAlternative(String name, ASMProgram... asmPrograms) {
		super(name, asmPrograms);
	}

	@Override
	public void accept(ASMVisitor visitor) {
		visitor.visitAlternative(this);
	}

}
