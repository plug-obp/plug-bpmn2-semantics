package obp2.bpmn2.extended.timed.dbm;

import java.util.Arrays;

public class DBMCreate {

    /**
     * <p>Creates an unconstrained DBM with all clocks enable.</p>
     * @param nbClocks number of clocks for DBM (not including the zero clock).
     * @return a newly allocated DBM with no constrain.
     */
    public static short[] allUnconstrained(int nbClocks) {
        short[] dbm = new short[DBMInternals.computeSize(nbClocks)];
        Arrays.fill(dbm, DBMInternals.INFINITE);
        for ( int i=0; i<dbm.length; i+= nbClocks+2) {
            dbm[i] = DBMInternals.CONSTRAIN_LESS_OR_EQUAL;
        }
        return dbm;
    }

    /**
     * <p>Creates a DBM where all clock are equals to zero and enable.</p>
     * @param nbClocks number of clocks for DBM (not including the zero clock).
     * @return a newly allocated DBM with no constrain.
     */
    public static short[] allZero(int nbClocks) {
        short[] dbm = new short[DBMInternals.computeSize(nbClocks)];
        Arrays.fill(dbm, DBMInternals.CONSTRAIN_LESS_OR_EQUAL);
        return dbm;
    }

    /**
     * <p>Creates  DBM where all clocks are disabled.</p>
     * @param nbClocks number of clocks for DBM (not including the zero clock).
     * @return a newly allocated DBM with no constrain.
     */
    public static short[] allDisabled(int nbClocks) {
        short[] dbm = new short[DBMInternals.computeSize(nbClocks)];
        Arrays.fill(dbm, DBMInternals.CONSTRAIN_LESS_OR_EQUAL);
        for ( int i=1; i<=nbClocks; i++) {
            DBMInternals.setInternal(dbm, nbClocks, i, i, DBMInternals.EMPTINESS);
        }
        return dbm;
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
     * <p>Copy a given DBM to a new one of potentially different size and/or different ordering of clocks.
     * It is meant to be used primarily when the DBM size varies.
     * Use cases include timed semantics with dynamic instantiation.</p>
     *
     * <p>The parameter <code>newPositions</code> (array of ints) drives the copy.
     * A clock of index <code>i</code> in the previous DBM will be moved to position <code>newPositions[i-1]</code>.
     * Thus, the array <code>newPositions</code> must be of length equal to the number of clocks in the DBM to copy.
     * Actual positions (if clock is not meant to be removed) range from 1 to <code>newNbClocks</code>.
     * If a position is equal or inferior to 0, the clock is removed.</p>
     *
     * <p>The parameter <code>nextNbClocks</code> defines the number of clocks in the returned DBM.
     * Thus, indexes in <code>newPositions</code> that are superior or equal to <code>1</code>
     * must also be inferior or equal to <code>nextNbClocks</code>.</p>
     *
     * <p>Clock with indexes that are not in <code>newPositions</code> are all created disabled.</p>
     *
     * <p>Arguments are not checked for coherency,
     * see and use {@link DBMCreate#safeOrderDrivenCopy} if needed (slower).</p>
     *
     * @param previousDBM The DBM to copy.
     * @param previousNbClocks The previous number of clocks.
     * @param nextNbClocks The next number of clocks.
     * @param newPositions The array describing the reordering.
     * @return The new DBM.
     */
    public static short[] orderDrivenCopy(
            short[] previousDBM, int previousNbClocks, int nextNbClocks, int[] newPositions
    ) {
        short[] nextDBM = allDisabled(nextNbClocks);
        nextDBM[0] = previousDBM[0];
        for (int i = 1; i <= previousNbClocks; i++) {
            int nextI = newPositions[i-1];
            if (nextI < 1) continue;
            short previousInternal;
            // (i, 0) -> (nextI, 0)
            previousInternal = DBMInternals.getInternal(previousDBM, previousNbClocks, i, 0);
            DBMInternals.setInternal(nextDBM, nextNbClocks, nextI, 0, previousInternal);
            // (0, i) -> (0, nextI)
            previousInternal = DBMInternals.getInternal(previousDBM, previousNbClocks, 0, i);
            DBMInternals.setInternal(nextDBM, nextNbClocks, 0, nextI, previousInternal);
            // (i, i) -> (nextI, nextI)
            previousInternal = DBMInternals.getInternal(previousDBM, previousNbClocks, i, i);
            DBMInternals.setInternal(nextDBM, nextNbClocks, nextI, nextI, previousInternal);
            for (int j = i+1; j <= previousNbClocks; j++) {
                int nextJ = newPositions[j-1];
                if (nextJ < 1) continue;
                // (i, j) -> (nextI, nextJ)
                previousInternal = DBMInternals.getInternal(previousDBM, previousNbClocks, i, j);
                DBMInternals.setInternal(nextDBM, nextNbClocks, nextI, nextJ, previousInternal);
                // (j, i) -> (nextJ, nextI)
                previousInternal = DBMInternals.getInternal(previousDBM, previousNbClocks, j, i);
                DBMInternals.setInternal(nextDBM, nextNbClocks, nextJ, nextI, previousInternal);
            }
        }
        return nextDBM;
    }

    /**
     * <p>Behaves the same as {@link DBMCreate#orderDrivenCopy} (see for details)
     * but compute <code>previousNbClocks</code> and checks arguments coherency.
     * An illegal argument exception is raised if appropriate:
     * <ul>
     *     <li>Parameter <code>newPositions</code> must be of length equal to previous number of clocks.</li>
     *     <li>New indexes must be inferior or equal to parameter <code>nextNbClocks</code>.</li>
     *     <li>Two different clocks can not be copied into the same index.</li>
     * </ul></p>
     *
     * <p>It is significantly slower than {@link DBMCreate#orderDrivenCopy}.</p>
     *
     *
     * @param previousDBM The DBM to copy.
     * @param nextNbClocks The next number of clocks.
     * @param newPositions The array describing the reordering.
     * @return The new DBM.
     */
    public static short[] safeOrderDrivenCopy(short[] previousDBM, int nextNbClocks, int[] newPositions) {
        final int previousNbClocks = DBMInternals.getNumberOfClocks(previousDBM);
        if (newPositions.length != previousNbClocks) {
            throw new IllegalArgumentException("newPositions must be of length equal to previous number of clocks");
        }
        boolean[] marks = new boolean[nextNbClocks];
        for (int index : newPositions) {
            if (index > nextNbClocks) {
                throw new IllegalArgumentException("New indexes must be inferior or equal to nextNbClocks");
            } else if (index > 0) {
                if (marks[index-1]) {
                    throw new IllegalArgumentException("Two different clocks can not be copied into the same index");
                } else {
                    marks[index-1] = true;
                }
            }
        }
        return orderDrivenCopy(previousDBM, previousNbClocks, nextNbClocks, newPositions);
    }

}
