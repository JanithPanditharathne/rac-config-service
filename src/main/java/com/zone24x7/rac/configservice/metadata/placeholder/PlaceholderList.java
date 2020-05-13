package com.zone24x7.rac.configservice.metadata.placeholder;

import java.util.List;


public class PlaceholderList {

    private List<Placeholder> placeholders;

    public PlaceholderList(List<Placeholder> placeholders) {
        this.placeholders = placeholders;
    }

    public List<Placeholder> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<Placeholder> placeholders) {
        this.placeholders = placeholders;
    }
}
