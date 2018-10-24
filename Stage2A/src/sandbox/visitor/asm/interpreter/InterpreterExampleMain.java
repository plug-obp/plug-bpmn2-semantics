package sandbox.visitor.asm.interpreter;


import java.util.Collection;
import java.util.Random;

import sandbox.visitor.asm.ASMAdd;
import sandbox.visitor.asm.ASMAlternative;
import sandbox.visitor.asm.ASMProgram;
import sandbox.visitor.asm.ASMPush;
import sandbox.visitor.asm.ASMSequence;
import sandbox.visitor.asm.ASMSub;

public class InterpreterExampleMain {
	
	static private final Random rng = new Random();
	
	static private <T> T pickOne(Collection<T> choices) {
		int max = choices.size();
		if (max == 0) return null;
		int randomIndex = rng.nextInt(max);
		int index = 0;
		for (T element : choices) {
			if (index == randomIndex) return element;
			index += 1;
		};
		throw new IndexOutOfBoundsException();
	}
	
	static public void main(String[] args) {
		
		Interpreter<ASMProgram, ASMConfiguration> interpreter = new ASMInterpreter();
		
		/*ASMSequence exampleProgram1 = new ASMSequence("program1",
				new ASMPush(21),
				new ASMPush(20),
				new ASMAdd(),
				new ASMPush(10),
				new ASMPush(9),
				new ASMSub(),
				new ASMAdd()
				) ; 
		
		ASMSequence exampleProgram2 = new ASMSequence("program2",
				new ASMPush(30),
				new ASMPush(31),
				new ASMAdd(),
				new ASMPush(11),
				new ASMSub()				
				);
		

		ASMSequence exampleProgram3 = new ASMSequence("program3",
				new ASMPush(50),
				new ASMPush(50),
				new ASMAdd(),
				new ASMPush(13),
				new ASMSub()				
				);
		
		ASMSequence exampleProgram4 = new ASMSequence("program4",
				new ASMPush(50),
				new ASMPush(50),
				new ASMAdd(),
				new ASMPush(13),
				new ASMSub()				
				);*/
		
		ASMProgram program0 = new ASMSequence("",
					new ASMPush(1),
					new ASMAlternative("",
							new ASMPush(2),
							new ASMSequence("",
									new ASMPush(3),
									new ASMPush(4),
									new ASMAdd()
									)
							),
					new ASMAlternative("",
							new ASMPush(5),
							new ASMAlternative("",
									new ASMPush(6),
									new ASMPush(7)
									)
							),
					new ASMAlternative("",
							new ASMAdd(),
							new ASMSub()
							),
					new ASMAlternative("",
							new ASMAdd(),
							new ASMSub()
							)
				);
		
		/*ASMAlternative exampleProgram1234 = new ASMAlternative("Program1234",
				exampleProgram1,
				exampleProgram2,
				exampleProgram3,
				exampleProgram4);*/
	
		interpreter.loadModel(program0);
		/*for (ASMFlowTransition transition : ((ASMInterpreter) interpreter).flowBuilder.getFlowTransitions()) {
			System.out.println(transition);
		}
		*/
		
		System.out.println();
		System.out.println("Interpretation :");
		
		ASMConfiguration currentConfiguration = pickOne(interpreter.getInitialConfigurations());
		System.out.println(currentConfiguration+"\n");
		while(interpreter.hasNextConfiguration(currentConfiguration)) {
			currentConfiguration = pickOne(interpreter.getNextConfigurations(currentConfiguration));
			System.out.println(currentConfiguration);
			
			}
	}
	
}
