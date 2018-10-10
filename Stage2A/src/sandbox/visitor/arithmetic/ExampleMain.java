package sandbox.visitor.arithmetic;

public class ExampleMain {
	public static void main (String[] args) {
		ArithmeticExpression[] exps = new ArithmeticExpression[10];
		exps[0] = new AENumber(42);
		exps[1] = new AENeg(exps[0]);
		exps[2] = new AEMult(exps[0], exps[1]);
		exps[3] = new AEAdd(exps[0], exps[2]);
		exps[4] = new AEMod(exps[3], exps[0] );
		exps[5] = new AEDiv(exps[3], exps[0] );
		exps[6] = new AELeftShift(exps[0], new AENumber(3) );
		exps[7] = new AERightShift(exps[6],new AENumber(3) );
		
		AEPrinter printer = new AEPrinter();
		AEEvaluator evaluator = new AEEvaluator();
		
		for (ArithmeticExpression exp : exps) {
			if (exp != null) {
				System.out.println(printer.getPrettyString(exp) + " = " + evaluator.eval(exp) );
			}
		}
		
	}
		
}
