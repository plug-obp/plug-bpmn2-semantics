package plug.dbm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


/**
 * <p><u>DBM handling methods</u></p>
 * 
 * <p>TODO comment</p>
 * <ul>
 * <li>ValueMask:		1111111111111110</li>
 * <li>ConstrainMask:	0000000000000001</li>
 * </ul>
 * 
 * 
 * <p>Each <code>i</code> in the DBM contains:
 * <ul><li><code>c_line - c_column</code> ~ <code>k</code></li></ul>
 * where:
 * <ul>
 * <li><code>line = i/(n+1)</code>,</li>
 * <li><code>column = i%(n+1)</code> and</li>
 * <li>'~' is:
 * 	<ul>
 *  <li>'<=' if <code>(dbm[i] & ConstrainMask) == 0</code>,</li>
 *  <li>'<' if <code>(dbm[i] & ConstrainMask) == 1</code>,</li>
 *  </ul>
 * </li>
 * <li><code>k = ((dbm[i] & ValueMask)>>1)</code>.</li>
 * </ul>
 * </p>
 * 
 * <p>Interval for constrains value is -1073741824 to 1073741823.</p>
 * <p>With short type it would be -16384 to 16383.</p>
 * <p>A DBM where all values are 0 is a DBM where all clock are equal to 0.</p>
 */
public class DBM {
	
	private final static short constrainMask = 1;
	private final static short constrainLess = 1;
	private final static short constrainLessOrEqual = 0;
	
	private final static short infinite = Short.MAX_VALUE;
	
	private final static short emptyness = computeInternal(-1, false);

