package artem.strelcov.corporativeapplication.model;

public enum Permission {
    VISITOR("visitor:read"),
    ADMIN_READ("admin:read"),
    ADMIN_PUT("admin:put");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
