package com.zone24x7.rac.configservice.recengine.recslot;

import java.util.List;

public class RecEngineRecSlotList {

    private List<RecEngineRecSlot> recSlots;

    public RecEngineRecSlotList() {
    }

    public RecEngineRecSlotList(List<RecEngineRecSlot> recSlots) {
        this.recSlots = recSlots;
    }

    public List<RecEngineRecSlot> getRecSlots() {
        return recSlots;
    }

    public void setRecSlots(List<RecEngineRecSlot> recSlots) {
        this.recSlots = recSlots;
    }
}
