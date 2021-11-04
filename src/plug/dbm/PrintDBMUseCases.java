package plug.dbm;

/**
 * <p>Run the main to manually diagnosis the currently used version of the DBM.</p>
 * 
 * @author Luka Le Roux (luka.le_roux@ensta-bretagne.fr)
 */
public class PrintDBMUseCases {

	static private void printHeader(String title) {
		System.out.println(title + ";");
	}
	
	static private void printHeader(String title, String ... comments) {
		printHeader(title);
		for (String comment : comments)
			System.out.println("// " + comment);
	}
	
	static private void printDBM(short [] dbm) {
		System.out.print(DBM.toString(dbm));
		System.out.println(DBM.toConstrainString(dbm));
		System.out.println();
	}
	
	static private void printDBM(String title, short[] dbm, String ... comments) {
		printHeader(title, comments);
		printDBM(dbm);
	}
	
	static private void printUseCase(int size, short [] values, short [] maxBounds) {
		System.out.println("DBM size: " + size);
		for (int clockId = 1; clockId <= size; clockId++) {
			System.out.println("Clock Id: " + clockId +
					" Value: " + values[clockId - 1] +
					" Max bound: " + maxBounds[clockId -1]);
		}
		System.out.println();
	}
	
	static private void printLine() {
		System.out.println("******************************");
		System.out.println();
	}
	
	static private void print(String title, short [] dbm, boolean printDBM, String ... comments) {
		printHeader(title, comments);
		if (printDBM)
			printDBM(dbm);
		System.out.println();
	}
	
	static private short [] createAllDisable(int size, boolean printDBM, String ... comments) {
		short [] dbm = DBM.createAllDisable(size);
		String title = "dbm = DBM.createAllDisable(" + size + ")";
		print(title, dbm, printDBM, comments);
		return dbm;
	}
	
	static private short [] createUnconstrained(int size, boolean printDBM, String ... comments) {
		short [] dbm = DBM.createUnconstrained(size);
		String title = "dbm = DBM.createUnconstrained(" + size + ")";
		print(title, dbm, printDBM, comments);
		return dbm;
	}
	
	static private short [] createZero(int size, boolean printDBM, String ... comments) {
		short [] dbm = DBM.createZero(size);
		String title = "dbm = DBM.createZero(" + size + ")";
		print(title, dbm, printDBM, comments);
		return dbm;
	}
	
	static private void set(short[] dbm, int clockIndex, int value, boolean printDBM, String ... comments) {
		DBM.set(dbm, clockIndex, value);
		String title = "DBM.set(dbm, " + clockIndex + ", " + value + ")";
		print(title, dbm, printDBM, comments);
	}
	
	static private void enable(short[] dbm, int clockIndex, boolean printDBM, String ... comments) {
		DBM.enable(dbm, clockIndex);
		String title = "DBM.enable(dbm, " + clockIndex + ")";
		print(title, dbm, printDBM, comments);
	}
	
	static private void disable(short[] dbm, int clockIndex, boolean printDBM, String ... comments) {
		DBM.disable(dbm, clockIndex);
		String title = "DBM.disable(dbm, " + clockIndex + ")";
		print(title, dbm, printDBM, comments);
	}
	
	static public void main(String[] args) {
		final int size = 3;
		short [] values = new short [size];
		short [] maxBounds = new short [size];
		values[0] = 3; maxBounds[0] = 5;
		values[1] = 7; maxBounds[1] = 9;
		printUseCase(size, values, maxBounds);
		
		short[] dbm = createUnconstrained(size, true);
		
		printLine();
		
		dbm = createZero(size, true);
		
		printLine();
		
		dbm = createAllDisable(size, true);
		
		set(dbm, 0, 3, true, "Last argument is useless for clock id 0");
		
		printLine();
		
		dbm = createAllDisable(size, false);
		
		enable(dbm, 0, true);
		
		disable(dbm, 0, true);
		
		printLine();
		
		dbm = createAllDisable(size, false);
		
		enable(dbm, 1, true);
		
		enable(dbm, 2, true);
		
		enable(dbm, 0, true);
		
		disable(dbm, 0, true);
		
		disable(dbm, 1, true);
		
		printLine();
		
		dbm = createAllDisable(size, false);
		
		set(dbm, 1, values[0], true);
		
		DBM.normalize(dbm, maxBounds);
		printDBM("normalize(dbm, maxBounds)", dbm);
		
		disable(dbm, 1, true);
		
		DBM.disable(dbm, 0);
		printDBM("disable(dbm, 0)", dbm,
				"This makes the DBM entirely empty for JC's basic DBM version.",
				"Following calls should be messed up for this one."
				);
		
		set(dbm, 1, values[0], true);
		
		set(dbm, 2, values[1], true);
		
		DBM.normalize(dbm, maxBounds);
		printDBM("normalize(dbm, maxBounds)", dbm);
		
		set(dbm, 0, 0, true, "Last argument is useless for clock id 0");
		
		DBM.normalize(dbm, maxBounds);
		printDBM("normalize(dbm, maxBounds)", dbm);
		
	}
	
}