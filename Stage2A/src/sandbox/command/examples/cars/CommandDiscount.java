package sandbox.command.examples.cars;
import java.util.*;

public class CommandDiscount {
	
	protected List<Cars> discountCars = new ArrayList<Cars>();
	protected long today;
	protected long duration;
	protected double discount;
	
	public CommandDiscount(long today, long duration, double discount) {
		this.today=today;
		this.discount=discount;
		this.duration=duration;
		
	}
	
	public void solde(List<Cars> cars) {
		discountCars.clear();
		for (Cars car: cars ) {
			if (car.getStorageTime(today) >= duration) {
				discountCars.add(car);
			}
		}
		
		for (Cars car: discountCars) {
			car.modifyPrice(1.0-discount);
		}
			
	}
	
	
	public void discardDiscount() {
		for (Cars car:discountCars)
			car.modifyPrice(1.0/(1.0-discount));
		
	}
	
	public void reApplyDiscount() {
		for (Cars car:discountCars)
			car.modifyPrice(1.0-discount);
		
	}
}
