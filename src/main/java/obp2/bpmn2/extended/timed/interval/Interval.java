package obp2.bpmn2.extended.timed.interval;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public class Interval {

    static public final int NEG_INFINITY = Integer.MIN_VALUE;
    static public final int POS_INFINITY = Integer.MAX_VALUE;

    private final int minValue;
    private final boolean minIncluded;
    private final int maxValue;
    private final boolean maxIncluded;

    public Interval(int minValue, boolean minIncluded, int maxValue, boolean maxIncluded) {
        if (minValue == POS_INFINITY)
            throw new IllegalArgumentException("Intervals can't have positive infinity minimum");
        if (maxValue == NEG_INFINITY)
            throw new IllegalArgumentException("Intervals can't have negative infinity maximum");
        if ((maxValue == POS_INFINITY && maxIncluded) || (minValue == NEG_INFINITY && minIncluded))
            throw new IllegalArgumentException("Intervals can't include infinity");
        this.minValue = minValue;
        this.minIncluded = minIncluded;
        this.maxValue = maxValue;
        this.maxIncluded = maxIncluded;
    }

    public Interval(Interval other) {
        this(other.getMinValue(), other.isMinIncluded(), other.getMaxValue(), other.isMaxIncluded());
    }

    @JsonCreator
    public Interval(String jsonString) {
        this(Intervals.parseInterval(jsonString));
    }

    public int getMinValue() {
        return minValue;
    }

    public boolean isMinIncluded() {
        return minIncluded;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public boolean isMaxIncluded() {
        return maxIncluded;
    }

    public boolean isMinInfinity() {
        return minValue == NEG_INFINITY;
    }

    public boolean isMaxInfinity() {
        return maxValue == POS_INFINITY;
    }

    public boolean isEmpty() {
        if (maxValue == minValue && (!minIncluded || !maxIncluded)) return true;
        if (minValue > maxValue) return true;
        return false;
    }

    public boolean includes(int value) {
        if (isEmpty()) return false;
        if (minIncluded && minValue == value) return true;
        if (maxIncluded && maxValue == value) return true;
        return value > minValue && value < maxValue;
    }

    public boolean includes(Interval other) {
        if (other.isEmpty()) return true;
        if (this.isEmpty()) return false;
        if (other.getMinValue() < this.getMinValue()) return false;
        if (other.getMaxValue() > this.getMaxValue()) return false;
        if (other.isMinIncluded() && !this.includes(other.getMinValue())) return false;
        if (other.isMaxIncluded() && !this.includes(other.getMaxValue())) return false;
        return true;
    }

    public Interval inter(Interval other) {
        if (this.includes(other)) return this;
        if (other.includes(this)) return other;
        if (this.getMaxValue() == other.getMinValue()) {
            if (!this.isMaxIncluded() || !other.isMinIncluded()) return Intervals.EMPTY;
            return Intervals.exact(this.getMaxValue());
        }
        if (this.getMinValue() == other.getMaxValue()) {
            if (!this.isMinIncluded() || !other.isMaxIncluded()) return Intervals.EMPTY;
            return Intervals.exact(this.getMinValue());
        }
        if (this.getMaxValue() < other.getMinValue()) return Intervals.EMPTY;
        if (this.getMinValue() > other.getMaxValue()) return Intervals.EMPTY;
        Interval minHolder = this.includes(other.getMinValue()) ? other : this;
        Interval maxHolder = (this == minHolder) ? other : this;
        return new Interval(
                minHolder.getMinValue(), minHolder.isMinIncluded(),
                maxHolder.getMaxValue(), maxHolder.isMaxIncluded()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval that = (Interval) o;
        return minValue == that.minValue && minIncluded == that.minIncluded &&
                maxValue == that.maxValue && maxIncluded == that.maxIncluded;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minValue, minIncluded, maxValue, maxIncluded);
    }

    @Override
    public String toString() {
        return Intervals.toString(this);
    }

    @JsonValue
    protected String toJsonString() {
        return Intervals.toString(this);
    }

}
