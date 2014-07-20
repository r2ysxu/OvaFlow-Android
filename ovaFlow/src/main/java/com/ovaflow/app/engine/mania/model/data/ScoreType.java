package com.ovaflow.app.engine.mania.model.data;

import java.io.Serializable;

/**
 * Created by ArthurXu on 05/07/2014.
 */
public class ScoreType implements Serializable {

    public static final int COOL = 100;
    public static final int GOOD = 50;
    public static final int BAD = 30;
    public static final int MISS = -1;

    private int score;

    private int cool, good, bad, miss;

    private int combo = 0;


    public ScoreType(ScoreType st) {
        this.cool = st.cool;
        this.good = st.good;
        this.bad = st.bad;
        this.miss = st.miss;
    }

    public ScoreType() {
        cool = 0;
        good = 0;
        bad = 0;
        miss = 0;
    }

    public void addPts(int pts) {
        switch (pts) {
            case COOL:
                cool++;
                break;
            case GOOD:
                good++;
                break;
            case BAD:
                bad++;
                break;
            case MISS:
                miss++;
                break;
        }
        if (pts != MISS) {
            combo++;
        }
    }

    public int getRank() {
        if (score < getTotal() * 40) return 0;
        else if (score < getTotal() * 80) return 1;
        else if (score < getTotal() * 120) return 2;
        else if (score < getTotal() * 180) return 3;
        else if (score < getTotal() * 300) return 4;
        else if (score < getTotal() * 480) return 5;
        else return 6;
    }

    public void add(ScoreType st) {
        this.cool += st.cool;
        this.good += st.good;
        this.bad += st.bad;
        this.miss += st.miss;
    }

    public void addScore(int pts) {
        score += pts;
    }

    public int getTotal() {
       return cool + good + bad + miss;
    }

    public int getPts() {
        return cool * COOL + good * GOOD + bad * BAD;
    }

    public int getScore() {
        return score;
    }

    public int getCombo() {
        return combo;
    }

    public void resetCombo() {
        combo = 0;
    }

    public int getCool() {
        return cool;
    }

    public int getGood() {
        return good;
    }

    public int getBad() {
        return bad;
    }

    public int getMiss() {
        return miss;
    }
}
