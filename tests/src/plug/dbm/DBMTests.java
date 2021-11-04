package plug.dbm;

import java.util.Arrays;

import junit.framework.TestCase;

public class DBMTests extends TestCase {

	public void testInclusion1() {
		short[] dbm = DBM.createUnconstrained(2);
		
		// Adds constrain: 'x1-x0 <= 3'.
		DBM.andInvariant(dbm, 1, 0, 3, false);

		// Adds constrain: 'x0-x1 <= -1'.
		DBM.andInvariant(dbm, 0, 1, -1, false);

		// Adds constrain: 'x2-x0 <= 3'.
		DBM.andInvariant(dbm, 2, 0, 3, false);
		
		// Adds constrain: 'x0-x2 <= -2'.
		DBM.andInvariant(dbm, 0, 2, -2, false);
		System.err.println(DBM.toConstrainString(dbm));
		
		short[] dbm1 = DBM.createUnconstrained(2);
		
		// Adds constrain: 'x1-x0 <= 3'.
		DBM.andInvariant(dbm1, 1, 0, 2, false);

		// Adds constrain: 'x0-x1 <= -1'.
		DBM.andInvariant(dbm1, 0, 1, -1, false);

		// Adds constrain: 'x2-x0 <= 3'.
		DBM.andInvariant(dbm1, 2, 0, 3, false);
		
		// Adds constrain: 'x0-x2 <= -2'.
		DBM.andInvariant(dbm1, 0, 2, -2, false);
		System.err.println(DBM.toConstrainString(dbm1));
		
		System.err.println( DBM.inclusion(dbm1, dbm));
	}
	
	public void testAnd1() { 
		short[] dbm = DBM.createUnconstrained(2);
		
		// Adds constrain: 'x1-x0 <= 3'.
		DBM.andInvariant(dbm, 1, 0, 3, false);

		// Adds constrain: 'x0-x1 <= -1'.
		DBM.andInvariant(dbm, 0, 1, -1, false);

		// Adds constrain: 'x2-x0 <= 3'.
		DBM.andInvariant(dbm, 2, 0, 3, false);
		
		// Adds constrain: 'x0-x2 <= -2'.
		DBM.andInvariant(dbm, 0, 2, -2, false);

		
		assertTrue(Arrays.equals(new short[] { 0, -2, -4, 6, 0, 2, 6, 4, 0 }, dbm));
	}

	public void testAnd2() { 
		short[] dbm = DBM.createUnconstrained(2);
	
		// Adds constrain: 'x1-x0 < 2'.
		DBM.andInvariant(dbm, 1, 0, 2, true);
		
		// Adds constrain: 'x2-x0 < 1'.
		DBM.andInvariant(dbm, 2, 0, 1, true);
		
		// Adds constrain: 'x0-x2 < 0'.
		DBM.andInvariant(dbm, 0, 2, 0, true);
		
		// Adds constrain: 'x2-x1 <= 0'.
		DBM.andInvariant(dbm, 2, 1, 0, false);
		
		assertTrue(Arrays.equals(new short[] { 0, 1, 1, 5, 0, 5, 3, 0, 0 }, dbm));
	}

	public void testEmptyness1() { 
		short[] dbm = DBM.createUnconstrained(2);
		
		// Adds constrain: 'x1-x0 < 2'.
		DBM.andInvariant(dbm, 1, 0, 2, true);
		
		// Adds constrain: 'x0-x1 < -4'.
		DBM.andInvariant(dbm, 0, 1, -4, true);
		
		assertTrue(DBM.isEmpty(dbm));
	}
	
	
	public void testExploration1() {
		short[] dbm = DBM.createUnconstrained(2);
		// Prepare initial DBM.
		
		// Adds constrains: '1 < x1 < 3'.
		DBM.andInvariant(dbm, 1, 0, 3, true);
		DBM.andInvariant(dbm, 0, 1, -1, true);
		
		// Adds constrains: '1 < x2 < 5'.
		DBM.andInvariant(dbm, 2, 0, 5, true);
		DBM.andInvariant(dbm, 0, 2, -1, true);
		// Adds guard 'y>2'
		DBM.andInvariant(dbm, 0, 2, -2, true);
		
		assertTrue(Arrays.equals(new short[] { 0, -1, -3, 7, 0, 3, 11, 9, 0 }, dbm));
		
		// Checks emptyness
		assertFalse(DBM.isEmpty(dbm));
		
		// Resets 'x:=0'
		DBM.set(dbm, 1, 0);
		
		assertTrue(Arrays.equals(new short[] { 0, 0, -3, 0, 0, -3, 11, 11, 0 }, dbm));
		
		// Time runs
		DBM.delay(dbm);
		
		assertTrue(Arrays.equals(new short[] { 0, 0, -3, DBM.computeInternalInfinite(), 0, -3, DBM.computeInternalInfinite(), 11, 0 }, dbm));
	}

	public void testExploration2() {
		// Prepare initial DBM.
		short[] dbm = DBM.createAllDisable(3);
		DBM.set(dbm, 1, 0);
		DBM.set(dbm, 2, 0);
		DBM.delay(dbm);
		
		assertTrue(Arrays.equals(new short[] { 0, 0, 0, 0, DBM.computeInternalInfinite(), 0, 0, 0, DBM.computeInternalInfinite(), 0, 0, 0, 0, 0, 0, -2 }, dbm));
		
		// Adds constrains: '1 < x1 < 3'.
		DBM.andInvariant(dbm, 1, 0, 3, true);
		DBM.andInvariant(dbm, 0, 1, -1, true);
		
		// Adds constrains: '1 < x2 < 5'.
		DBM.andInvariant(dbm, 2, 0, 5, true);
		DBM.andInvariant(dbm, 0, 2, -1, true);
		// Adds guard 'y>2'
		DBM.andInvariant(dbm, 0, 2, -2, true);
		
		assertTrue(Arrays.equals(new short[] { 0, -3, -3, 0, 7, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, -2 }, dbm));
		
		// Checks emptyness
		assertFalse(DBM.isEmpty(dbm));
		
		// sets 'x:=0'
		DBM.set(dbm, 1, 0);
		
		assertTrue(Arrays.equals(new short[] { 0, 0, -3, 0, 0, 0, -3, 0, 7, 7, 0, 0, 0, 0, 0, -2 }, dbm));
		
		// Time runs
		DBM.delay(dbm);
		
		assertTrue(Arrays.equals(new short[] { 0, 0, -3, 0, DBM.computeInternalInfinite(), 0, -3, 0, DBM.computeInternalInfinite(), 7, 0, 0, 0, 0, 0, -2 }, dbm));
	}
	
	public void testActivation() {
		short[] dbm = DBM.createAllDisable(2);
		assertTrue(Arrays.equals(new short[] { 0, 0, 0, 0, -2, 0, 0, 0, -2 }, dbm));
		DBM.set(dbm, 1, 1);
		assertTrue(Arrays.equals(new short[] { 0, -2, 0, 2, 0, 0, 0, 0, -2 }, dbm));
	}
	
	public void testPerformance() {
		for ( int i=0; i<500000; i++) {
			testAnd1();
			testAnd2();
			testEmptyness1();
			testExploration1();
			testExploration2();
		}
	}
}
