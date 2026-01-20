package plug.bpmn2.semantics.state.instance.data;

public enum ActivityState {
    INACTIVE,
    READY,
    ACTIVE,
    COMPLETING,
    COMPLETED,
    WITHDRAWN,
    FAILING,
    FAILED,
    TERMINATING,
    TERMINATED,
    COMPENSATING,
    COMPENSATED,
    CLOSED;

    private static final boolean[][] TRANSITION_MATRIX;

    private static void markTransition(ActivityState source, ActivityState target) {
        TRANSITION_MATRIX[source.ordinal()][target.ordinal()] = true;
    }

    private static void markTransitions(ActivityState source, ActivityState... targets) {
        for (ActivityState target : targets) {
            markTransition(source, target);
        }
    }

    static {
        ActivityState[] states = ActivityState.values();
        int size = states.length;
        TRANSITION_MATRIX = new boolean[size][];
        for (int i = 0; i < size; i++) {
            TRANSITION_MATRIX[i] = new boolean[size];
        }
        for (ActivityState source : states) {
            switch (source) {
                case INACTIVE: markTransitions(source, READY); break;
                case READY: markTransitions(source, ACTIVE, FAILING, TERMINATING, WITHDRAWN); break;
                case ACTIVE: markTransitions(source, COMPLETING, FAILING, TERMINATING, WITHDRAWN); break;
                case COMPLETING: markTransitions(source, COMPLETED, FAILING, TERMINATING); break;
                case COMPLETED: markTransitions(source, CLOSED, COMPENSATING); break;
                case WITHDRAWN: markTransitions(source, CLOSED); break;
                case FAILING: markTransitions(source, FAILED); break;
                case FAILED: markTransitions(source, CLOSED); break;
                case TERMINATING: markTransitions(source, FAILED, TERMINATED); break;
                case TERMINATED: markTransitions(source, CLOSED); break;
                case COMPENSATING: markTransitions(source, COMPENSATED, FAILED, TERMINATED); break;
                case COMPENSATED: markTransitions(source, CLOSED); break;
                case CLOSED: markTransitions(source); break;
            }
        }
    }

    public boolean hasTransitionTo(ActivityState other) {
        return TRANSITION_MATRIX[this.ordinal()][other.ordinal()];
    }

}
