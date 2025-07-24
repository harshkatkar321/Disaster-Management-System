package com.app.config;

import java.util.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class JwtAuthConverter {

    public static Converter<Jwt, Collection<GrantedAuthority>> defaultGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
        scopesConverter.setAuthorityPrefix("SCOPE_");
        scopesConverter.setAuthoritiesClaimName("scope");

        return jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            // add default scope-based authorities
            authorities.addAll(scopesConverter.convert(jwt));

            // add roles claim, top-level "roles" array
            List<String> roles = jwt.getClaimAsStringList("roles");
            if (roles != null) {
                roles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r)));
            }

            // add Keycloak-style realm_access.roles
            Map<String, Object> realm = jwt.getClaim("realm_access");
            if (realm != null && realm.containsKey("roles")) {
                @SuppressWarnings("unchecked")
                List<String> realmRoles = (List<String>) realm.get("roles");
                realmRoles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r)));
            }

            return authorities;
        };
    }

    public static JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(defaultGrantedAuthoritiesConverter());
        return converter;
    }
}
