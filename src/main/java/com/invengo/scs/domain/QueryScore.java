package com.invengo.scs.domain;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/09/13
 * Time: 09:16
 */
public class QueryScore {

    private Score score;

    private Integer max;
    private Integer min;

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "QueryScore{" +
                "score=" + score +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}
