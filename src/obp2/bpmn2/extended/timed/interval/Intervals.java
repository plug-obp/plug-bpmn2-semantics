package obp2.bpmn2.extended.timed.interval;

public class Intervals {

    private Intervals() {}

    static public final Interval EMPTY = new Interval(0, false, 0, false);

    static public Interval exact(int value) {
        if (value == Interval.NEG_INFINITY || value == Interval.POS_INFINITY) return EMPTY;
        return new Interval(value, true, value, true);
    }

    static public Interval between(int min, boolean minIncluded, int max, boolean maxIncluded) {
        return new Interval(min, minIncluded, max, maxIncluded);
    }

    static public Interval atLeast(int minValue, boolean included) {
        if (minValue == Interval.POS_INFINITY) return EMPTY;
        return new Interval(
                minValue, included && !(minValue == Interval.NEG_INFINITY),
                Interval.POS_INFINITY, false
        );
    }

    static public Interval atMost(int maxValue, boolean included) {
        if (maxValue == Interval.NEG_INFINITY) return EMPTY;
        return new Interval(
                Interval.NEG_INFINITY, false,
                maxValue, included && !(maxValue == Interval.POS_INFINITY)
        );
    }

    static public Interval le(int value) {
        return atMost(value, true);
    }

    static public Interval lt(int value) {
        return atMost(value, false);
    }

    static public Interval ge(int value) {
        return atLeast(value, true);
    }

    static public Interval gt(int value) {
        return atLeast(value, false);
    }

    /**
     * <p>A flexible parsing function. Format rules:
     * <ul>
     *     <li>First and last character are reserved to denote inclusion.
     *     <ul>
     *         <li>If first character is '[', minimum is included.</li>
     *         <li>if last character is ']', maximum is included.</li>
     *         <li>A different character denotes exclusion</li>
     *     </ul>
     *     </li>
     *     <li>Minimum and maximum are separated with a ','.</li>
     *     <li>Spaces are allowed around minimum and maximum.</li>
     *     <li>If minimum (resp. maximum) doesn't parse to an integer,
     *     it is positive (resp. negative) infinity.</li>
     * </ul></p>
     *
     * <p>Examples of correct format:
     * <ul>
     *     <li>"]-1, 1]", "(-1, 1]", "]-1,1]"</li>
     * </ul>
     * </p>
     */
    static public Interval parseInterval(String string) {
        if (string.length() < 3) throw new IllegalArgumentException("string must be length >= 3");
        boolean minIncluded = string.charAt(0) == '[';
        boolean maxIncluded = string.charAt(string.length()-1) == ']';
        String[] minMaxString = string.substring(1, string.length()-1).split(",");
        if (minMaxString.length != 2) throw new IllegalArgumentException("string.split(',') must be length 2");
        String minString = minMaxString[0].trim();
        String maxString = minMaxString[1].trim();
        int min, max;
        try {
            min = Integer.parseInt(minString);
        } catch (NumberFormatException e) {
            min = Interval.NEG_INFINITY;
        }
        try  {
            max = Integer.parseInt(maxString);
        } catch (NumberFormatException e) {
            max = Interval.POS_INFINITY;
        }
        return new Interval(min, minIncluded, max, maxIncluded);
    }

    static public String toString(Interval interval) {
        boolean minIncluded = interval.isMinIncluded();
        int minValue = interval.getMinValue();
        boolean maxIncluded = interval.isMaxIncluded();
        int maxValue = interval.getMaxValue();
        return (minIncluded ? "[" : "]") + (minValue == Interval.NEG_INFINITY ? "oo" : minValue) + "," +
                (maxValue == Interval.POS_INFINITY ? "oo" : maxValue) + (maxIncluded ? "]" : "[");
    }

    static public class Positive {

        private Positive() {}

        static public final Interval ANY_VALUE = new Interval(
                0, true,
                Interval.POS_INFINITY, false
        );

        static public Interval between(int min, boolean minIncluded, int max, boolean maxIncluded) {
            if (min < 0 || max < 0) throw new IllegalArgumentException("Positive value expected");
            return new Interval(min, minIncluded, max, maxIncluded);
        }

        static public Interval exact(int value) {
            return between(value, true, value, true);
        }

        static public Interval atLeast(int value, boolean included) {
            if (value == Interval.POS_INFINITY) return EMPTY;
            return between(value, included, Interval.POS_INFINITY, false);
        }

        static public Interval atMost(int value, boolean included) {
            if (value == Interval.POS_INFINITY) {
                return ANY_VALUE;
            }
            return between(0, true, value, included);
        }

        static public Interval le(int value) {
            return atMost(value, true);
        }

        static public Interval lt(int value) {
            return atMost(value, false);
        }

        static public Interval ge(int value) {
            return atLeast(value, true);
        }

        static public Interval gt(int value) {
            return atLeast(value, false);
        }

    }

}
