package com.zone24x7.rac.configservice.recengine.rec;

import java.util.List;

public class RecEngineRecList {

    private List<RecEngineRec> recs;

    public RecEngineRecList() {
    }

    public RecEngineRecList(List<RecEngineRec> recs) {
        this.recs = recs;
    }

    public List<RecEngineRec> getRecs() {
        return recs;
    }

    public void setRecs(List<RecEngineRec> recs) {
        this.recs = recs;
    }
}
