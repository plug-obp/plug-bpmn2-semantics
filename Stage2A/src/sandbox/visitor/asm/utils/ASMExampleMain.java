package sandbox.visitor.asm.utils;

import sandbox.visitor.asm.ASMAdd;
import sandbox.visitor.asm.ASMPush;
import sandbox.visitor.asm.ASMSequence;
import sandbox.visitor.asm.ASMSub;

public class ASMExampleMain {
	
	static public void main(String[] args) {
		
		ASMPrinter printer = new ASMPrinter();
		ASMEvaluator evaluator = new ASMEvaluator();
		ASMSequence seq = new ASMSequence("program1",
				new ASMPush(21),
				new ASMPush(20),
				new ASMAdd(),
				new ASMPush(10),
				new ASMPush(9),
				new ASMSub(),
				new ASMAdd()
				//, new ASMPop()
		);
		
		for (String line : printer.getStringList(seq)) {
			System.out.println(line);
		}
		
		System.out.print("\n");
		System.out.println("RESULT : "+ evaluator.getEvaluation(seq));
		
	}

}
