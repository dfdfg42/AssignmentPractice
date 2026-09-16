package com.example.pixelcanvas;

public class User {

    private String uuid;
    private String name;
    private boolean isAdmin;

    public User(String uuid, String name, boolean isAdmin) {
        this.uuid = uuid;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isAdmin(String uuid) {
        return isAdmin;
    }
}
