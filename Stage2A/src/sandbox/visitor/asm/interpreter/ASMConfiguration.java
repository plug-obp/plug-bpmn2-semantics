package sandbox.visitor.asm.interpreter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sandbox.visitor.asm.ASMOperator;




public class ASMConfiguration implements Cloneable {
	
	private ASMOperator pointer;
	private final List<Integer> pileOperandes;
	
	public ASMConfiguration() {
		pointer = null;
		pileOperandes = new ArrayList<>();
	}
	
	public ASMConfiguration(ASMConfiguration parent) {
		this();
		pointer = parent.getPointer();
		pileOperandes.addAll(parent.getPileOperandes());
	}
	
	
	
	@Override
	public String toString() {
		return String.format("Pointer : %s   Stack : %s", this.getPointer(),this.getPileOperandes());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + ((pileOperandes == null) ? 0 : pileOperandes.hashCode());
		result = prime * result + ((pointer == null) ? 0 : pointer.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ASMConfiguration other = (ASMConfiguration) obj;
		if (pileOperandes == null) {
			if (other.pileOperandes != null)
				return false;
		} else if (!pileOperandes.equals(other.pileOperandes))
			return false;
		if (pointer == null) {
			if (other.pointer != null)
				return false;
		} else if (!pointer.equals(other.pointer))
			return false;
		return true;
	}




	public ASMOperator getPointer() {
		return pointer;
	}

	public void setPointer(ASMOperator pointer) {
		this.pointer = pointer;
	}

	public List<Integer> getPileOperandes() {
		return pileOperandes;
	}

	public void setOperandes(List<Integer> listInteger) {
		this.pileOperandes.clear();
		Integer[] operandes = new Integer[listInteger.size()];
		listInteger.toArray(operandes);
		Collections.addAll(this.pileOperandes, operandes);
	}
	
}
