package com.zone24x7.rac.configservice.recengine.bundle;

import java.util.List;

public class RecEngineBundle {

    private int id;
    private String name;
    private String type;
    private int defaultLimit;
    private List<RecEngineBundleAlgorithm> algorithms;
    private RecEngineBundleAlgorithmCombineInfo algoCombineInfo;

    public RecEngineBundle() {
    }

    public RecEngineBundle(int id, String name, String type, int defaultLimit,
                           List<RecEngineBundleAlgorithm> algorithms,
                           RecEngineBundleAlgorithmCombineInfo algoCombineInfo) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.defaultLimit = defaultLimit;
        this.algorithms = algorithms;
        this.algoCombineInfo = algoCombineInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDefaultLimit() {
        return defaultLimit;
    }

    public void setDefaultLimit(int defaultLimit) {
        this.defaultLimit = defaultLimit;
    }

    public List<RecEngineBundleAlgorithm> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<RecEngineBundleAlgorithm> algorithms) {
        this.algorithms = algorithms;
    }

    public RecEngineBundleAlgorithmCombineInfo getAlgoCombineInfo() {
        return algoCombineInfo;
    }

    public void setAlgoCombineInfo(RecEngineBundleAlgorithmCombineInfo algoCombineInfo) {
        this.algoCombineInfo = algoCombineInfo;
    }
}
