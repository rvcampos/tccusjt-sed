package br.com.usjt.jaxrs.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XXX Classe vazia de autenticacao do JAXRS
 */
public class SecurityDummy implements br.com.usjt.shiro.Security {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityDummy.class);

    @Override
    public boolean isAuthenticated() {
        SecurityDummy.LOG.error("Bypass");
        return true;
    }

    @Override
    public boolean isPermitted(String permission) {
        SecurityDummy.LOG.error("Bypass|" + permission);
        return true;
    }

    @Override
    public void logout() {
    }

    @Override
    public boolean login(String username, String password, int ent) {
        return true;
    }

    @Override
    public boolean hasRole(String role) {
        return true;
    }

    @Override
    public boolean hasAnyRole(String... roles) {
        return true;
    }

    @Override
    public Integer getUserId() {
        return 0;
    }

    @Override
    public String getPrincipals() {
        return "";
    }

    @Override
    public Integer getTipo() {
        return 0;
    }
    
}
