package obp2.bpmn2.extended.timed.dbm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DBMPrinter {

    /**
     * <p>Creates a textual representation of the DBM. Only for debug.</p>
     * @param dbm DBM to print.
     * @return a String that represent the DBM.
     */
    public static String toString(short[] dbm) {
        if ( dbm == null ) return "No clock constrains.";
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
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
                if (i == j && internal == DBMInternals.EMPTINESS) {
                    builder.append("  X ");
                } else if (internal == DBMInternals.INFINITE) {
                    builder.append(" oo ");
                } else {
                    builder.append(DBMInternals.isStrict(internal) ? " <" : "<=");
                    int value = DBMInternals.getValue(internal);
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
        if ( DBMOps.isEmpty(dbm) ) return "empty";
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        StringBuilder builder = new StringBuilder();
        for ( int i=1; i<=nbClocks; i++) {
            if ( dbm[(i*(nbClocks+1))+i] != DBMInternals.EMPTINESS) {
                StringBuilder constrain = new StringBuilder();
                int miniInternal = dbm[i];
                if ( miniInternal != DBMInternals.INFINITE) {
                    constrain.append(-DBMInternals.getValue(miniInternal));
                    constrain.append(DBMInternals.isStrict(miniInternal) ? " < " : " <= ");
                }
                constrain.append("c");
                constrain.append(i);
                int maxiInternal = dbm[i*(nbClocks+1)];
                if ( maxiInternal != DBMInternals.INFINITE) {
                    constrain.append(DBMInternals.isStrict(maxiInternal) ? " < " : " <= ");
                    constrain.append(DBMInternals.getValue(maxiInternal));
                }
                if ( constrain.length() > 4 ) {
                    if ( builder.length() > 0 ) builder.append(" & ");
                    builder.append(constrain);
                }
            }
        }

        for ( int i=1; i<=nbClocks; i++ ) {
            if ( dbm[(i*(nbClocks+1))+i] != DBMInternals.EMPTINESS) {
                for ( int j=i+1; j<=nbClocks; j++) {
                    if ( dbm[(j*(nbClocks+1))+j] != DBMInternals.EMPTINESS) {
                        StringBuilder constrain = new StringBuilder();
                        boolean atLeastOneBound = false;
                        int miniInternal = dbm[(j*(nbClocks+1))+i];
                        if ( miniInternal != DBMInternals.INFINITE) {
                            atLeastOneBound = true;
                            constrain.append(-DBMInternals.getValue(miniInternal));
                            constrain.append(DBMInternals.isStrict(miniInternal) ? " < " : " <= ");
                        }
                        constrain.append("c");
                        constrain.append(i);
                        constrain.append(" - c");
                        constrain.append(j);
                        int maxiInternal = dbm[(i*(nbClocks+1))+j];
                        if ( maxiInternal != DBMInternals.INFINITE) {
                            atLeastOneBound = true;
                            constrain.append(DBMInternals.isStrict(maxiInternal) ? " < " : " <= ");
                            constrain.append(DBMInternals.getValue(maxiInternal));
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

    public static String toString(short[] dbm, int nbClocks, int clockIndex, String clockName) {
        if (DBMInternals.isEmpty(dbm, nbClocks, clockIndex)) return clockName + " has no value";
        StringBuilder builder = new StringBuilder();
        int miniInternal = DBMInternals.getMinInternal(dbm, nbClocks, clockIndex);
        if ( miniInternal != DBMInternals.INFINITE) {
            builder.append(-DBMInternals.getValue(miniInternal))
                    .append(DBMInternals.isStrict(miniInternal) ? " < " : " <= ");
        }
        builder.append(clockName);;
        int maxiInternal = DBMInternals.getMaxInternal(dbm, nbClocks, clockIndex);
        if ( maxiInternal != DBMInternals.INFINITE) {
            builder.append(DBMInternals.isStrict(maxiInternal) ? " < " : " <= ")
                    .append(DBMInternals.getValue(maxiInternal));
        }
        return builder.toString();
    }

    public static String toString(short[] dbm, int nbClocks, int c1, int c2, String c1Name, String c2Name) {
        String differenceName = c1Name + " - " +c2Name;
        if (DBMInternals.isEmpty(dbm, nbClocks, c1) || DBMInternals.isEmpty(dbm, nbClocks, c2)) {
            return "(" + differenceName + ") has no value";
        }
        StringBuilder builder = new StringBuilder();
        boolean atLeastOneBound = false;
        int miniInternal = DBMInternals.getMinInternal(dbm, nbClocks, c1, c2);
        if ( miniInternal != DBMInternals.INFINITE) {
            atLeastOneBound = true;
            builder.append(-DBMInternals.getValue(miniInternal));
            builder.append(DBMInternals.isStrict(miniInternal) ? " < " : " <= ");
        }
        builder.append(differenceName);
        int maxiInternal = DBMInternals.getMaxInternal(dbm, nbClocks, c1, c2);
        if ( maxiInternal != DBMInternals.INFINITE) {
            atLeastOneBound = true;
            builder.append(DBMInternals.isStrict(maxiInternal) ? " < " : " <= ");
            builder.append(DBMInternals.getValue(maxiInternal));
        }
        if (!atLeastOneBound) {
            return "(" + differenceName + ") can be anything";
        }
        return builder.toString();


    }

    /**
     * <p>Returns a String that expresses the DBM as a graph in the trivial graph format.</p>
     * @param dbm DBM to print.
     * @return a String that represent the DBM as TGF.
     */
    public static String toTGF(short[] dbm) {
        if ( dbm == null ) return "";
        if ( DBMOps.isEmpty(dbm) ) return "";
        int nbClocks = DBMInternals.getNumberOfClocks(dbm.length);
        StringBuilder nodeBuilder = new StringBuilder();
        StringBuilder edgeBuilder = new StringBuilder();

        for ( int i=0; i<=nbClocks; i++ ) {
            if ( DBMOps.isEnabled(dbm, nbClocks, i) ) {
                nodeBuilder.append(i + " " + i + "\n");
                for ( int j=i+1; j<=nbClocks; j++) {
                    if (  DBMOps.isEnabled(dbm, nbClocks, j) ) {

                        int miniInternal = dbm[(j*(nbClocks+1))+i];
                        if ( miniInternal != DBMInternals.INFINITE) {
                            edgeBuilder.append(j + " " + i);
                            edgeBuilder.append(DBMInternals.isStrict(miniInternal) ? " > " : " >= ");
                            edgeBuilder.append(-DBMInternals.getValue(miniInternal) + "\n");
                        }



                        int maxiInternal = dbm[(i*(nbClocks+1))+j];
                        if ( maxiInternal != DBMInternals.INFINITE) {
                            edgeBuilder.append(i + " " + j);
                            edgeBuilder.append(DBMInternals.isStrict(maxiInternal) ? " < " : " <= ");
                            edgeBuilder.append(DBMInternals.getValue(maxiInternal) + "\n");
                        }
                    }
                }
            }
        }

        return nodeBuilder.toString() + "\n#\n" + edgeBuilder.toString();
    }

    public static void toFileTFG(short[] dbm, int id) {
        String tgfString = toTGF(dbm);
        Path path = Paths.get("dbm" + id + ".tgf").toAbsolutePath();
        try {
            path.toFile().createNewFile();
            Files.write(path, tgfString.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
