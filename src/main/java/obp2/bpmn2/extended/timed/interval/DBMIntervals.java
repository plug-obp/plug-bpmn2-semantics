package obp2.bpmn2.extended.timed.interval;


import obp2.bpmn2.extended.timed.dbm.*;

public class DBMIntervals {
    private DBMIntervals() {}

    static public boolean checkGuard(int nbClocks, short[] dbm, int clockIndex, Interval guard) {
        // Checking if min value of clock is indeed below or equal to max value of guard
        short minInternal = DBMInternals.getMinInternal(dbm, nbClocks, clockIndex);
        int minValue = -DBMInternals.getValue(minInternal);
        if (minValue > guard.getMaxValue()) {
            return false;
        }
        if (minValue == guard.getMaxValue()) {
            boolean minStrict = DBMInternals.isStrict(minInternal);
            if (!guard.isMaxIncluded() || minStrict) {
                return false;
            }
        }
        // Checking if max value of clock is indeed above or equal to min value of guard
        short maxInternal = DBMInternals.getMaxInternal(dbm, nbClocks, clockIndex);
        int maxValue = DBMInternals.getValue(maxInternal);
        if (maxValue < guard.getMinValue()) {
            return false;
        }
        if (maxValue == guard.getMinValue()) {
            boolean maxStrict = DBMInternals.isStrict(maxInternal);
            return guard.isMinIncluded() && !maxStrict;
        }
        return true;
    }

    static public void applyGuard(int nbClocks, short[] dbm, int clockIndex, Interval guard) {
        if (!guard.isMinIncluded() || guard.getMinValue() != 0) {
            DBMOps.andGuard(nbClocks, dbm,
                    0, clockIndex,
                    -guard.getMinValue(), !guard.isMinIncluded()
            );
        }
        if (guard.getMaxValue() != Interval.POS_INFINITY) {
            DBMOps.andGuard(nbClocks, dbm,
                    clockIndex, 0,
                    guard.getMaxValue(), !guard.isMaxIncluded()
            );
        }
    }

    static public void applyInvariant(int nbClocks, short[] dbm, int clockIndex, Interval invariant) {
        // Apply min bound invariant (if different from included 0)
        if (!invariant.isMinIncluded() || invariant.getMinValue() != 0) {
            DBMOps.andInvariant(dbm, 0, clockIndex,
                    -invariant.getMinValue(), !invariant.isMinIncluded()
            );
        }
        // Apply max bound invariant (if different from excluded infinity)
        if (invariant.getMaxValue() != Interval.POS_INFINITY) {
            DBMOps.andInvariant(dbm, clockIndex, 0,
                    invariant.getMaxValue(), !invariant.isMaxIncluded()
            );
        }
    }

    static public boolean checkInInterval(int nbClocks, short[] dbm, int clockIndex, Interval interval) {
        // Checking if min value of clock is indeed above or equal to min value of interval
        short minInternal = DBMInternals.getMinInternal(dbm, nbClocks, clockIndex);
        int minValue = -DBMInternals.getValue(minInternal);
        if (minValue < interval.getMinValue()) {
            return false;
        }
        if (minValue == interval.getMinValue()) {
            boolean minStrict = DBMInternals.isStrict(minInternal);
            if (!minStrict && !interval.isMinIncluded()) {
                return false;
            }
        }
        // Checking if max value of clock is indeed below or equal to max value of guard
        short maxInternal = DBMInternals.getMaxInternal(dbm, nbClocks, clockIndex);
        int maxValue = DBMInternals.getValue(maxInternal);
        if (maxValue > interval.getMaxValue()) {
            return false;
        }
        if (maxValue == interval.getMaxValue()) {
            boolean maxStrict = DBMInternals.isStrict(maxInternal);
            return maxStrict || interval.isMaxIncluded();
        }
        return true;
    }

}
