package sandbox.command.examples.executer;

public class ExampleMain {
	
	public static void main(String[] args) {
		
		Task t= new Task();
		t.AddString(
				"tache 1",
				"tache 2",
				"tache 3",
				"tache 4"
				
				);
		Invoker invoker = new Invoker();
		invoker.AddTask(t);
		
		Command comm = new Command();
		
		invoker.lancerExecution(comm);
		invoker.lancerExecution(comm);
		invoker.lancerExecution(comm);
		invoker.remakeCommand(2);
		invoker.undoCommand(1);
		
		
		
	}

}
