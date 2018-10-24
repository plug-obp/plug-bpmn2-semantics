package sandbox.visitor.asm.interpreter;

import java.util.List;

public interface Interpreter<M, C> {

	void loadModel(M model);
	List<C> getInitialConfigurations();
	boolean hasNextConfiguration(C source);
	List<C> getNextConfigurations(C source);
	
}
