package com.zone24x7.rac.configservice.recslot;

import java.util.List;

public class RecSlotList {

    private List<RecSlotDetail> recSlots;

    public RecSlotList() {
    }

    public RecSlotList(List<RecSlotDetail> recSlots) {
        this.recSlots = recSlots;
    }

    public List<RecSlotDetail> getRecSlots() {
        return recSlots;
    }

    public void setRecSlots(List<RecSlotDetail> recSlots) {
        this.recSlots = recSlots;
    }
}
