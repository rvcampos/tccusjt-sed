package br.com.vectorx.shiro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

import br.com.vectorx.usuario.RbacPermissionBean;
import br.com.vectorx.usuario.RbacPrivilegeBean;
import br.com.vectorx.usuario.RbacRelRolePermissionBean;
import br.com.vectorx.usuario.RbacRelRoleUserBean;
import br.com.vectorx.usuario.RbacResourceBean;
import br.com.vectorx.usuario.RbacUserBean;
import br.com.vectorx.util.EntryWrapper;
import br.com.vectorx.util.HS;

/**
 * Autorização do aplicativo usando shiro
 */
public class SecurityRealm extends AuthorizingRealm {

    /**
     * construtor padrao
     */
    public SecurityRealm () {
        this.setName(SecurityRealm.class.getName() + "::CorBan");
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

            RbacUserBean user = HS.searchByValueUnique(RbacUserBean.class, criterio);
            if (user != null) {
                SimplePrincipalCollection coll = new SimplePrincipalCollection();
                coll.add(user.getUser_id(), this.getName());
                coll.add(user.getLogin(), this.getName());
                coll.add(user.getUnit_id(), this.getName());
                SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(coll, user.getPassword(), this.getName());
                return sai;
            } else {
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
                    Criteria crit = ses.createCriteria(RbacRelRoleUserBean.class);
                    crit.add(Restrictions.eq("user_id", id));
                    @SuppressWarnings ("unchecked")
                    List<RbacRelRoleUserBean> list = crit.list();
                    Set<Long> relRoles = new HashSet<Long>();
                    for (RbacRelRoleUserBean ln : list) {
                        relRoles.add(ln.getRole_id());
                    }
                    Set<Long> relPerm = new HashSet<Long>();
                    for (Long ln : relRoles) {
                        crit = ses.createCriteria(RbacRelRolePermissionBean.class);
                        crit.add(Restrictions.eq("role_id", ln));
                        @SuppressWarnings ("unchecked")
                        List<RbacRelRolePermissionBean> per = crit.list();
                        for (RbacRelRolePermissionBean ln2 : per) {
                            relPerm.add(ln2.getPermission_id());
                        }
                    }
                    Set<Entry<Long, Long>> permissions = new HashSet<Map.Entry<Long, Long>>();
                    for (Long ln : relPerm) {
                        crit = ses.createCriteria(RbacPermissionBean.class);
                        crit.add(Restrictions.eq("permission_id", ln));
                        @SuppressWarnings ("unchecked")
                        List<RbacPermissionBean> pbean = crit.list();
                        for (RbacPermissionBean ln3 : pbean) {
                            permissions.add(new EntryWrapper<Long, Long>(ln3.getResource_id(), ln3.getPrivilege_id()));
                        }
                    }
                    for (Entry<Long, Long> p : permissions) {
                        crit = ses.createCriteria(RbacResourceBean.class);
                        crit.add(Restrictions.eq("resource_id", p.getKey()));
                        RbacResourceBean r = null;
                        try {
                            r = (RbacResourceBean) crit.uniqueResult();
                        }
                        catch (Exception e4) {}
                        crit = ses.createCriteria(RbacPrivilegeBean.class);
                        crit.add(Restrictions.eq("privilege_id", p.getValue()));
                        RbacPrivilegeBean pp = null;
                        try {
                            pp = (RbacPrivilegeBean) crit.uniqueResult();
                        }
                        catch (Exception e5) {}
                        if (r != null && pp != null) {
                            String msg = r.getVerb() + ":" + r.getUrl() + ":" + pp.getTp_privilege();
                            SecurityRealm.LOG.info("Usuario:" + id + "|" + msg);
                            sinfo.addStringPermission(msg);
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
                    catch (Exception e3) {}
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
