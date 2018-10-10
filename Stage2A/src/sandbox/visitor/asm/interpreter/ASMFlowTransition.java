package sandbox.visitor.asm.interpreter;

import java.util.ArrayList;
import java.util.List;

import sandbox.visitor.asm.ASMOperator;
import sandbox.visitor.asm.utils.ASMPrinter;

public class ASMFlowTransition {
	
	private List<ASMOperator> entryList = new ArrayList<>();
	private List<ASMOperator> exitList = new ArrayList<>();
	
	public List<ASMOperator> getEntryList() {
		return entryList;
	}

	public List<ASMOperator> getExitList() {
		return exitList;
	}

	public boolean evaluateGuard(ASMConfiguration source) {
		
		// TODO ajuster la suite lorsque la configuration supporte plusieurs pointeurs
		if (exitList.size() > 1) return false;
		if (entryList.size() > 1) return false;
		if (source.getPointer() == null) return false;
		if (!(source.getPointer() instanceof ASMOperator)) return false;
		ASMOperator pointer = (ASMOperator) source.getPointer();
		if (entryList.contains(pointer)) return true;
		
		return false;
	}
	
	public void finalizeTarget(ASMConfiguration target) {
		target.setPointer(exitList.size() == 1 ? exitList.get(0) : null);
	}

	@Override
	public String toString() {
		ASMPrinter printer = ASMPrinter.INSTANCE;
		String source = entryList.isEmpty() ? "_" : printer.getStringList(entryList.get(0)).get(0);
		String target = exitList.isEmpty() ? "_" : printer.getStringList(exitList.get(0)).get(0);
		return source + " --> " + target;
	}

	
	
}
