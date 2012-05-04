package br.com.vectorx.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import br.com.vectorx.jaxrs.JSPAttr;
import br.com.vectorx.jaxrs.security.Security;

/**
 * Implementacao de seguranca do JAXRS VectorX para o Shiro
 */
public class SecurityShiro implements Security {

    private Subject currentUser;

    private SecurityShiro () {}

    /**
     * Construtor
     */
    public static SecurityShiro init() {
        SecurityShiro rt = new SecurityShiro();
        rt.currentUser = SecurityUtils.getSubject();
        return rt;
    }

    @Override
    public boolean isAuthenticated() {
        try {
            this.currentUser.getSession().touch();
        }
        catch (Exception e) {
            new JSPAttr("msgsession","Sessão expirada. Favor efetuar o login novamente");
            this.currentUser.logout();
            return false;
        }
        return this.currentUser.isAuthenticated();
    }

    @Override
    public boolean isPermitted(String permission) {
        return this.currentUser.isPermitted(permission);
    }

    @Override
    public void logout() {
        this.currentUser.logout();
    }

    @Override
    public boolean login(String username, String password) {
        return this.login(new UsernamePasswordToken(username, password));
    }

    private boolean login(UsernamePasswordToken newlogin) {
        if (newlogin == null) {
            this.currentUser.logout();
            return false;
        }
        try {
            this.currentUser.login(newlogin);
        }
        catch (Exception e) {
            return false;
        }
        if (this.currentUser.isAuthenticated()) { return true; }
        return false;
    }

}
