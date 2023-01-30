package obp2.bpmn2.extended.timed.dbm;

public class DBMEval {

    public static boolean evaluateConstraint(short[] dbm, String constraint) {
        return false;
    }

    public static boolean isClockLessThan(short[] dbm, int clockID, int bound) {
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        if (dbm[(clockID*(nbClocks+1))+clockID] == DBMInternals.EMPTINESS) return false;

        int maxiInternal = dbm[clockID * (nbClocks + 1)];
        return bound == DBMInternals.getValue(maxiInternal) && DBMInternals.isStrict(maxiInternal);
    }
    public static boolean isClockLessOrEqualThan(short[] dbm, int clockID, int bound) {
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        if (dbm[(clockID*(nbClocks+1))+clockID] == DBMInternals.EMPTINESS) return false;

        int maxiInternal = dbm[clockID * (nbClocks + 1)];
        return bound == DBMInternals.getValue(maxiInternal) && !DBMInternals.isStrict(maxiInternal);
    }
    public static boolean isClockGreaterThan(short[] dbm, int clockID, int bound) {
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        if (dbm[(clockID*(nbClocks+1))+clockID] == DBMInternals.EMPTINESS) return false;

        int miniInternal = dbm[clockID];
        return -bound == DBMInternals.getValue(miniInternal) && DBMInternals.isStrict(miniInternal);
    }
    public static boolean isClockGreaterOrEqualThan(short[] dbm, int clockID, int bound) {
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        if (dbm[(clockID*(nbClocks+1))+clockID] == DBMInternals.EMPTINESS) return false;

        int miniInternal = dbm[clockID];
        return -bound == DBMInternals.getValue(miniInternal) && !DBMInternals.isStrict(miniInternal);
    }

    public static boolean isClockDifferenceLessThan(short[] dbm, int i, int j, int bound) {
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        int maxiInternal = dbm[(i*(nbClocks+1))+j];

        return bound == DBMInternals.getValue(maxiInternal) && DBMInternals.isStrict(maxiInternal);
    }
    public static boolean isClockDifferenceLessOrEqualThan(short[] dbm, int i, int j, int bound) {
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        int maxiInternal = dbm[(i*(nbClocks+1))+j];

        return bound == DBMInternals.getValue(maxiInternal) && !DBMInternals.isStrict(maxiInternal);
    }
    public static boolean isClockDifferenceGreaterThan(short[] dbm, int i, int j, int bound) {
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        int miniInternal = dbm[(j*(nbClocks+1))+i];

        return -bound == DBMInternals.getValue(miniInternal) && DBMInternals.isStrict(miniInternal);
    }
    public static boolean isClockDifferenceGreaterOrEqualThan(short[] dbm, int i, int j, int bound) {
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        int miniInternal = dbm[(j*(nbClocks+1))+i];

        return -bound == DBMInternals.getValue(miniInternal) && !DBMInternals.isStrict(miniInternal);
    }

}
