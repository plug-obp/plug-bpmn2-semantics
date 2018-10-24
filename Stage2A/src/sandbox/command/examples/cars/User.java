package sandbox.command.examples.cars;

public class User {

	public static void main (String[] args) {
	Cars car1 = new Cars("Audi", 1,10000);
	Cars car2 = new Cars("Mercedes", 3,5000);
	Cars car3 = new Cars("Laguna", 6,2000);
	
	Catalog c = new Catalog();
	c.addCars(car1);
	c.addCars(car2);
	c.addCars(car3);

	c.show();
		
	CommandDiscount command = new CommandDiscount(12, 6, 0.5);
	
	c.launchCommand(command);
	
	c.show();
	
	}
	
}
