package com.ovaflow.app.engine.mania.model.data;

/**
 * Created by ArthurXu on 05/07/2014.
 */
public class ScoreType {

    private short cool, good, bad, miss;


    public void addCool() { cool += 1; }

    public void addGood() { good += 1; }

    public void addBad() { bad += 1; }

    public void addMiss() { miss += 1; }

    public short getCool() { return cool; }

    public short getGood() { return good; }

    public short getBad() { return bad; }

    public short getMiss() { return miss; }
}
