package sandbox.command.examples.executer;
import java.util.*;

public class Invoker {
	private List<Task> PilesTaches = new ArrayList<Task>();
	private List<Command> commandes = new ArrayList<Command>();
	
	public void lancerExecution(Command comm) {
		commandes.add(0,comm);
		for (Task t : PilesTaches)
			comm.exe(t);
		
	}
	
	public void undoCommand(int number) {
		for (Task t : PilesTaches)
			commandes.get(number).undo(t);
	}
	
	public void remakeCommand(int number) {
		for (Task t : PilesTaches)
			commandes.get(number).redo(t);
	}
	
	public void AddTask(Task t) {
		PilesTaches.add(t);
	}
	
}
