package obp2.bpmn2.extended.timed.dbm;

import java.util.Arrays;

public class DBMInternals {

    private final static short CONSTRAIN_MASK = 1;
    private final static short CONSTRAIN_LESS = 1;

    public final static short CONSTRAIN_LESS_OR_EQUAL = 0;
    public final static short INFINITE = Short.MAX_VALUE;
    public final static short EMPTINESS = computeInternal(-1, false);

    /**
     * <p>This integer array stores the correspondancies between the number of clocks
     * and the dbm array size (#clocks*#clocks = dbm array size).
     * With this array, a configuration doesn't need to
     * store the number of clock for a dbm, it can be quickly inferred by
     * {@link #getNumberOfClocks(int)}.</p>
     */
    private final static int[] NB_CLOCK_CORRESPONDENCES = new int[]
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

    /** <p>Searches for number of clock from dbm array length.</p> */
    public static int getNumberOfClocks(int length) {
        if (length <= 250000) {
            return Arrays.binarySearch(NB_CLOCK_CORRESPONDENCES, length);
        }
        else {
            //if ever more than 500 clocks, we do not have any more the entries precomputed
            return ((int) Math.sqrt(length) - 1);
        }
    }

    public static int getNumberOfClocks(short[] dbm) {
        return getNumberOfClocks(dbm.length);
    }

    public static final int computeSize(int nbClocks) {
        return (nbClocks + 1) * (nbClocks + 1);
    }

    public static int computeIndex(int nbClocks, int i, int j) {
        return i*(nbClocks+1) + j;
    }

    public static short getInternal(short[] dbm, int nbClocks, int i, int j) {
        return dbm[computeIndex(nbClocks, i, j)];
    }

    public static boolean isEmpty(short[] dbm, int nbClocks, int i) {
        return getInternal(dbm, nbClocks, i, i) == DBMInternals.EMPTINESS;
    }

    public static short getMaxInternal(short[] dbm, int nbClocks, int i) {
        return getInternal(dbm, nbClocks, i, 0);
    }

    public static short getMinInternal(short[] dbm, int nbClocks, int i) {
        return getInternal(dbm, nbClocks, 0, i);
    }

    public static short getMaxInternal(short[] dbm, int nbClocks, int i, int j) {
        return getInternal(dbm, nbClocks, i, j);
    }

    public static short getMinInternal(short[] dbm, int nbClocks, int i, int j) {
        return getInternal(dbm, nbClocks, j, i);
    }

    public static void setInternal(short[] dbm, int nbClocks, int i, int j, short internal) {
        dbm[computeIndex(nbClocks, i, j)] = internal;
    }

    public static void setInternal(short[] dbm, int nbClocks, int i, int j, int value, boolean strict) {
        short internal = DBMInternals.computeInternal(value, strict);
        setInternal(dbm, nbClocks, i, j, internal);
    }

    public static void setInternal(short[] dbm, int i, int j, int value, boolean strict) {
        int nbClocks = getNumberOfClocks(dbm);
        setInternal(dbm, nbClocks, i, j, value, strict);
    }

    /**
     * <p>Computes internal representation for value and constrain type.</p>
     * @param value value to encode.
     * @param strict type of constrain to encode. If true, it represent '<', '<=' otherwise.
     * @return the internal representation.
     */
    public static short computeInternal(int value, boolean strict) {
        return (short) (value << 1 | (strict ? CONSTRAIN_LESS : CONSTRAIN_LESS_OR_EQUAL));
    }

    /**
     * <p>Extracts the constrain value for given internal representation.<p>
     * @param internal representation to extract constrain value from.
     * @return the constrain value.
     */
    public static int getValue(int internal) {
        return internal >> 1;
    }

    /**
     * <p>Extracts strict state for constrain from given internal representation.</p>
     * @param internal representation to extract constrain state from.
     * @return the strict state.
     */
    public static boolean isStrict(int internal) {
        return (internal& CONSTRAIN_MASK) == CONSTRAIN_LESS;
    }

    /**
     * <p>Compares two encoded values: <code>internal1 < internal1</code>. It
     * takes the strict state in account.</p>
     * @param internal1 first encoded value.
     * @param internal2 second encoded value.
     * @return true if internal1 is less than internal2.
     */
    public static boolean lessThan(int internal1, int internal2) {
        if ( internal1 == DBMInternals.INFINITE) return false;
        int value1 = DBMInternals.getValue(internal1);
        int value2 = DBMInternals.getValue(internal2);
        if ( value1 == value2 ) {
            // values are equals, internal1 < internal2 if and only if:
            // -> internal1 is strict and internal2 is NOT.
            return DBMInternals.isStrict(internal1) && !DBMInternals.isStrict(internal2);
        }
        return value1 < value2;
    }

    public static boolean lessOrEqual(int internal1, int internal2) {
        if (internal1 == internal2) return true;
        if (internal1 == DBMInternals.INFINITE) return false;
        int value1 = DBMInternals.getValue(internal1);
        int value2 = DBMInternals.getValue(internal2);
        if (value1 == value2) {
            return DBMInternals.isStrict(internal1) || !DBMInternals.isStrict(internal2);
        }
        return value1 < value2;
    }

    /**
     * <p>Returns the minimum of two encoded values. It takes the strict state
     * in account.</p>
     * @param internal1 first encoded value.
     * @param internal2 second encoded value.
     * @return the minimum of the two.
     * @see DBMInternals#lessThan(int, int)
     */
    public static short min(short internal1, short internal2) {
        return lessThan(internal1, internal2) ? internal1 : internal2;
    }

    /**
     * <p>Adds the two encoded values, taking in account strict states.</p>
     * @param internal1 first encoded value.
     * @param internal2 second encoded value.
     * @return the addition of the two encoded values.
     */
    public static short add(short internal1, short internal2) {
        if ( internal1 == DBMInternals.INFINITE || internal2 == DBMInternals.INFINITE) return DBMInternals.INFINITE;
        return DBMInternals.computeInternal(
                DBMInternals.getValue(internal1) + DBMInternals.getValue(internal2),
                DBMInternals.isStrict(internal1) || DBMInternals.isStrict(internal2)
        );
    }

}
