package sandbox.command.examples.cars;
import java.util.*;

public class Catalog {
	
	protected List<Cars> availableCars = new ArrayList<Cars>();
	protected List<CommandDiscount> commands = new ArrayList<CommandDiscount>();
	
	
	
	public void launchCommand(CommandDiscount command) {
		commands.add(0,command);
		command.solde(availableCars);
	}
	
	public void undoCommand(int number) {
		commands.get(number).discardDiscount();
	}
	
	public void remakeCommand(int number) {
		commands.get(number).reApplyDiscount();
	}
	
	public void addCars(Cars car) {
		availableCars.add(car);
	}
	
	public void show () {
		for (Cars c: availableCars)
			c.print();
	}
	
}
