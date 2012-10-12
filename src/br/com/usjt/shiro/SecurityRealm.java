package br.com.usjt.shiro;

import java.util.HashMap;
import java.util.List;
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
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ead.admin.AdminBean;
import br.com.usjt.ead.aluno.AlunoBean;
import br.com.usjt.ead.professor.ProfessorBean;
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
            
            Security sh = SecurityShiro.init();
            Integer ent = sh.getTipo();
            
            Map<String, String> criterio = new HashMap<String, String>();
            criterio.put("email", token.getUsername());
            Integer usuario_id = null;
            String pass = "";
            List<?> lst;
            boolean ok = true;
            if (ent != null) {
                switch (ent)
                {
                case 0:
                    lst = HS.searchByValue(AdminBean.class, criterio);
                    if(lst.isEmpty())
                    {
                        throw new Exception("Usuário não encontrado");
                    }
                    AdminBean bean = (AdminBean) lst.get(0);
                    usuario_id = bean.getId_admin();
                    pass = bean.getSenha();
                    break;

                case 1:
                    lst = HS.searchByValue(ProfessorBean.class, criterio);
                    if(lst.isEmpty())
                    {
                        throw new Exception("Usuário não encontrado");
                    }
                    ProfessorBean b = (ProfessorBean) lst.get(0);
                    usuario_id = b.getId_professor();
                    pass = b.getSenha();
                    break;

                case 2:
                    lst = HS.searchByValue(AlunoBean.class, criterio);
                    if(lst.isEmpty())
                    {
                        throw new Exception("Usuário não encontrado");
                    }
                    AlunoBean b2 = (AlunoBean) lst.get(0);
                    usuario_id = b2.getId_aluno();
                    pass = b2.getSenha();
                    ok = b2.getAtivo();
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
        Security sh = SecurityShiro.init();
        Integer ent = sh.getTipo();
        for (Object pc : principals.fromRealm(this.getName())) {
            if (pc instanceof SimplePrincipalCollection) {
                try {
                    if (ent != null) {
                        switch (ent)
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
