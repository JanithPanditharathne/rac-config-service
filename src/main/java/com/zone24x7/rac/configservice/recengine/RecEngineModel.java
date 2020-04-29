package com.zone24x7.rac.configservice.recengine;

import javax.persistence.*;

/**
 * Class representing the Rec Engine model.
 *
 */
@Entity
@Table(name = "rec_engine", schema = "ibraccs")
public class RecEngineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "key_rec_engine", nullable = false)
    private String key;

    @Column(name = "value_rec_engine")
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
