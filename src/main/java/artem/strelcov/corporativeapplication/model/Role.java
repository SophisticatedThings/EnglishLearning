package artem.strelcov.corporativeapplication.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static artem.strelcov.corporativeapplication.model.Permission.*;

public enum Role {

    ADMIN(Set.of(USERS_READ)),
    VISITOR(Set.of(HOME_PAGE,CHAT,LEARNING_MATERIALS,TESTS));

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
