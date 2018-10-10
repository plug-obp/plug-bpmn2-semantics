package sandbox.visitor.asm.interpreter;

import sandbox.visitor.asm.ASMAdd;
import sandbox.visitor.asm.ASMPop;
import sandbox.visitor.asm.ASMPush;
import sandbox.visitor.asm.ASMSub;
import sandbox.visitor.asm.ASMVisitor;

public class ASMExecuteOperator extends ASMVisitor.Stub implements ASMVisitor {
	
	private ASMConfiguration target;
	
	ASMConfiguration executeOperator(ASMConfiguration source) {
		this.target= new ASMConfiguration(source);
		this.target.getPointer().accept(this);
		return target;
	}
	
	private void push(int operande) {
		this.target.getPileOperandes().add(operande);
	}
	
	private int pop() {
		return this.target.getPileOperandes().remove(this.target.getPileOperandes().size()-1);
	}

	@Override
	public void visitPush(ASMPush push) {
		push(push.getArgument());
	}

	@Override
	public void visitPop(ASMPop pop) {
		pop();
	}

	@Override
	public void visitAdd(ASMAdd add) {
		int rhs = pop();
		int lhs = pop();
		push(lhs + rhs);
	}

	@Override
	public void visitSub(ASMSub sub) {
		int rhs = pop();
		int lhs = pop();
		push(lhs - rhs);
	}
	
}
