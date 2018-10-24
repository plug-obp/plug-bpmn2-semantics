package sandbox.command.examples.executer;
import java.util.*;

public class Command implements Icommands {
	
	protected List<String> pileExec = new ArrayList<String>();
	

	@Override
	public void exe(Task t) {
			
		if (t.getPointer() < t.getString().size())
		{
			//System.out.println(t.getString().get(t.getPointer()));
			pileExec.add(t.getString().get(t.getPointer()));
			System.out.println(pileExec);
			
			t.setPointer(t.getPointer()+1);

		}
		
		else
			System.out.println("List Exhausted /END of List");
		
		
				
	}
	

	@Override
	public void undo(Task t) {
		if (t.getPointer() !=0) {
			pileExec.remove(pileExec.size()-1);
			System.out.println(pileExec);
			t.setPointer(t.getPointer()-1);
		}
		else
			System.out.println("C'ant Undo Anymore");
		
		//System.out.println(t.getString().get(t.getPointer()));
		//t.setPointer(t.getPointer()+1);
		
	}


	@Override
	public void redo(Task t) {
		pileExec.add(t.getString().get(t.getPointer()));
		System.out.println(pileExec);
		System.out.println(t.getString().get(t.getPointer()));
		
	}

}
