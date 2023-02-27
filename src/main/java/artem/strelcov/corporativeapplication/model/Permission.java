package artem.strelcov.corporativeapplication.model;

public enum Permission {
    VISITOR("visitor:read"),
    ADMIN("admin:read");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
