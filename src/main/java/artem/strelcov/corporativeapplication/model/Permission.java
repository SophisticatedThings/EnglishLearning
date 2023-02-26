package artem.strelcov.corporativeapplication.model;

public enum Permission {
    HOME_PAGE("home_page:read"),CHAT("chat:read"),LEARNING_MATERIALS("learning_materials:read"),
    TESTS("tests:read"),
    USERS_READ("users:read");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
