package sandbox.visitor.arithmetic;



public class AEPrinter implements ArithmeticExpressionVisitor {

	private String result = "";

	
	public String getPrettyString (ArithmeticExpression exp) {
		// Lancer la visite
		exp.accept(this);
		// Récupérer et retourner le résultat
		return getResult();
	}
	
	public String getResult() {
		return result;
	}

	@Override
	public void visitNumber(AENumber number) {
		result = Integer.toString(number.getValue());
		
	}
	
	private String[] visitBinOp(AEBinOp binOp) {
		String[] args = new String[2];
		binOp.getLhs().accept(this);
		args[0] = result;
		binOp.getRhs().accept(this);
		args[1] = result;
		return args;
	}
	
	@Override
	public void visitMult(AEMult mult) {
		String[] args = visitBinOp(mult);
		result = "(" + args[0] + " x " + args[1] + ")";
	}

	@Override
	public void visitAdd(AEAdd add) {
		String[] args = visitBinOp(add);
		result = "(" + args[0] + " + " + args[1] + ")";
		
	}

	@Override
	public void visitNeg(AENeg neg) {
		neg.getArgument().accept(this);
		result = "-(" + result + ")";
		
	}

	@Override
	public void visitDiv(AEDiv div) {
		// TODO Auto-generated method stub
		String[] args = visitBinOp(div);
		result = "(" + args[0] + " / " + args[1] +")";
		
	}

	@Override
	public void visitMod(AEMod mod) {
		// TODO Auto-generated method stub
		String[] args = visitBinOp(mod);
		result = "(" + args[0] + " % " + args[1] +")";
		
	}

	@Override
	public void visitLeftShif(AELeftShift lshift) {
		// TODO Auto-generated method stub
		String[] args = visitBinOp(lshift);
		result = "(" + args[0] + " << " + args[1] +")";
		
	}

	@Override
	public void visitRightShift(AERightShift rshift) {
		// TODO Auto-generated method stub
		String[] args = visitBinOp(rshift);
		result = "(" + args[0] + " >> " + args[1] +")";
		
	}

}
