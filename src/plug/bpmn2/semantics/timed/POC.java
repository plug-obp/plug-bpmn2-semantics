package plug.bpmn2.semantics.timed;

import plug.dbm.DBM;

import java.util.LinkedList;
import java.util.List;

public class POC {

    static public Integer INFINITY = null;

    static public class Bound {
        public final int number;
        public final boolean included;

        public Bound(int number, boolean included) {
            this.number = number;
            this.included = included;
        }

        static public Bound ZERO = new Bound(0, true);
        static public Bound INFINITY = new Bound(POC.INFINITY, false);
        static public Bound INCLUDED(int number) {
            return new Bound(number, true);
        }
        static public Bound EXCLUDED(int number) {
            return new Bound(number, false);
        }
    }

    static public class Interval {
        public Bound min;
        public Bound max;

        public Interval(Bound min, Bound max) {
            this.min = min;
            this.max = max;
        }

        static public Interval URGENT = new Interval(Bound.ZERO, Bound.ZERO);
        static public Interval DEFAULT = new Interval(Bound.ZERO, Bound.INFINITY);
    }

    static public class Model {

        static public class Lane {
            private List<Interval> intervalList;

            public Lane() {
                intervalList = new LinkedList<>();
            }

            public List<Interval> getIntervalList() {
                return intervalList;
            }
        }

        private List<Lane> laneList;

        public Model() {
            this.laneList = new LinkedList<>();
        }

        public List<Lane> getLaneList() {
            return laneList;
        }
    }

    static public class State {

        static public class LaneState {
            private Model.Lane ref;
            private int activeIntervalIndex = -1;

            public LaneState(Model.Lane ref) {
                this.ref = ref;
            }

            public Model.Lane getRef() {
                return ref;
            }

            public int getActiveIntervalIndex() {
                return activeIntervalIndex;
            }

        }

        private DBM dbm;
        private List<LaneState> laneStateList;

    }

}
