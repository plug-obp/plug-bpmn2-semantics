package sandbox.command.examples.cars;

public class Cars {
	protected String name;
	protected long enterDate;
	protected double sellingPrice;
	
	public Cars(String name, long date, double price) {
		this.name=name;
		this.enterDate=date;
		this.sellingPrice=price;
	}
	
	public long getStorageTime(long today) {
		return today - enterDate;
	}
	
	public void modifyPrice (double coef) {
		this.sellingPrice = .01*Math.round(coef * this.sellingPrice *100);
		
	}
	
	public void print() {
		System.out.println(name + " price : " + sellingPrice + " entry date " +enterDate );
	}
	
}
