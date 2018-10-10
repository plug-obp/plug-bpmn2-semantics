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

public class ASMEvaluator implements ASMVisitor {

	
	private List<Integer> listInt = new ArrayList<>();
	private int value;
	
	public List<Integer> getListInt() {
		return listInt;
	}
	
	public List<Integer> getEvaluation (ASMSequence asm) {
		
		asm.accept(this);
		return getListInt();
		
	}
	
	@Override
	public void visitSequence(ASMSequence sequence) {
		
		for (ASMProgram asmProgram : sequence.getProgramList())
			asmProgram.accept(this);
		
	}

	@Override
	public void visitPush(ASMPush push) {
		getListInt().add(push.getArgument());
		
	}

	@Override
	public void visitPop(ASMPop pop) {
		getListInt().remove(getListInt().size()-1);
		
	}

	@Override
	public void visitAdd(ASMAdd add) {
		value = getListInt().get(getListInt().size()-1) +  getListInt().get(getListInt().size()-2);
		getListInt().set(getListInt().size()-2, value);
		visitPop(null);
		
	}

	@Override
	public void visitSub(ASMSub sub) {
		value = getListInt().get(getListInt().size()-2) -  getListInt().get(getListInt().size()-1);
		getListInt().set(getListInt().size()-2, value);
		visitPop(null);
		
	}

	

	
}
