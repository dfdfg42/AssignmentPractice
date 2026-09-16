package com.example.pixelcanvas;

import java.time.Instant;

public class PixelInfo {

    private String color;

    private String uuid;

    private Instant lastUpdate;

    public PixelInfo(String color, String uuid, Instant lastUpdate) {
        this.color = color;
        this.uuid = uuid;
        this.lastUpdate = lastUpdate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
