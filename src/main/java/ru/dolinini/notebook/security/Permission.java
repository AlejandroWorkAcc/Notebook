package ru.dolinini.notebook.security;

public enum Permission {
    READNOTES("permission:readnotes"), WRITENOTES("permission:writenotes"), READ("permission:read"), WRITE("permission:write");

    public final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
