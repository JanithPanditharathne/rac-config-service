package com.zone24x7.rac.configservice.rec;

import java.util.List;

public class RecList {

    private List<RecDetail> recs;

    public RecList() {
    }

    public RecList(List<RecDetail> recs) {
        this.recs = recs;
    }

    public List<RecDetail> getRecs() {
        return recs;
    }

    public void setRecs(List<RecDetail> recs) {
        this.recs = recs;
    }
}
