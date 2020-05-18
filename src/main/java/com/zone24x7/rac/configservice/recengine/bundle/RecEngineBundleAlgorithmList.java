package com.zone24x7.rac.configservice.recengine.bundle;

public class RecEngineBundleAlgorithmList {

    private int rank;
    private RecEngineBundleAlgorithm algorithm;

    public RecEngineBundleAlgorithmList() {
    }

    public RecEngineBundleAlgorithmList(int rank, RecEngineBundleAlgorithm algorithm) {
        this.rank = rank;
        this.algorithm = algorithm;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public RecEngineBundleAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(RecEngineBundleAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
