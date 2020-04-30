package com.zone24x7.rac.configservice.algorithm;

import java.util.List;

public class AlgorithmList {

    private List<Algorithm> algorithms;

    public AlgorithmList() {
    }

    public AlgorithmList(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }
}
