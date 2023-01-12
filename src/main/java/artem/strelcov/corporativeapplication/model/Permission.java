package artem.strelcov.corporativeapplication.model;

public enum Permission {
    INDEX_READ("index:read");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
