package com.zone24x7.rac.configservice.recengine.bundle;

public class RecEngineBundleAlgorithmCombineInfo {

    private boolean enableCombine;
    private String combineDisplayText;

    public RecEngineBundleAlgorithmCombineInfo() {
        // Constructor
    }


    public boolean isEnableCombine() {
        return enableCombine;
    }

    public void setEnableCombine(boolean enableCombine) {
        this.enableCombine = enableCombine;
    }

    public String getCombineDisplayText() {
        return combineDisplayText;
    }

    public void setCombineDisplayText(String combineDisplayText) {
        this.combineDisplayText = combineDisplayText;
    }
}
