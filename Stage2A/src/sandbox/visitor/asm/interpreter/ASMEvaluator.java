package sandbox.visitor.asm.interpreter;
/*package sandbox.visitor.asm.interpreter.deterministic;
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
	private int size;
	private ASMConfiguration asmConfiguration2 = new ASMConfiguration();
	private List<List<String>> stringList = new ArrayList<>();
	
	public List<List<String>> getStringList() {
		return stringList;
	}
	
	public List<Integer> getListInt() {
		return listInt;
	}
	
	public ASMConfiguration init (ASMProgram model, ASMConfiguration asmConfiguration) {
		
		asmConfiguration.setPointer(0);
		this.asmConfiguration2=asmConfiguration;
		model.acccept(this);
		asmConfiguration.setOperandes(getListInt());
		this.asmConfiguration2=asmConfiguration;
//		
		return asmConfiguration;
		
	}
	
	@Override
	public void visitSequence(ASMSequence sequence) {
		
		setSize(sequence.getProgramList().size());
		sequence.getProgramList().get(asmConfiguration2.getPointer()).acccept(this);
		
		
		
	}

	@Override
	public void visitPush(ASMPush push) {
		getListInt().add(push.getArgument());
		asmConfiguration2.setOperandes(getListInt());
		asmConfiguration2.setPointer(asmConfiguration2.getPointer()+1);
	}

	@Override
	public void visitPop(ASMPop pop) {
		getListInt().remove(getListInt().size()-1);
		asmConfiguration2.setOperandes(getListInt());
		asmConfiguration2.setPointer(asmConfiguration2.getPointer()+1);
		
	}

	@Override
	public void visitAdd(ASMAdd add) {
		value = getListInt().get(getListInt().size()-1) +  getListInt().get(getListInt().size()-2);
		getListInt().set(getListInt().size()-2, value);
		getListInt().remove(getListInt().size()-1);
		asmConfiguration2.setOperandes(getListInt());
		asmConfiguration2.setPointer(asmConfiguration2.getPointer()+1);
		
	}

	@Override
	public void visitSub(ASMSub sub) {
		value = getListInt().get(getListInt().size()-2) -  getListInt().get(getListInt().size()-1);
		getListInt().set(getListInt().size()-2, value);
		getListInt().remove(getListInt().size()-1);
		asmConfiguration2.setOperandes(getListInt());
		asmConfiguration2.setPointer(asmConfiguration2.getPointer()+1);
		
		
	}

	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ASMConfiguration getAsmConfiguration2() {
		return asmConfiguration2;
	}

	public void setAsmConfiguration2(ASMConfiguration asmConfiguration2) {
		this.asmConfiguration2 = asmConfiguration2;
	}

	

	
}*/
