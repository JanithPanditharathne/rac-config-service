package com.zone24x7.rac.configservice.recslot;

import javax.persistence.*;

@Entity
public class RecSlotRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rec_slot_id")
    private int recSlotID;

    @Column(name = "rule_id")
    private int ruleID;

    public RecSlotRule() {
    }

    public RecSlotRule(int recSlotID, int ruleID) {
        this.recSlotID = recSlotID;
        this.ruleID = ruleID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecSlotID() {
        return recSlotID;
    }

    public void setRecSlotID(int recSlotID) {
        this.recSlotID = recSlotID;
    }

    public int getRuleID() {
        return ruleID;
    }

    public void setRuleID(int ruleID) {
        this.ruleID = ruleID;
    }
}
