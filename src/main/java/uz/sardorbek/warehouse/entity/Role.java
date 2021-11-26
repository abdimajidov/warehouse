package uz.sardorbek.warehouse.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    DIRECTOR,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
