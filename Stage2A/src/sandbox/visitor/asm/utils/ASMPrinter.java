package sandbox.visitor.asm.utils;

import java.util.ArrayList;
import java.util.List;

import sandbox.visitor.asm.ASMAdd;
import sandbox.visitor.asm.ASMPop;
import sandbox.visitor.asm.ASMProgram;
import sandbox.visitor.asm.ASMPush;
import sandbox.visitor.asm.ASMSequence;
import sandbox.visitor.asm.ASMSub;
import sandbox.visitor.asm.ASMVisitor;


public class ASMPrinter implements ASMVisitor {

	static public final ASMPrinter INSTANCE = new ASMPrinter();
	
	private List<String> stringList = new ArrayList<>();
	
	public List<String> getStringList() {
		return stringList;
	}


	public List<String> getStringList (ASMProgram op) {
		stringList.clear();
		op.accept(this);
		
		return getStringList();
		
	}
	
	
	@Override
	public void visitSequence(ASMSequence sequence) {
		for (ASMProgram program : sequence.getProgramList()) {
			program.accept(this);
		}
		
	}


	@Override
	public void visitPush(ASMPush push) {
		getStringList().add("PUSH " + push.getArgument());
	}

	@Override
	public void visitPop(ASMPop pop) {
		getStringList().add("POP");
	}

	@Override
	public void visitAdd(ASMAdd add) {
		getStringList().add("ADD");
	}

	@Override
	public void visitSub(ASMSub sub) {
		getStringList().add("SUB");
	}


	



	

}

	