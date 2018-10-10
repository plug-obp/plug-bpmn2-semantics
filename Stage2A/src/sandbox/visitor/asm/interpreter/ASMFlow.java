package sandbox.visitor.asm.interpreter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import sandbox.visitor.asm.ASMAdd;
import sandbox.visitor.asm.ASMAlternative;
import sandbox.visitor.asm.ASMOperator;
import sandbox.visitor.asm.ASMPop;
import sandbox.visitor.asm.ASMProgram;
import sandbox.visitor.asm.ASMPush;
import sandbox.visitor.asm.ASMSequence;
import sandbox.visitor.asm.ASMSub;
import sandbox.visitor.asm.ASMVisitor;
import sandbox.visitor.asm.utils.ASMPrinter;

public class ASMFlow {

	private final Visitor visitor = new Visitor();
	private final List<ASMOperator> initialPointers = new ArrayList<>();
	private final List<ASMFlowTransition> flowTransitions = new ArrayList<>();

	public List<ASMOperator> getInitialPointers() {
		return initialPointers;
	}
	
	public List<ASMFlowTransition> getFlowTransitions() {
		return flowTransitions;
	}

	public void buildFlow(ASMProgram program) {
		initialPointers.clear();
		flowTransitions.clear();
		visitor.contextStack.clear();
		program.accept(visitor);
		for (ASMOperator finalOperator : visitor.popCurrentContext()) {
			ASMFlowTransition finalTransition = new ASMFlowTransition();
			finalTransition.getEntryList().add(finalOperator);
			flowTransitions.add(finalTransition);
		}
	}
	
	public List<ASMFlowTransition> getFlowTransitions(ASMConfiguration source) {
		return flowTransitions.stream()								// Converts list to a stream
			.filter(transition -> transition.evaluateGuard(source)) // Filters transitions (evaluateGuard)
			.collect(Collectors.toList());							// Capture resulting stream into a new list
	}
	
	public class Visitor implements ASMVisitor {

		private List<List<ASMOperator>> contextStack = new LinkedList<>();
		
		private void printStack() {
			ASMPrinter printer = ASMPrinter.INSTANCE;
			StringBuilder result = new StringBuilder("<");
			for (List<ASMOperator> context : contextStack) {
				result.append(" |");
				for (ASMOperator operator : context) {
					result.append(" ").append(printer.getStringList(operator).get(0)).append(" ");
				}
				result.append("| ");
			}
			result.append(">");
			System.out.println(result.toString());
		}
		
		private List<ASMOperator> popCurrentContext() {
			if (contextStack.isEmpty()) {
				newContext();
			}
			return contextStack.remove(contextStack.size() - 1);
		}
		
		private List<ASMOperator> newContext() {
			List<ASMOperator> newContext = new LinkedList<>();
			contextStack.add(newContext);
			return newContext;
		}
		
		private void pushContext(List<ASMOperator> context) {
			contextStack.add(context);
		}
		
		@Override
		public void visitSequence(ASMSequence sequence) {
			for (ASMProgram program : sequence.getProgramList()) {
				program.accept(this);
				printStack();
			}
		}
		
		@Override
		public void visitAlternative(ASMAlternative alternative) {
			List<ASMOperator> startContext = popCurrentContext();
			List<ASMOperator> endContext = newContext();
			for (ASMProgram program : alternative.getProgramList()) {
				pushContext(startContext);
				program.accept(this);
				endContext.addAll(popCurrentContext());
				printStack();
			}
		}
		
		private void buildTransition(ASMOperator targetPointer) {
			List<ASMOperator> context = popCurrentContext();
			if (context.isEmpty()) {
				ASMFlow.this.initialPointers.add(targetPointer);
				System.out.println("Initial pointer : " + ASMPrinter.INSTANCE.getStringList(targetPointer).get(0));
			} else {
				// Join
				for (ASMOperator sourcePointer : context) {
					ASMFlowTransition transition = new ASMFlowTransition();
					transition.getEntryList().add(sourcePointer);
					transition.getExitList().add(targetPointer);
					ASMFlow.this.flowTransitions.add(transition);
					System.out.println("Built transition : " + transition);
				}
			}
		}

		@Override
		public void visitPush(ASMPush push) {
			buildTransition(push);
			newContext().add(push);
		}

		@Override
		public void visitPop(ASMPop pop) {
			buildTransition(pop);
			newContext().add(pop);
		}

		@Override
		public void visitAdd(ASMAdd add) {
			buildTransition(add);
			newContext().add(add);
		}

		@Override
		public void visitSub(ASMSub sub) {
			buildTransition(sub);
			newContext().add(sub);
		}
		
	}
	
}
