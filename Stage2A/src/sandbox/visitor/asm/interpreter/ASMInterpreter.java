package sandbox.visitor.asm.interpreter;

import sandbox.visitor.asm.*;
import java.util.*;



public class ASMInterpreter implements Interpreter<ASMProgram, ASMConfiguration> {

	ASMExecuteOperator operatorExecutor = new ASMExecuteOperator();
	ASMFlow flowBuilder = new ASMFlow();
	
	
	@Override
	public void loadModel(ASMProgram model) {
		flowBuilder.buildFlow(model);
	}

	@Override
	public List<ASMConfiguration> getInitialConfigurations() {
		List<ASMConfiguration> result = new LinkedList<>();
		for (ASMOperator initialPointer : flowBuilder.getInitialPointers()) {
			ASMConfiguration initialConfiguration = new ASMConfiguration();
			initialConfiguration.setPointer(initialPointer);
			result.add(initialConfiguration);
		}
		return result;
	}

	@Override
	public boolean hasNextConfiguration(ASMConfiguration source) {
		return (source.getPointer() != null);
	}

	@Override
	public List<ASMConfiguration> getNextConfigurations(ASMConfiguration source) {
		List<ASMConfiguration> result = new LinkedList<>();
		for (ASMFlowTransition fireableTransition : flowBuilder.getFlowTransitions(source)) {
			ASMConfiguration target = operatorExecutor.executeOperator(source);
			fireableTransition.finalizeTarget(target);
			result.add(target);
		}
		return result;
	}
	

}