	/** 
	 * <p>This integer array stores the correspondancies between the number of clocks
	 * and the dbm array size (#clocks*#clocks = dbm array size). 
	 * With this array, a {@link Configuration} doesn't need to 
	 * store the number of clock for a dbm, it can be quickly inferred by 
	 * {@link #getNumberOfClocks(int)}.</p>
	 */
	private final static int[] nbClockCorrespondancies = new int[] 
	{	1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 
		121, 144, 169, 196, 225, 256, 289, 324, 361, 400, 
		441, 484, 529, 576, 625, 676, 729, 784, 841, 900, 
		961, 1024, 1089, 1156, 1225, 1296, 1369, 1444, 1521, 1600, 
		1681, 1764, 1849, 1936, 2025, 2116, 2209, 2304, 2401, 2500, 
		2601, 2704, 2809, 2916, 3025, 3136, 3249, 3364, 3481, 3600, 
		3721, 3844, 3969, 4096, 4225, 4356, 4489, 4624, 4761, 4900, 
		5041, 5184, 5329, 5476, 5625, 5776, 5929, 6084, 6241, 6400, 
		6561, 6724, 6889, 7056, 7225, 7396, 7569, 7744, 7921, 8100, 
		8281, 8464, 8649, 8836, 9025, 9216, 9409, 9604, 9801, 10000, 
		10201, 10404, 10609, 10816, 11025, 11236, 11449, 11664, 11881, 12100, 
		12321, 12544, 12769, 12996, 13225, 13456, 13689, 13924, 14161, 14400, 
		14641, 14884, 15129, 15376, 15625, 15876, 16129, 16384, 16641, 16900, 
		17161, 17424, 17689, 17956, 18225, 18496, 18769, 19044, 19321, 19600, 
		19881, 20164, 20449, 20736, 21025, 21316, 21609, 21904, 22201, 22500, 
		22801, 23104, 23409, 23716, 24025, 24336, 24649, 24964, 25281, 25600, 
		25921, 26244, 26569, 26896, 27225, 27556, 27889, 28224, 28561, 28900, 
		29241, 29584, 29929, 30276, 30625, 30976, 31329, 31684, 32041, 32400, 
		32761, 33124, 33489, 33856, 34225, 34596, 34969, 35344, 35721, 36100, 
		36481, 36864, 37249, 37636, 38025, 38416, 38809, 39204, 39601, 40000, 
		40401, 40804, 41209, 41616, 42025, 42436, 42849, 43264, 43681, 44100, 
		44521, 44944, 45369, 45796, 46225, 46656, 47089, 47524, 47961, 48400, 
		48841, 49284, 49729, 50176, 50625, 51076, 51529, 51984, 52441, 52900, 
		53361, 53824, 54289, 54756, 55225, 55696, 56169, 56644, 57121, 57600, 
		58081, 58564, 59049, 59536, 60025, 60516, 61009, 61504, 62001, 62500, 
		63001, 63504, 64009, 64516, 65025, 65536, 66049, 66564, 67081, 67600, 
		68121, 68644, 69169, 69696, 70225, 70756, 71289, 71824, 72361, 72900, 
		73441, 73984, 74529, 75076, 75625, 76176, 76729, 77284, 77841, 78400, 
		78961, 79524, 80089, 80656, 81225, 81796, 82369, 82944, 83521, 84100, 
		84681, 85264, 85849, 86436, 87025, 87616, 88209, 88804, 89401, 90000, 
		90601, 91204, 91809, 92416, 93025, 93636, 94249, 94864, 95481, 96100, 
		96721, 97344, 97969, 98596, 99225, 99856, 100489, 101124, 101761, 102400, 
		103041, 103684, 104329, 104976, 105625, 106276, 106929, 107584, 108241, 108900, 
		109561, 110224, 110889, 111556, 112225, 112896, 113569, 114244, 114921, 115600, 
		116281, 116964, 117649, 118336, 119025, 119716, 120409, 121104, 121801, 122500, 
		123201, 123904, 124609, 125316, 126025, 126736, 127449, 128164, 128881, 129600, 
		130321, 131044, 131769, 132496, 133225, 133956, 134689, 135424, 136161, 136900, 
		137641, 138384, 139129, 139876, 140625, 141376, 142129, 142884, 143641, 144400, 
		145161, 145924, 146689, 147456, 148225, 148996, 149769, 150544, 151321, 152100, 
		152881, 153664, 154449, 155236, 156025, 156816, 157609, 158404, 159201, 160000, 
		160801, 161604, 162409, 163216, 164025, 164836, 165649, 166464, 167281, 168100, 
		168921, 169744, 170569, 171396, 172225, 173056, 173889, 174724, 175561, 176400, 
		177241, 178084, 178929, 179776, 180625, 181476, 182329, 183184, 184041, 184900, 
		185761, 186624, 187489, 188356, 189225, 190096, 190969, 191844, 192721, 193600, 
		194481, 195364, 196249, 197136, 198025, 198916, 199809, 200704, 201601, 202500, 
		203401, 204304, 205209, 206116, 207025, 207936, 208849, 209764, 210681, 211600, 
		212521, 213444, 214369, 215296, 216225, 217156, 218089, 219024, 219961, 220900, 
		221841, 222784, 223729, 224676, 225625, 226576, 227529, 228484, 229441, 230400, 
		231361, 232324, 233289, 234256, 235225, 236196, 237169, 238144, 239121, 240100, 
		241081, 242064, 243049, 244036, 245025, 246016, 247009, 248004, 249001, 250000 };
	/**
	 * <p>Creates a textual representation of the DBM. Only for debug.</p>
	 * @param dbm DBM to print.
	 * @return a String that represent the DBM.
	 */
	public static String toString(short[] dbm) {
		if ( dbm == null ) return "No clock constrains.";
		int nbClocks = getNumberOfClocks(dbm.length);
		StringBuilder builder = new StringBuilder();
		builder.append("     |");
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( i==0 ) {
				builder.append("  0 ");
			} else {
				builder.append("  c");
				builder.append(i);
				builder.append(" ");
			}
			builder.append("  |");
		}
		builder.append("\n");
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( i==0 ) {
				builder.append("   0");
			} else {
				builder.append("  c");
				builder.append(i);
			}
			builder.append(" |");
			for ( int j=0; j<=nbClocks; j++ ) {
				builder.append(" ");
				int internal = dbm[(i*(nbClocks+1))+j];
				if (i == j && internal == emptyness) {
					builder.append("  X ");
				} else if (internal == infinite ) {
					builder.append(" oo ");
				} else {
					builder.append(isStrict(internal) ? " <" : "<=");
					int value = getValue(internal);
					if ( value >= 0 ) builder.append(" ");
					builder.append(value);
				}
				builder.append(" | ");
			}
			builder.append("\n");
		}
		/* Prints internal dbm, only for debug
		builder.append("Internal ");
		builder.append(Arrays.toString(dbm));
		*/
		return builder.toString();
	}
	
	/**
	 * <p>Returns a String that expresses the DBM as constrains over clocks.</p>
	 * @param dbm DBM to print.
	 * @return a String that represent the DBM as constrains expressions.
	 */
	public static String toConstrainString(short[] dbm) {
		if ( dbm == null ) return "oo";
		if ( isEmpty(dbm) ) return "empty";
		int nbClocks = getNumberOfClocks(dbm.length);
		StringBuilder builder = new StringBuilder();
		for ( int i=1; i<=nbClocks; i++) {
			if ( dbm[(i*(nbClocks+1))+i] != emptyness ) {
				StringBuilder constrain = new StringBuilder();
				int miniInternal = dbm[i];
				if ( miniInternal != infinite ) {
					constrain.append(-getValue(miniInternal));
					constrain.append(isStrict(miniInternal) ? " < " : " <= ");
				}
				constrain.append("c");
				constrain.append(i);
				int maxiInternal = dbm[i*(nbClocks+1)];
				if ( maxiInternal != infinite ) {
					constrain.append(isStrict(maxiInternal) ? " < " : " <= ");
					constrain.append(getValue(maxiInternal));
				}
				if ( constrain.length() > 4 ) {
					if ( builder.length() > 0 ) builder.append(" & ");
					builder.append(constrain);
				}
			}
		}
		
		for ( int i=1; i<=nbClocks; i++ ) {
			if ( dbm[(i*(nbClocks+1))+i] != emptyness ) {
				for ( int j=i+1; j<=nbClocks; j++) {
					if ( dbm[(j*(nbClocks+1))+j] != emptyness ) {
						StringBuilder constrain = new StringBuilder();
						boolean atLeastOneBound = false;
						int miniInternal = dbm[(j*(nbClocks+1))+i];
						if ( miniInternal != infinite ) {
							atLeastOneBound = true;
							constrain.append(-getValue(miniInternal));
							constrain.append(isStrict(miniInternal) ? " < " : " <= ");
						}
						constrain.append("c");
						constrain.append(i);
						constrain.append(" - c");
						constrain.append(j);
						int maxiInternal = dbm[(i*(nbClocks+1))+j];
						if ( maxiInternal != infinite ) {
							atLeastOneBound = true;
							constrain.append(isStrict(maxiInternal) ? " < " : " <= ");
							constrain.append(getValue(maxiInternal));
						}
						if ( constrain.length() > 4 && atLeastOneBound) {
							if ( builder.length() > 0 ) builder.append(" & ");
							builder.append(constrain);
						}
					}
				}
			}
		}
		if ( builder.length() == 0 ) {
			builder.append("oo");
		}
		return builder.toString();
	}
	
	/**
	 * <p>Creates an unconstrained DBM with all clocks enable.</p>
	 * @param nbClocks number of clocks for DBM (not including the zero clock).
	 * @return a newly allocated DBM with no constrain.
	 */
	public static short[] createUnconstrained(int nbClocks) {
		short[] dbm = new short[(nbClocks+1)*(nbClocks+1)];
		Arrays.fill(dbm, infinite);
		for ( int i=0; i<dbm.length; i+= nbClocks+2) {
			dbm[i] = constrainLessOrEqual;
		}
		return dbm;
	}

	/**
	 * <p>Creates a DBM where all clock are equals to zero and enable.</p>
	 * @param nbClocks number of clocks for DBM (not including the zero clock).
	 * @return a newly allocated DBM with no constrain.
	 */
	public static short[] createZero(int nbClocks) {
		short[] dbm = new short[(nbClocks+1)*(nbClocks+1)];
		Arrays.fill(dbm, constrainLessOrEqual);
		return dbm;
	}
	
	/**
	 * <p>Creates  DBM where all clocks are disabled.</p>
	 * @param nbClocks number of clocks for DBM (not including the zero clock).
	 * @return a newly allocated DBM with no constrain.
	 */
	public static short[] createAllDisable(int nbClocks) {
		short[] dbm = new short[(nbClocks+1)*(nbClocks+1)];
		Arrays.fill(dbm, constrainLessOrEqual);
		for ( int i=1; i<=nbClocks; i++) {
			dbm[i*(nbClocks+1) + i] = emptyness;
		}
		return dbm;
	}

	/**
	 * <p>Closes a DBM, the result DBM is canonical (O(nbClocks^3) algorithm).</p>
	 * @param dbm DBM to modify
	 */
	public static void close(short[] dbm) {
		int nbClocks = getNumberOfClocks(dbm.length);
		for ( int k=0; k<=nbClocks; k++ ) {
			// if k is active
			if ( dbm[k*(nbClocks+1) + k] != emptyness ) {
				for ( int i=0; i<=nbClocks; i++ ) {
					// and if i is active
					if ( dbm[i*(nbClocks+1) + i] != emptyness ) {
						for ( int j=0; j<=nbClocks; j++ ) {
							if ( i == j ) continue;
							// and if j is active
							if ( dbm[j*(nbClocks+1) + j] != emptyness ) {
								int ij = i*(nbClocks+1) + j;
								int ik = i*(nbClocks+1) + k;
								int kj = k*(nbClocks+1) + j;
								dbm[ij] = min(dbm[ij], add(dbm[ik], dbm[kj]));
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * <p>Returns true if the DBM is empty.</p>
	 * <p><b>Caution:</b> It only tests if the first index of encoded values is 
	 * {@link #emptyness}. This works because the {@link #and(short[], int, int, int, int, boolean)}
	 * operator detects the emptyness and sets the {@link #emptyness} to first index.</p>
	 * @param dbm dbm to test.
	 * @return true is DBM is empty, false other.
	 */
	public static boolean isEmpty(short[] dbm) {
		return dbm[0] == emptyness; 
	}
	
	/**
	 * <p>Adds a constrain to DBM. It has the form: 
	 * <code>x - y {<, <=} m</code>.
	 * This method doesn't change the DBM is either x or y is inactive.
	 * It should be use in {@link Behavior#invariant(Configuration, ExplorationContext)}
	 * methods.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link #emptyness}.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param m constant value for the constrain
	 * @param strict if true the constrain is strict '<', '<=' otherwise.
	 */
	public static void andInvariant(short[] dbm, int x, int y, int m, boolean strict) {
		short mInternal = computeInternal(m, strict);
		andInvariant(dbm, x, y, mInternal);
	}
	
	/**
	 * <p>Adds a constrain to DBM. It has the form: 
	 * <code>x - y {<, <=} m</code>.
	 * This method doesn't change the DBM is either x or y is inactive.
	 * It should be use in {@link Behavior#invariant(Configuration, ExplorationContext)}
	 * methods.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link #emptyness}.</p>
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param mInternal constant value for the constrain and strictness value as internal representation 
	 * 					computed with {@link #computeInternal(int, boolean)}
	 */
	public static void andInvariant(short[] dbm, int x, int y, short mInternal) {
		int nbClocks = getNumberOfClocks(dbm.length);
		if ( dbm[x*(nbClocks+1) + x] == emptyness || dbm[y*(nbClocks+1) + y] == emptyness ) {
			// do nothing.
			
		} else {
			// applies and
			and(nbClocks, dbm, x, y, mInternal);
		}
	}

	/**
	 * <p>Adds a constrain to DBM. It has the form: 
	 * <code>x - y {<, <=} m</code>.
	 * In this method, if either x or y is inactive. The resulting DBM is empty.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link #emptyness}.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param m constant value for the constrain
	 * @param strict if true the constrain is strict '<', '<=' otherwise.
	 */
	public static void andGuard(short[] dbm, int x, int y, int m, boolean strict) {
		short mInternal = computeInternal(m, strict);
		andGuard(dbm, x, y, mInternal);
	}
	
	/**
	 * <p>Adds a constrain to DBM. It has the form: 
	 * <code>x - y {<, <=} m</code>.
	 * In this method, if either x or y is inactive. The resulting DBM is empty.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link #emptyness}.</p>
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param mInternal constant value for the constrain and strictness value as internal representation 
	 * 					computed with {@link #computeInternal(int, boolean)}
	 */
	public static void andGuard(short[] dbm, int x, int y, short mInternal) {
		int nbClocks = getNumberOfClocks(dbm.length);
		if ( dbm[x*(nbClocks+1) + x] == emptyness || dbm[y*(nbClocks+1) + y] == emptyness ) {
			// Creates a empty DBM, witch is directly detected by emptyness algorithm.
			dbm[0] = emptyness;
			
		} else {
			// applies and
			and(nbClocks, dbm, x, y, mInternal);
		}
	}
	
	/**
	 * <p>Adds a constrain to DBM. It has the form: 
	 * <code>x - y {<, <=} m</code>.
	 * <p>This operator preserves the canonical form.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link #emptyness}.</p>
	 * @param nbClocks number of clock.s
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param mInternal constant value for the constrain and strictness value as internal representation 
	 * 					computed with {@link #computeInternal(int, boolean)}
	 */
	private static void and(int nbClocks, short[] dbm, int x, int y, short mInternal) {
		if ( lessThan(add(dbm[y*(nbClocks+1) + x], mInternal), constrainLessOrEqual) ) {
			// Creates a empty DBM, witch is directly detected by emptyness algorithm.
			dbm[0] = emptyness;
			
		} else if ( lessThan(mInternal, dbm[x*(nbClocks+1) + y]) ) {
			// adds the constrain.
			dbm[x*(nbClocks+1) + y] = mInternal;
			// local normalization to keep the DBM canonical
			for ( int i=0; i<=nbClocks; i++ ) {
				// if i is active
				if ( dbm[i*(nbClocks+1) + i] != emptyness ) {
					for ( int j=0; j<=nbClocks; j++ ) {
						if ( i == j ) continue;
						// and if j is active
						if ( dbm[j*(nbClocks+1) + j] != emptyness ) {
							short addIXJ = add(dbm[i*(nbClocks+1) +x], dbm[x*(nbClocks+1) +j]);
							if ( lessThan(addIXJ, dbm[i*(nbClocks+1) +j]) ) {
								dbm[i*(nbClocks+1) +j] = addIXJ;
							}
							short addIYJ = add(dbm[i*(nbClocks+1) +y], dbm[y*(nbClocks+1) +j]);
							if ( lessThan(addIYJ, dbm[i*(nbClocks+1) +j]) ) {
								dbm[i*(nbClocks+1) +j] = addIYJ;
							}
						}
					}
				}
			}
		}
		
	}

	/**
	 * <p>Normalize DBM.</p>
	 * @param dbm DBM to modify
	 * @param k maximum constants value for each clock in DBM (k.length == nbClocks).
	 */
	public static void normalize(short[] dbm, short[] k) {
		// TODO implement activation
		int nbClocks = getNumberOfClocks(dbm.length);
		for ( int i=0; i<=nbClocks; i++ ) {
			// if i is active
			if ( dbm[i*(nbClocks+1) + i] != emptyness ) {
				for ( int j=0; j<=nbClocks; j++ ) {
					if ( i==j ) continue; 
					// and if j is active
					if ( dbm[j*(nbClocks+1) + j] != emptyness ) {
	
						int index = i+(nbClocks+1) + j;
						int internal = dbm[index];
						int value = getValue(internal);
						boolean strict = isStrict(internal);
						if ( internal != infinite && value > k[i]) {
							dbm[index] = infinite; 
						} else if ( internal != infinite && ( value < -k[j] || (value == -k[j] && !strict) ) ) {
							dbm[index] = computeInternal(-k[j], true);
						}
					}
				}
			}
		}
		close(dbm);
	}

	/**
	 * <p>Copy given DBM.</p>
	 * @param dbm to copy.
	 * @return new DBM.
	 */
	public static short[] copy(short[] dbm) {
		return dbm == null ? null : Arrays.copyOf(dbm, dbm.length);
	}
	
	
	/**
	 * <p>Applies delay on given DBM.</p>
	 * @param dbm DBM to modify
	 */
	public static void delay(short[] dbm) {
		int nbClocks = getNumberOfClocks(dbm.length);
		for ( int i=1; i<=nbClocks; i++) {
			if ( dbm[i*(nbClocks+1) + i] != emptyness ) {	
				dbm[i*(nbClocks+1)] = infinite;
			}
		}
	}
	
	/**
	 * <p>Applies delay on given DBM for one clock.</p>
	 * @param dbm DBM to modify
	 * @param clockIndex clock to delay
	 */
	public static void delay(short[] dbm, int clockIndex) {
		int nbClocks = getNumberOfClocks(dbm.length);
		if ( dbm[clockIndex *(nbClocks+1) + clockIndex] != emptyness ) {	
			dbm[clockIndex*(nbClocks+1)] = infinite;
		}
	}
	
	/**
	 * <p>Sets a clock to a value. It enables the clock if needed.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * @param dbm DBM to modify
	 * @param clockIndex clock to set
	 * @param value value to be set for the clock
	 */
	public static void set(short[] dbm, int clockIndex, int value) {
		int nbClocks = getNumberOfClocks(dbm.length);
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( i==clockIndex ) {
				dbm[i*(nbClocks+1) + i] = constrainLessOrEqual;
			} else {
				// if clock i is active
				if ( dbm[i*(nbClocks+1) + i] != emptyness ) {
					dbm[clockIndex*(nbClocks+1) + i] = add(computeInternal(value, false), dbm[i]);
					dbm[i*(nbClocks+1) + clockIndex] = add(computeInternal(-value, false), dbm[i*(nbClocks+1)]);
				}
			}
		}
	}
	
	/**
	 * <p>Returns true if clock is enable.</p>
	 * @param dbm DBM to test.
	 * @param clockIndex clock index to test
	 * @return true if clock is enable.
	 */
	public static boolean isEnable(short[] dbm, int clockIndex) {
		int nbClocks = getNumberOfClocks(dbm.length);
		return dbm[clockIndex*(nbClocks+1) + clockIndex] != emptyness; 
	}
	
	/**
	 * <p>Enables a clock and releases all constrains on it.</p>
	 * <p>This operator <b>does NOT</b> preserves the canonical form.</p>
	 * @param dbm DBM to modify
	 * @param clockIndex clock to enable.
	 */
	public static void enable(short[] dbm, int clockIndex) {
		int nbClocks = getNumberOfClocks(dbm.length);
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( i==clockIndex ) {
				dbm[i*(nbClocks+1) + i] = constrainLessOrEqual;
			} else {
				if ( dbm[i*(nbClocks+1) + i] != emptyness ) { 
					dbm[clockIndex*(nbClocks+1) + i] = infinite;
					dbm[i*(nbClocks+1) + clockIndex] = infinite;
				}
			}
		}
	}
	
	/**
	 * <p>Disables a clock.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * @param dbm DBM to modify
	 * @param clockIndex clock to disable.
	 */
	public static void disable(short[] dbm, int clockIndex) {
		int nbClocks = getNumberOfClocks(dbm.length);
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( i==clockIndex ) {
				dbm[i*(nbClocks+1) + i] = emptyness;
			} else {
				dbm[clockIndex*(nbClocks+1) + i] = constrainLessOrEqual;
				dbm[i*(nbClocks+1) + clockIndex] = constrainLessOrEqual;
			}
		}
	}
	
	/** <p>Searches for number of clock from dbm array length.</p> */
	private static int getNumberOfClocks(int length) {
		if (length <= 250000) {
			return Arrays.binarySearch(nbClockCorrespondancies, length);
		}
		else {
			//if ever more than 500 clocks, we do not have any more the entries precomputed
			return ((int) Math.sqrt(length) - 1);
		}
	}
	
	/** 
	 * <p>Compares two encoded values: <code>internal1 < internal1</code>. It
	 * takes the strict state in account.</p>
	 * @param internal1 first encoded value.
	 * @param internal2 second encoded value.
	 * @return true if internal1 is less than internal2.
	 */
	private static boolean lessThan(int internal1, int internal2) {
		if ( internal1 == infinite ) return false;
		int value1 = getValue(internal1);
		int value2 = getValue(internal2);
		if ( value1 == value2 ) {
			// values are equals, internal1 < internal2 if and only if:
			// -> internal1 is strict and internal2 is NOT.
			return isStrict(internal1) && !isStrict(internal2);
		}
		return value1 < value2;
	}
	
	//check if dbm1 is included in dbm2
	public static boolean inclusion(short[] dbm1, short[] dbm2) {
		if (dbm1.length != dbm2.length) return false;
		if (Arrays.equals(dbm1, dbm2)) return true;
		int i = 0;
		while (i < dbm1.length) {
			short v1 = dbm1[i];
			short v2 = dbm2[i];
			i++;
			if (v1 != v2 && lessThan(v2, v1)) {
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	private static boolean lessOrEqual(int internal1, int internal2) {
		if (internal1 == internal2) return true;
		if (internal1 == infinite && internal2 != infinite) return false;
		int value1 = getValue(internal1);
		int value2 = getValue(internal2);
		if (value1 == value2) {
			return isStrict(internal1) || !isStrict(internal2);			
		}
		return value1 < value2;
	}
	
	/**
	 * <p>Returns the minimum of two encoded values. It takes the strict state
	 * in account.</p> 
	 * @param internal1 first encoded value.
	 * @param internal2 second encoded value.
	 * @return the minimum of the two.
	 * @see DBM#lessThan(int, int)
	 */
	private static short min(short internal1, short internal2) {
		return lessThan(internal1, internal2) ? internal1 : internal2;
	}
	
	/**
	 * <p>Adds the two encoded values, taking in account strict states.</p>
	 * @param internal1 first encoded value.
	 * @param internal2 second encoded value.
	 * @return the addition of the two encoded values.
	 */
	private static short add(short internal1, short internal2) {
		if ( internal1 == infinite || internal2 == infinite ) return infinite;
		return computeInternal(
					getValue(internal1) + getValue(internal2), 
					isStrict(internal1) || isStrict(internal2)
				);
	}
	
	/**
	 * <p>Computes internal representation for value and constrain type.</p>
	 * @param value value to encode.
	 * @param strict type of constrain to encode. If true, it represent '<', '<=' otherwise.
	 * @return the internal representation.
	 */
	public static short computeInternal(int value, boolean strict) {
		return (short) (value << 1 | (strict ? constrainLess : constrainLessOrEqual));
	}
	
	public static short computeInternalInfinite() {
		return infinite;
	}
	
	/**
	 * <p>Extracts the constrain value for given internal representation.<p>
	 * @param internal representation to extract constrain value from.
	 * @return the constrain value.
	 */
	private static int getValue(int internal) {
		return internal >> 1;
	}
	
	/**
	 * <p>Extracts strict state for constrain from given internal representation.</p> 
	 * @param internal representation to extract constrain state from.
	 * @return the strict state.
	 */
	private static boolean isStrict(int internal) {
		return (internal&constrainMask) == constrainLess;
	}
	
	public static boolean isEnabled(short[] dbm, int nbClocks, int clockIndex) {
		return dbm[clockIndex*(nbClocks+1) + clockIndex] != emptyness; 
	}
	
	/**
	 * <p>Returns a String that expresses the DBM as a graph in the trivial graph format.</p>
	 * @param dbm DBM to print.
	 * @return a String that represent the DBM as TGF.
	 */
	public static String toTGF(short[] dbm) {
		if ( dbm == null ) return "";
		if ( isEmpty(dbm) ) return "";
		int nbClocks = getNumberOfClocks(dbm.length);
		StringBuilder nodeBuilder = new StringBuilder();
		StringBuilder edgeBuilder = new StringBuilder();
		
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( isEnabled(dbm, nbClocks, i) ) {
				nodeBuilder.append(i + " " + i + "\n");
				for ( int j=i+1; j<=nbClocks; j++) {
					if (  isEnabled(dbm, nbClocks, j) ) {
						
						int miniInternal = dbm[(j*(nbClocks+1))+i];
						if ( miniInternal != infinite ) {
							edgeBuilder.append(j + " " + i);
							edgeBuilder.append(isStrict(miniInternal) ? " > " : " >= ");
							edgeBuilder.append(-getValue(miniInternal) + "\n");
						}
						
						
						
						int maxiInternal = dbm[(i*(nbClocks+1))+j];
						if ( maxiInternal != infinite ) {
							edgeBuilder.append(i + " " + j);
							edgeBuilder.append(isStrict(maxiInternal) ? " < " : " <= ");
							edgeBuilder.append(getValue(maxiInternal) + "\n");
						}
					}
				}
			}
		}
		
		return nodeBuilder.toString() + "\n#\n" + edgeBuilder.toString();
	}
	
	public static void toFileTFG(short[] dbm, int id) {
		String tgfString = DBM.toTGF(dbm);
		Path path = Paths.get("dbm" + id + ".tgf").toAbsolutePath();
		try {
			path.toFile().createNewFile();
			Files.write(path, tgfString.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getNumberOfActiveClocks(short[] dbm) {
		int nbClocks = getNumberOfClocks(dbm.length);
		int nb = 0;
		for ( int i=0; i<=nbClocks; i++ ) {
			if (isEnabled(dbm, nbClocks, i)) {
				nb++;
			}
		}
		return nb;
	}
	
    public static boolean isClockLessThan(short[] dbm, int clockID, int bound) {
    	int nbClocks = getNumberOfClocks(dbm.length);
    	if (dbm[(clockID*(nbClocks+1))+clockID] == emptyness) return false;
    	
    	int maxiInternal = dbm[clockID * (nbClocks + 1)];
    	if (bound == getValue(maxiInternal) && isStrict(maxiInternal)) return true; 
    	return false;
    }
    public static boolean isClockLessOrEqualThan(short[] dbm, int clockID, int bound) { 
    	int nbClocks = getNumberOfClocks(dbm.length);
    	if (dbm[(clockID*(nbClocks+1))+clockID] == emptyness) return false;
    	
    	int maxiInternal = dbm[clockID * (nbClocks + 1)];
    	if (bound == getValue(maxiInternal) && !isStrict(maxiInternal)) return true; 
    	return false;
    }
    public static boolean isClockGreaterThan(short[] dbm, int clockID, int bound) { 
    	int nbClocks = getNumberOfClocks(dbm.length);
    	if (dbm[(clockID*(nbClocks+1))+clockID] == emptyness) return false;
    	
    	int miniInternal = dbm[clockID];
    	if (-bound == getValue(miniInternal) && isStrict(miniInternal)) return true; 
    	return false;
    }
    public static boolean isClockGreaterOrEqualThan(short[] dbm, int clockID, int bound) {
    	int nbClocks = getNumberOfClocks(dbm.length);
    	if (dbm[(clockID*(nbClocks+1))+clockID] == emptyness) return false;
    	
    	int miniInternal = dbm[clockID];
    	if (-bound == getValue(miniInternal) && !isStrict(miniInternal)) return true; 
    	return false;
    }

    public static boolean isClockDiferenceLessThan(short[] dbm, int i, int j, int bound) { 
    	int nbClocks = getNumberOfClocks(dbm.length);
    	int maxiInternal = dbm[(i*(nbClocks+1))+j];
    	
    	if (bound == getValue(maxiInternal) && isStrict(maxiInternal)) return true;
    	return false;
    }
    public static boolean isClockDiferenceLessOrEqualThan(short[] dbm, int i, int j, int bound) {
    	int nbClocks = getNumberOfClocks(dbm.length);
    	int maxiInternal = dbm[(i*(nbClocks+1))+j];
    	
    	if (bound == getValue(maxiInternal) && !isStrict(maxiInternal)) return true;
    	return false;
    }
    public static boolean isClockDiferenceGreaterThan(short[] dbm, int i, int j, int bound) {
    	int nbClocks = getNumberOfClocks(dbm.length);
    	int miniInternal = dbm[(j*(nbClocks+1))+i];
    	
    	if (-bound == getValue(miniInternal) && isStrict(miniInternal)) return true;
    	return false;
    }
    public static boolean isClockDiferenceGreaterOrEqualThan(short[] dbm, int i, int j, int bound) { 
    	int nbClocks = getNumberOfClocks(dbm.length);
    	int miniInternal = dbm[(j*(nbClocks+1))+i];
    	
    	if (-bound == getValue(miniInternal) && !isStrict(miniInternal)) return true;
    	return false;
    }
    
    public static boolean evaluateConstraint(short[] dbm, String constraint) {
    	return false;
    }
    
    public static boolean isEmptyDBM(short[] dbm) {
    	if ( dbm == null ) return true;
		if ( isEmpty(dbm) ) return true;
		int nbClocks = getNumberOfClocks(dbm.length);
		for ( int i=1; i<=nbClocks; i++) {
			if ( dbm[(i*(nbClocks+1))+i] != emptyness ) {
				return false;
			}
		}
		return true;
    }
}
