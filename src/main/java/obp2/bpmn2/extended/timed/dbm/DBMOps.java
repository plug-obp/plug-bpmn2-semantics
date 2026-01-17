package obp2.bpmn2.extended.timed.dbm;

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
public class DBMOps {

	/**
	 * <p>Closes a DBM, the result DBM is canonical (O(nbClocks^3) algorithm).</p>
	 * @param dbm DBM to modify
	 */
	public static void close(short[] dbm) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		for ( int k=0; k<=nbClocks; k++ ) {
			// if k is active
			if ( dbm[k*(nbClocks+1) + k] != DBMInternals.EMPTINESS) {
				for ( int i=0; i<=nbClocks; i++ ) {
					// and if i is active
					if ( dbm[i*(nbClocks+1) + i] != DBMInternals.EMPTINESS) {
						for ( int j=0; j<=nbClocks; j++ ) {
							if ( i == j ) continue;
							// and if j is active
							if ( dbm[j*(nbClocks+1) + j] != DBMInternals.EMPTINESS) {
								int ij = i*(nbClocks+1) + j;
								int ik = i*(nbClocks+1) + k;
								int kj = k*(nbClocks+1) + j;
								dbm[ij] = DBMInternals.min(dbm[ij], DBMInternals.add(dbm[ik], dbm[kj]));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * <p>Returns true if the DBM is marked empty (dbm[0] == {@link DBMInternals#EMPTINESS}).</p>
	 * @param dbm dbm to test.
	 * @return true is DBM is marked empty, false otherwise.
	 */
	public static boolean isMarkedEmpty(short[] dbm) {
		return dbm[0] == DBMInternals.EMPTINESS;
	}

	public static void markEmpty(short[] dbm) {
		dbm[0] = DBMInternals.EMPTINESS;
	}

	/**
	 * <p>Returns true if the DBM is empty. If true, also marks it as such if it was not.</p>
	 * @see DBMOps#markEmpty, DBM#isMarkedEmpty
	 * @param dbm dbm to test.
	 * @return true is DBM is empty, false otherwise.
	 */
	public static boolean isEmpty(short[] dbm) {
		if ( dbm == null ) return true;
		if ( isMarkedEmpty(dbm) ) return true;
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		for ( int i=1; i<=nbClocks; i++) {
			if ( dbm[(i*(nbClocks+1))+i] != DBMInternals.EMPTINESS) {
				return false;
			}
		}
		markEmpty(dbm);
		return true;
	}

	/**
	 * <p>Adds a constrain to DBM. It has the form:
	 * <code>x - y {<, <=} m</code>.
	 * This method doesn't change the DBM is either x or y is inactive.
	 *
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link DBMInternals#EMPTINESS}.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param m constant value for the constrain
	 * @param strict if true the constrain is strict '<', '<=' otherwise.
	 */
	public static void andInvariant(short[] dbm, int x, int y, int m, boolean strict) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		short mInternal = DBMInternals.computeInternal(m, strict);
		andInvariant(nbClocks, dbm, x, y, mInternal);
	}

	public static void andInvariant(int nbClocks, short[] dbm, int x, int y, int m, boolean strict) {
		short mInternal = DBMInternals.computeInternal(m, strict);
		andInvariant(nbClocks, dbm, x, y, mInternal);
	}

	/**
	 * <p>Adds a constrain to DBM. It has the form:
	 * <code>x - y {<, <=} m</code>.
	 * This method doesn't change the DBM is either x or y is inactive.
	 *
	 * <p>This operator preserves the canonical form.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link DBMInternals#EMPTINESS}.</p>
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param mInternal constant value for the constrain and strictness value as internal representation
	 * 					computed with {@link DBMInternals#computeInternal(int, boolean)}
	 */
	public static void andInvariant(short[] dbm, int x, int y, short mInternal) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		andInvariant(nbClocks, dbm, x, y, mInternal);
	}

	public static void andInvariant(int nbClocks, short[] dbm, int x, int y, short mInternal) {
		// Does nothing if either clock relates to emptiness
		if (dbm[x * (nbClocks + 1) + x] != DBMInternals.EMPTINESS &&
						dbm[y * (nbClocks + 1) + y] != DBMInternals.EMPTINESS) {
			and(nbClocks, dbm, x, y, mInternal);
		}
	}

	/**
	 * <p>Adds a constrain to DBM. It has the form:
	 * <code>x - y {<, <=} m</code>.
	 * In this method, if either x or y is inactive. The resulting DBM is empty.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link DBMInternals#EMPTINESS}.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param m constant value for the constrain
	 * @param strict if true the constrain is strict '<', '<=' otherwise.
	 */
	public static void andGuard(short[] dbm, int x, int y, int m, boolean strict) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		andGuard(nbClocks, dbm, x, y, m, strict);
	}

	/**
	 * <p>Adds a constrain to DBM. It has the form:
	 * <code>x - y {<, <=} m</code>.
	 * In this method, if either x or y is inactive. The resulting DBM is empty.</p>
	 * <p>This operator preserves the canonical form.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link DBMInternals#EMPTINESS}.</p>
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param mInternal constant value for the constrain and strictness value as internal representation
	 * 					computed with {@link DBMInternals#computeInternal(int, boolean)}
	 */
	public static void andGuard(short[] dbm, int x, int y, short mInternal) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		andGuard(nbClocks, dbm, x, y, mInternal);
	}

	/**
	 * <p>Adds a constrain to DBM. It has the form:
	 * <code>x - y {<, <=} m</code>.
	 * <p>This operator preserves the canonical form.</p>
	 * <p>If the constrain creates a empty DBM, it sets the first encoded value
	 * to {@link DBMInternals#EMPTINESS}.</p>
	 * @param nbClocks number of clock.s
	 * @param dbm DBM to modify
	 * @param x clock index for first clock of the constrain
	 * @param y clock index for second clock of the constrain
	 * @param mInternal constant value for the constrain and strictness value as internal representation
	 * 					computed with {@link DBMInternals#computeInternal(int, boolean)}
	 */
	private static void and(int nbClocks, short[] dbm, int x, int y, short mInternal) {
		if ( DBMInternals.lessThan(DBMInternals.add(dbm[y*(nbClocks+1) + x], mInternal), DBMInternals.CONSTRAIN_LESS_OR_EQUAL) ) {
			// Creates a empty DBM, witch is directly detected by emptyness algorithm.
			dbm[0] = DBMInternals.EMPTINESS;

		} else if ( DBMInternals.lessThan(mInternal, dbm[x*(nbClocks+1) + y]) ) {
			// adds the constrain.
			dbm[x*(nbClocks+1) + y] = mInternal;
			// local normalization to keep the DBM canonical
			for ( int i=0; i<=nbClocks; i++ ) {
				// if i is active
				if ( dbm[i*(nbClocks+1) + i] != DBMInternals.EMPTINESS) {
					for ( int j=0; j<=nbClocks; j++ ) {
						if ( i == j ) continue;
						// and if j is active
						if ( dbm[j*(nbClocks+1) + j] != DBMInternals.EMPTINESS) {
							short addIXJ = DBMInternals.add(dbm[i*(nbClocks+1) +x], dbm[x*(nbClocks+1) +j]);
							if ( DBMInternals.lessThan(addIXJ, dbm[i*(nbClocks+1) +j]) ) {
								dbm[i*(nbClocks+1) +j] = addIXJ;
							}
							short addIYJ = DBMInternals.add(dbm[i*(nbClocks+1) +y], dbm[y*(nbClocks+1) +j]);
							if ( DBMInternals.lessThan(addIYJ, dbm[i*(nbClocks+1) +j]) ) {
								dbm[i*(nbClocks+1) +j] = addIYJ;
							}
						}
					}
				}
			}
		}

	}

	public static void andGuard(int nbClocks, short[] dbm, int x, int y, int m, boolean strict) {
		short mInternal = DBMInternals.computeInternal(m, strict);
		andGuard(nbClocks, dbm, x, y, mInternal);
	}

	public static void andGuard(int nbClocks, short[] dbm, int x, int y, short mInternal) {
		if ( dbm[x*(nbClocks+1) + x] == DBMInternals.EMPTINESS || dbm[y*(nbClocks+1) + y] == DBMInternals.EMPTINESS) {
			// Creates a empty DBM, witch is directly detected by emptyness algorithm.
			dbm[0] = DBMInternals.EMPTINESS;

		} else {
			// applies and
			and(nbClocks, dbm, x, y, mInternal);
		}
	}

	/**
	 * <p>Normalize DBM.</p>
	 * @param dbm DBM to modify
	 * @param k maximum constants value for each clock in DBM (k.length == nbClocks).
	 */
	public static void normalize(short[] dbm, short[] k) {
		// TODO implement activation
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		for ( int i=0; i<=nbClocks; i++ ) {
			// if i is active
			if ( dbm[i*(nbClocks+1) + i] != DBMInternals.EMPTINESS) {
				for ( int j=0; j<=nbClocks; j++ ) {
					if ( i==j ) continue;
					// and if j is active
					if ( dbm[j*(nbClocks+1) + j] != DBMInternals.EMPTINESS) {

						int index = i+(nbClocks+1) + j;
						int internal = dbm[index];
						int value = DBMInternals.getValue(internal);
						boolean strict = DBMInternals.isStrict(internal);
						if ( internal != DBMInternals.INFINITE && value > k[i]) {
							dbm[index] = DBMInternals.INFINITE;
						} else if ( internal != DBMInternals.INFINITE && ( value < -k[j] || (value == -k[j] && !strict) ) ) {
							dbm[index] = DBMInternals.computeInternal(-k[j], true);
						}
					}
				}
			}
		}
		close(dbm);
	}

	/**
	 * <p>Applies delay on given DBM.</p>
	 * @param dbm DBM to modify
	 */
	public static void delay(short[] dbm) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		for ( int i=1; i<=nbClocks; i++) {
			if ( dbm[i*(nbClocks+1) + i] != DBMInternals.EMPTINESS) {
				dbm[i*(nbClocks+1)] = DBMInternals.INFINITE;
			}
		}
	}

	/**
	 * <p>Applies delay on given DBM for one clock.</p>
	 * @param dbm DBM to modify
	 * @param clockIndex clock to delay
	 */
	public static void delay(short[] dbm, int clockIndex) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		if ( dbm[clockIndex *(nbClocks+1) + clockIndex] != DBMInternals.EMPTINESS) {
			dbm[clockIndex*(nbClocks+1)] = DBMInternals.INFINITE;
		}
	}

	public static void set(short[] dbm, int nbClocks, int clockIndex, int value) {
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( i==clockIndex ) {
				dbm[i*(nbClocks+1) + i] = DBMInternals.CONSTRAIN_LESS_OR_EQUAL;
			} else {
				// if clock i is active
				if ( dbm[i*(nbClocks+1) + i] != DBMInternals.EMPTINESS) {
					dbm[clockIndex*(nbClocks+1) + i] = DBMInternals.add(DBMInternals.computeInternal(value, false), dbm[i]);
					dbm[i*(nbClocks+1) + clockIndex] = DBMInternals.add(DBMInternals.computeInternal(-value, false), dbm[i*(nbClocks+1)]);
				}
			}
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
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		set(dbm, nbClocks, clockIndex, value);
	}

	/**
	 * <p>Returns true if clock is enabled.</p>
	 * @param dbm DBM to test.
	 * @param clockIndex clock index to test
	 * @return true if clock is enable.
	 */
	public static boolean isEnabled(short[] dbm, int clockIndex) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		return dbm[clockIndex*(nbClocks+1) + clockIndex] != DBMInternals.EMPTINESS;
	}

	/**
	 * <p>Enables a clock and releases all constrains on it.</p>
	 * <p>This operator <b>does NOT</b> preserves the canonical form.</p>
	 * @param dbm DBM to modify
	 * @param clockIndex clock to enable.
	 */
	public static void enable(short[] dbm, int clockIndex) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( i==clockIndex ) {
				dbm[i*(nbClocks+1) + i] = DBMInternals.CONSTRAIN_LESS_OR_EQUAL;
			} else {
				if ( dbm[i*(nbClocks+1) + i] != DBMInternals.EMPTINESS) {
					dbm[clockIndex*(nbClocks+1) + i] = DBMInternals.INFINITE;
					dbm[i*(nbClocks+1) + clockIndex] = DBMInternals.INFINITE;
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
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		for ( int i=0; i<=nbClocks; i++ ) {
			if ( i==clockIndex ) {
				dbm[i*(nbClocks+1) + i] = DBMInternals.EMPTINESS;
			} else {
				dbm[clockIndex*(nbClocks+1) + i] = DBMInternals.CONSTRAIN_LESS_OR_EQUAL;
				dbm[i*(nbClocks+1) + clockIndex] = DBMInternals.CONSTRAIN_LESS_OR_EQUAL;
			}
		}
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
			if (v1 != v2 && DBMInternals.lessThan(v2, v1)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEnabled(short[] dbm, int nbClocks, int clockIndex) {
		return dbm[clockIndex*(nbClocks+1) + clockIndex] != DBMInternals.EMPTINESS;
	}

	public static int getNumberOfActiveClocks(short[] dbm) {
		int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
		int nb = 0;
		for ( int i=0; i<=nbClocks; i++ ) {
			if (isEnabled(dbm, nbClocks, i)) {
				nb++;
			}
		}
		return nb;
	}

}
