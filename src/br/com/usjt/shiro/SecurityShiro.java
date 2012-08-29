package br.com.usjt.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.security.SecurityDummy;


/**
 * Implementacao de seguranca do JAXRS
 */
public class SecurityShiro implements Security {

    private Subject currentUser;

    private SecurityShiro () {}

    /**
     * Construtor
     */
    public static Security init() {
        return new SecurityDummy();
//        SecurityShiro rt = new SecurityShiro();
//        rt.currentUser = SecurityUtils.getSubject();
//        return rt;
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
    public boolean login(String username, String password, int entidade) {
        return this.login(new UsernamePasswordToken(username, password),entidade);
    }

    private boolean login(UsernamePasswordToken newlogin, int entidade) {
        if (newlogin == null) {
            this.currentUser.logout();
            return false;
        }
        try {
            currentUser.getSession().setAttribute("ent", entidade);
            this.currentUser.login(newlogin);
        }
        catch (Exception e) {
            return false;
        }
        if (this.currentUser.isAuthenticated()) { return true; }
        return false;
    }

}
