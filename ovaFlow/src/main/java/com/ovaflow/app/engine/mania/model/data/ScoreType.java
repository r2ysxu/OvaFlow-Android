package com.ovaflow.app.engine.mania.model.data;

import java.io.Serializable;

/**
 * Created by ArthurXu on 05/07/2014.
 */
public class ScoreType implements Serializable {

    public static final int COOL = 100;
    public static final int GOOD = 50;
    public static final int BAD = 30;
    public static final int MISS = 0;

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
        if (pts != MISS)
            combo++;
    }

    public int getScore() {
        return COOL * cool + GOOD * good + BAD * bad;
    }

    public int getCombo() {
        return combo;
    }

    public void resetCombo() {
        combo = 0;
    }
}
