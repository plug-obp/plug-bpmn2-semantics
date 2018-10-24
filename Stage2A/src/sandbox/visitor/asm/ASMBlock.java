package sandbox.visitor.asm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class ASMBlock implements ASMProgram {

	private final List<ASMProgram> programList = new LinkedList<>();
	private String name;

	public ASMBlock(String name,ASMProgram... asmPrograms) {
		this.setName(name);
		Collections.addAll(programList, asmPrograms);
	}

	public List<ASMProgram> getProgramList() {
		return programList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}