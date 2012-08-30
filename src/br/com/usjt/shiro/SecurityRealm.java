package br.com.usjt.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.ws.security.components.crypto.Crypto;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ead.aluno.AlunoBean;
import br.com.usjt.ead.professor.ProfessorBean;
import br.com.usjt.util.CryptoXFacade;
import br.com.usjt.util.HS;

/**
 * Autorizacao do aplicativo usando shiro
 */
public class SecurityRealm extends AuthorizingRealm
{

    /**
     * construtor padrao
     */
    public SecurityRealm()
    {
        this.setName(SecurityRealm.class.getName() + "::tccusjt-sed");
    }

    private static Logger LOG = LoggerFactory.getLogger(SecurityRealm.class);

    /**
     * Passo 1: Autenticando<br>
     * Trazendo o identificador unico do usuario
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        try {
            UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

            Map<String, String> criterio = new HashMap<String, String>();
            criterio.put("email", token.getUsername());
            Integer usuario_id = null;
            Object ent = SecurityUtils.getSubject().getSession().getAttribute("ent");
            String pass = "";
            boolean ok = true;
            if (ent != null) {
                switch ((Integer) ent)
                {
                case 0:
                    usuario_id = 0;
                    pass = CryptoXFacade.crypt("admin");
                    break;

                case 1:
                    ProfessorBean b = HS.searchByValue(ProfessorBean.class, criterio).get(0);
                    usuario_id = b.getId_professor();
                    pass = b.getSenha();
                    break;

                case 2:
                    AlunoBean b2 = HS.searchByValue(AlunoBean.class, criterio).get(0);
                    usuario_id = b2.getId_aluno();
                    pass = b2.getSenha();
                    ok = b2.isAtivo();
                    break;
                }
            }
            if (usuario_id != null) {
                if (!ok) {
                    throw new Exception("Usuario não ativo");
                }
                SimplePrincipalCollection coll = new SimplePrincipalCollection();
                coll.add(usuario_id, this.getName());
                coll.add(token.getUsername(), this.getName());
                coll.add(ent, this.getName());
                SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(coll, pass, this.getName());
                return sai;
            }
            else {
                throw new Exception("Usuario não encontrado");
            }
        }
        catch (HibernateException e) {
            SecurityRealm.LOG.error("SHIRO REALM HIBERNATE", e);
        }
        catch (Exception e) {
            SecurityRealm.LOG.error("SHIRO REALM", e);
        }
        return null;
    }

    /**
     * Passo 2: Autorizando<br>
     * Que pode ser com mais de uma credencial
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo sinfo = new SimpleAuthorizationInfo();
        for (Object pc : principals.fromRealm(this.getName())) {
            if (pc instanceof SimplePrincipalCollection) {
                Integer id = (Integer) ((SimplePrincipalCollection) pc).getPrimaryPrincipal();
                try {
                    Object ent = SecurityUtils.getSubject().getSession().getAttribute("ent");

                    if (ent != null) {
                        switch ((Integer) ent)
                        {
                        case 0:
                            sinfo.addRole("admin");
                            break;

                        case 1:
                            sinfo.addRole("professor");
                            break;

                        case 2:
                            sinfo.addRole("aluno");
                            break;
                        }
                    }
                }
                catch (Exception e) {
                    SecurityRealm.LOG.error("SHIRO PERMISSION", e);
                }
            }
        }
        return sinfo;
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        super.clearCache(principals);
        super.onLogout(principals);
    }

}
