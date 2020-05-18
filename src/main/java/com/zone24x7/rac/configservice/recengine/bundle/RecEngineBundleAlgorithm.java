package com.zone24x7.rac.configservice.recengine.bundle;

import com.zone24x7.rac.configservice.recengine.algorithm.RecEngineAlgorithm;

public class RecEngineBundleAlgorithm {

    private int rank;
    private RecEngineAlgorithm algorithm;

    public RecEngineBundleAlgorithm() {
    }

    public RecEngineBundleAlgorithm(int rank, RecEngineAlgorithm algorithm) {
        this.rank = rank;
        this.algorithm = algorithm;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public RecEngineAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(RecEngineAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
