package br.com.usjt.shiro;

import java.util.HashMap;
import java.util.Map;

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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.usuario.PerfilBean;
import br.com.usjt.usuario.PermissaoBean;
import br.com.usjt.usuario.PessoaBean;
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
            criterio.put("login", token.getUsername());

            PessoaBean user = HS.searchByValueUnique(PessoaBean.class, criterio);
            if (user != null) {
                SimplePrincipalCollection coll = new SimplePrincipalCollection();
                coll.add(user.getUsuario_id(), this.getName());
                coll.add(user.getLogin(), this.getName());
                SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(coll, user.getPassword(), this.getName());
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
                Long id = (Long) ((SimplePrincipalCollection) pc).getPrimaryPrincipal();

                Session ses = HS.getSession();
                try {
                    Criteria crit = ses.createCriteria(PessoaBean.class);
                    crit.add(Restrictions.idEq(id));
                    PessoaBean usuario = (PessoaBean) crit.uniqueResult();
                    for (PerfilBean ln : usuario.getPerfis()) {
                        for (PermissaoBean ln3 : ln.getPermissoes()) {
                            if (ln3.getPrivilegio() != null && ln3.getRecurso() != null) {
                                String msg = ln3.getRecurso().getVerb() + ":" + ln3.getRecurso().getUrl() + ":"
                                        + ln3.getPrivilegio().getTp_privilegio();
                                sinfo.addStringPermission(msg);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    SecurityRealm.LOG.error("SHIRO PERMISSION", e);
                }
                finally {
                    try {
                        ses.close();
                    }
                    catch (Exception e3) {
                    }
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
