package artem.strelcov.corporativeapplication.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static artem.strelcov.corporativeapplication.model.Permission.INDEX_READ;

public enum Role {
    PROGRAMMER(Set.of(INDEX_READ)),
    DIRECTOR(Set.of(INDEX_READ)),
    ADMIN(Set.of(INDEX_READ)),
    MANAGER(Set.of(INDEX_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
