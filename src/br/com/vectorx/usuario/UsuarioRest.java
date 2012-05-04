package br.com.vectorx.usuario;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vectorx.corban.cadastro.contato.CadContactBean;
import br.com.vectorx.corban.cadastro.contato.CadContactPhoneNumberBean;
import br.com.vectorx.corban.cadastro.contato.CadPhoneTypeBean;
import br.com.vectorx.corban.cadastro.servicos.CadBusinessUnit;
import br.com.vectorx.crud.CRUD;
import br.com.vectorx.crud.CRUDException;
import br.com.vectorx.crud.CRUDTab;
import br.com.vectorx.crud.PopulaBean;
import br.com.vectorx.crud.Validacao;
import br.com.vectorx.jaxrs.JSPAttr;
import br.com.vectorx.jaxrs.MediaTypeMore;
import br.com.vectorx.jaxrs.security.SecurityPublic;
import br.com.vectorx.util.HS;

/**
 * Ponto de entrada para telas customizadas
 */
@Path ("/")
public class UsuarioRest {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioRest.class);

    /**
     * {@link RbacUserBean}
     * 
     * @param <T>
     */
    @GET
    @POST
    @Path ("crud/usuario/read")
    @Stylesheet (href = "crud.jsp", type = MediaTypeMore.APP_JSP)
    public <T> void cadastroRbacUserRead() {
        CRUD.doit("read", new RbacUserBean());
    }

    /**
     * {@link RbacUserBean}
     * 
     * @param <T>
     */
    @GET
    @POST
    @Path ("crud/usuario/delete")
    @Stylesheet (href = "crud.jsp", type = MediaTypeMore.APP_JSP)
    public <T> void cadastroRbacUserDel() {
        JSPAttr j = new JSPAttr();
        if (j.getParameter("user_id") != null && !j.getParameter("user_id").isEmpty()) {
            try {
                RbacUserBean bean = HS.searchById(RbacUserBean.class, Long.parseLong(j.getParameter("user_id")));
                Long id = new Long(bean.getContact_id());
                CRUD.doit("delete", new RbacUserBean());
                this.deleta(id, CadContactBean.class);
            }
            catch (Exception e) {
                j.set("default_error_msg", "Falha na operação. Entre em contato com o administrador.");
            }
        }
    }

    private void deletePhone(Long contact_id, JSPAttr j) throws CRUDException {
        try {
            this.deleta(Long.parseLong(j.getParameter("phone_id")), CadContactPhoneNumberBean.class);
        }
        catch (NumberFormatException e) {
            throw new CRUDException("ID telefone inválido.");
        }
        catch (Exception e) {
            throw new CRUDException("Falha na operação. Entre em contato com o administrador.", e);
        }
    }

    /**
     * @param metodo
     */
    @GET
    @POST
    @Path ("telefone/{metodo}")
    @Stylesheet (href = "usuario/telefone.jsp", type = MediaTypeMore.APP_JSP)
    // FIXME security
    @SecurityPublic
    public <T> void cadastroTel(@PathParam ("metodo") String metodo) {
        JSPAttr j = new JSPAttr();
        Long id=null;
        try {
            id = new Long(Long.parseLong(j.getParameter("contact_id")));
            if (!metodo.equals("edit_update")) {
                j.set("metodo", "create");
            }
            if (metodo.equals("delete")) {
                this.deletePhone(Long.parseLong(j.getParameter("phone_id")), j);
            } else if (metodo.equals("update")) {
                Session session = HS.getSession();
                Transaction tx = session.getTransaction();
                try {
                    tx.begin();
                    CadContactPhoneNumberBean phone = PopulaBean.bean(new CadContactPhoneNumberBean(), j);
                    phone.setContact_id(Long.parseLong(j.getParameter("contact_id")));
                    if (this.isValidUsuarioContato(j, phone)) {
                        session.update(phone);
                        tx.commit();
                        j.set("default_success_msg", "Operação realizada com sucesso!");
                    }
                }
                catch (Exception e) {
                    throw new CRUDException("Falha na operação. Entre em contato com o administrador.", e);
                }
                finally {
                    if (session.isOpen()) session.close();
                }
            } else if (metodo.equals("edit_update")) {
                j.set("metodo", "update");
                populaPhoneUpdate(j);
            } else if (metodo.equals("create")) {
                Session session = HS.getSession();
                Transaction tx = session.getTransaction();
                try {
                    tx.begin();
                    CadContactPhoneNumberBean phone = PopulaBean.bean(new CadContactPhoneNumberBean(), j);
                    phone.setContact_id(Long.parseLong(j.getParameter("contact_id")));
                    if (this.isValidUsuarioContato(j, phone)) {
                        session.save(phone);
                        tx.commit();
                        j.set("default_success_msg", "Operação realizada com sucesso!");
                    }
                }
                catch (Exception e) {
                    throw new CRUDException("Falha na operação. Entre em contato com o administrador.", e);
                }
                finally {
                    if (session.isOpen()) session.close();
                }

            } else if (metodo.equals("edit_update")) {
                populaPhoneUpdate(j);
            }
        }
        catch (CRUDException e) {
            j.set("default_error_msg", e.getMessage());
        }finally{
            List<CadContactPhoneNumberBean> tel;
            Map<String, Long> map = new HashMap<String, Long>();
            map.put("contact_id", id);
            try {
                Map<Long, String> types = this.phoneTypeList();
                tel = HS.searchByValue(CadContactPhoneNumberBean.class, map);
                j.set("tpPhone", HS.searchAll(CadPhoneTypeBean.class));
                j.set("tels", tel);
                j.set("types", types);
            }
            catch (HibernateException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        j.set("contact_id", Long.parseLong(j.getParameter("contact_id")));
    }

    private Map<Long, String> phoneTypeList() throws CRUDException {
        Map<Long, String> map = new HashMap<Long, String>();
        List<CadPhoneTypeBean> types;
        try {
            types = HS.searchAll(CadPhoneTypeBean.class);
        }
        catch (Exception e) {
            throw new CRUDException("Falha na operação entre em contato com o administrador.");
        }
        for (CadPhoneTypeBean cadPhoneTypeBean : types) {
            map.put(cadPhoneTypeBean.getType_id(), cadPhoneTypeBean.getName());
        }
        return map;
    }

    /**
     * {@link RbacUserBean}
     * 
     * @param <T>
     */
    @GET
    @POST
    @Path ("crud/usuario/{metodo}")
    @Stylesheet (href = "usuario/cadusuario.jsp", type = MediaTypeMore.APP_JSP)
    public <T> void cadastroRbacUser(@PathParam ("metodo") String metodo) {
        JSPAttr j = new JSPAttr();
        try {
            // FIXME refatorar... Arrancar insert e update para métodos.
            if (metodo.equals("edit_insert")) {
                j.set("metodo", "create");
                this.popula(j);
            } else if (metodo.equals("edit_update")) {
                j.set("metodo", "update");
                populaEditUpdate(j);
            } else {
                RbacUserBean user = null;
                Session session = HS.getSession();
                Transaction tx = session.beginTransaction();
                try {
                    CadContactBean contact = PopulaBean.bean(new CadContactBean(), j);
                    user = PopulaBean.bean(new RbacUserBean(), j);
                    CadContactPhoneNumberBean tel = PopulaBean.bean(new CadContactPhoneNumberBean(), j);
                    tel.setContact_id(contact.getContact_id());
                    if (metodo.equals("create")) {
                        if (isValidUsuarioContato(j, contact)) {
                            tx.begin();
                            session.save(contact);
                            user.setContact_id(contact.getContact_id());
                            // FIXME pegar usuario logado
                            user.setDt_lastlogin(new Timestamp(new Date().getTime()));
                            user.setDt_lastpasswordchange(new Timestamp(new Date().getTime()));
                            if (isValidUsuarioContato(j, user)) {
                                session.save(user);
                                tx.commit();
                                j.set("default_success_msg", "Operação realizada com sucesso!");
                            } else {
                                tx.rollback();
                                return;
                            }
                        }
                    } else if (metodo.equals("update")) {
                        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                            RbacUserBean tmp = HS.searchById(RbacUserBean.class, user.getUser_id());
                            user.setPassword(tmp.getPassword());
                        }
                        if (isValidUsuarioContato(j, contact)) {
                            tx.begin();
                            session.update(contact);
                            user.setContact_id(contact.getContact_id());
                            // FIXME pegar usuario logado
                            user.setDt_lastlogin(new Timestamp(new Date().getTime()));
                            user.setDt_lastpasswordchange(new Timestamp(new Date().getTime()));
                            if (isValidUsuarioContato(j, user)) {
                                session.update(user);
                                tx.commit();
                                j.set("default_success_msg", "Operação realizada com sucesso!");
                            } else {
                                tx.rollback();
                                return;
                            }
                        }
                    }
                }
                catch (HibernateException e) {
                    if (e.getCause() != null && e.getCause().getMessage() != null) {
                        if (e.getCause().getMessage().startsWith("ORA-00001")) {
                            throw new CRUDException("Violação de registro único, o registro já existe no sistema.");
                        } else {
                            LOG.error("Falha na operação. Entre em contato com o administrador", e);
                            throw new CRUDException("Falha na operação. Entre em contato com o administrador.",
                                    new Exception("Falha ao inserir registros [" + user.toString() + "]", e));
                        }
                    }
                    throw new CRUDException("Falha na operação. Entre em contato com o administrador.", new Exception(
                            "Falha ao inserir registros [" + user.toString() + "]", e));
                }
                catch (Exception e) {
                    throw new CRUDException("Falha na operação. Entre em contato com o administrador.", new Exception(
                            "Falha ao inserir registros [" + user.toString() + "]", e));
                }
                finally {
                    if (session.isOpen()) {
                        session.close();
                    }
                }
            }
        }
        catch (CRUDException e) {
            j.set("default_error_msg", e.getMessage());
        }

    }

    private void populaEditUpdate(JSPAttr j) throws CRUDException {
        if (j.getParameter("user_id") != null && !j.getParameter("user_id").isEmpty()) {
            try {
                RbacUserBean use = HS.searchById(RbacUserBean.class, Long.parseLong(j.getParameter("user_id")));
                CadContactBean contato = HS.searchById(CadContactBean.class, use.getContact_id());
                populaUpdateUser(j, use, contato);
                this.popula(j);
                populaTabs(j, use);
            }
            catch (NumberFormatException e) {
                throw new CRUDException("ID usuário inválido.");
            }
            catch (Exception e) {
                throw new CRUDException("Falha na operação entre em contato com o administrador.");
            }
        }
    }

    private <T> void populaTabs(JSPAttr j, T bean) throws Exception {
        if (j.get("crud_read") != null) {
            j.set("crud_read", j.get("crud_read"));
        } else if (j.getParameter("crud_read") != null) {
            j.set("crud_read", j.getParameter("crud_read"));
        } else {
            j.set("crud_read", "no");
        }
        if (j.get("crud_menu") != null) {
            j.set("crud_menu", j.get("crud_menu"));
        } else if (j.getParameter("crud_menu") != null) {
            j.set("crud_menu", j.getParameter("crud_menu"));
        } else {
            j.set("crud_menu", "yes");
        }
        if (j.get("crud_tabs_id") != null) {
            j.set("crud_tabs_id", j.get("crud_tabs_id"));
        } else if (j.getParameter("crud_tabs_id") != null) {
            j.set("crud_tabs_id", j.getParameter("crud_tabs_id"));
        } else {
            j.set("crud_tabs_id", null);
        }
        if (j.get("crud_tabs_nome") != null) {
            j.set("crud_tabs_nome", j.get("crud_tabs_nome"));
        } else if (j.getParameter("crud_tabs_nome") != null) {
            j.set("crud_tabs_nome", j.getParameter("crud_tabs_nome"));
        } else {
            j.set("crud_tabs_nome", null);
        }
        
        Queue<CRUDTab> tabs = new LinkedList<CRUDTab>();
        j.set("crud_bcnav", PopulaBean.descreveNav(bean.getClass()));
        j.set("crud_id_bean", PopulaBean.descreveBeanGet(bean));
        tabs.add(new CRUDTab("Pápeis","papeisUser"));
        j.set("crud_tabs", tabs);
    }

    private void populaUpdateUser(JSPAttr j, RbacUserBean use, CadContactBean contato) throws Exception {
        j.set("user_id", j.getParameter("user_id"));
        j.set("contact_id", contato.getContact_id());
        j.set("login", use.getLogin());
        j.set("email", contato.getEmail());
        j.set("name", contato.getName());
    }

    private <T> void deleta(Long id, Class<T> clazz) throws CRUDException {
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        T bean = null;
        try {
            bean = HS.searchById(clazz, id);
            session.delete(bean);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            throw new CRUDException("Falha na operação. Entre em contato com o administrador.", new Exception(
                    "Não foi possivel excluir o registro [" + bean.toString() + "]"));
        }
        finally {
            session.clear();
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Popula os dados de telefone para update
     */
    private void populaPhoneUpdate(JSPAttr j) throws CRUDException {
        try {
            CadContactPhoneNumberBean tel = HS.searchById(CadContactPhoneNumberBean.class,
                    Long.parseLong(j.getParameter("phone_id")));
            j.set("areacode", tel.getAreacode());
            j.set("phone", tel.getPhone());
            j.set("extension", tel.getExtension());
            j.set("phone_id", tel.getPhone_id());

            this.popula(j);
        }
        catch (Exception e) {
            throw new CRUDException("Falha na operação. Entre em contato com o administrador.", e);
        }
    }

    private void popula(JSPAttr j) throws CRUDException {
        try {
            j.set("businessUnit", HS.searchAll(CadBusinessUnit.class));
        }
        catch (Exception e) {
            throw new CRUDException("Falha na operação. Entre em contato com o administrador.", e);
        }
    }

    private <T> boolean isValidUsuarioContato(JSPAttr j, T bean) throws CRUDException {
        Validator v = Validacao.getValidator();
        Set<ConstraintViolation<T>> vt = v.validate(bean);
        if (vt.size() > 0) {
            StringBuilder msgs = new StringBuilder();
            String sep = "";
            for (ConstraintViolation<T> o : vt) {
                msgs.append(sep + o.getMessage());
                sep = "\n";
            }
            j.set("default_error_msg", msgs.toString());
            this.popula(j);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     */
    @GET
    @POST
    @Path ("papeisPermissoes")
    @Stylesheet (href = "usuario/papeisPermissoes.jsp", type = MediaTypeMore.APP_JSP)
    // @SecurityPrivate(permission=SecurityPrivate.SecType.INSERT)
    @SecurityPublic
    public void cadastroRolePermission() {
        JSPAttr j = new JSPAttr();
        String metodo = j.getParameter("default_action");
        if (metodo != null && !metodo.isEmpty() && metodo.equals("update")) {
            this.regrasPapeisPermissao(j);
        }
        this.carregaPermissoes(j);
        this.carregaPapeisPermissoes(j);

    }

    private void regrasPapeisPermissao(JSPAttr j) {
        Session s = HS.getSession();
        String[] permissaoPapeis = j.getParameterValues("permission");
        List<RbacRelRolePermissionBean> toAdd = new ArrayList<RbacRelRolePermissionBean>();
        Transaction tx = s.beginTransaction();
        try {
            Long role_id = Long.parseLong(j.getParameter("crud_tabs_id"));
            List<RbacRelRolePermissionBean> papeisPermissoes = this.carregaPapeisPermissoes(j);
            if (permissaoPapeis != null && permissaoPapeis.length > 0) {
                for (String permissao : permissaoPapeis) {
                    RbacRelRolePermissionBean bean = (RbacRelRolePermissionBean) s.createCriteria(
                            RbacRelRolePermissionBean.class).add(
                            Restrictions.eq("permission_id", Long.parseLong(permissao))).add(
                            Restrictions.eq("role_id", role_id)).uniqueResult();
                    if (bean == null) {
                        bean = new RbacRelRolePermissionBean();
                        bean.setRole_id(role_id);
                        bean.setPermission_id(Long.parseLong(permissao));
                    }
                    if (!papeisPermissoes.contains(bean)) {
                        s.saveOrUpdate(bean);
                    }
                    toAdd.add(bean);
                }
                papeisPermissoes.removeAll(toAdd);
                if (!papeisPermissoes.isEmpty()) {
                    for (RbacRelRolePermissionBean bean : papeisPermissoes) {
                        s.delete(bean);
                    }
                }
                tx.commit();
                j.set("default_success_msg", "Operação realizada com sucesso");
            } else {
                if (papeisPermissoes != null && !papeisPermissoes.isEmpty()) {
                    int res = s.createQuery(
                            "delete from " + RbacRelRolePermissionBean.class.getSimpleName()
                                    + " where role_id = :role_id").setParameter("role_id", role_id).executeUpdate();
                    tx.commit();
                    j.set("default_success_msg", "Operação realizada com sucesso");
                }
            }
        }
        catch (Exception e) {
            tx.rollback();
            j.set("default_error_msg", "Falha na operação. Entre em contato com um administrador");
        }
        finally {
            s.close();
        }
    }

    @SuppressWarnings ("unchecked")
    private void carregaPermissoes(JSPAttr j) {
        List<RbacPermissionBean> listaPermission = new ArrayList<RbacPermissionBean>();
        Session session = HS.getSession();
        try {
            // TODO carregar nome valores
            Criteria critPermission = session.createCriteria(RbacPermissionBean.class);
            listaPermission = critPermission.list();
            Map<Long, String> lista = new HashMap<Long, String>();

            for (RbacPermissionBean per : listaPermission) {
                Map<String, Long> p = new HashMap<String, Long>();
                Map<String, Long> r = new HashMap<String, Long>();
                p.put("privilege_id", per.getPrivilege_id());
                r.put("resource_id", per.getResource_id());
                RbacPrivilegeBean privilege = HS.searchByValueUnique(RbacPrivilegeBean.class, p);
                RbacResourceBean resource = HS.searchByValueUnique(RbacResourceBean.class, r);
                lista.put(per.getPermission_id(), privilege.getPretty_name() + " | " + resource.getPretty_name());

            }
            j.set("crud_permission", lista);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @SuppressWarnings ("unchecked")
    private List<RbacRelRolePermissionBean> carregaPapeisPermissoes(JSPAttr j) {
        String id = j.getParameter("crud_tabs_id");
        List<RbacRelRolePermissionBean> listaPermissoes = new ArrayList<RbacRelRolePermissionBean>();
        Session session = HS.getSession();
        List<Long> list = new ArrayList<Long>();
        try {
            Criteria critRoles = session.createCriteria(RbacRelRolePermissionBean.class);
            listaPermissoes = critRoles.add(Restrictions.eq("role_id", Long.parseLong(id))).list();
            j.set("crud_role_permission", listaPermissoes);
            for (RbacRelRolePermissionBean permission : listaPermissoes) {
                list.add(permission.getPermission_id());
            }
            j.set("role_permission", list);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return listaPermissoes;
    }

    /**
     * {@link RbacRelRoleUserBean}
     */
    @GET
    @POST
    @Path ("papeisUser")
    @Stylesheet (href = "usuario/rolesDefault.jsp", type = MediaTypeMore.APP_JSP)
    // @SecurityPrivate (permission = SecType.INSERT)
    @SecurityPublic
    public void cadastroRoleUser() {
        JSPAttr j = new JSPAttr();
        String metodo = j.getParameter("default_action");
        if (metodo != null && !metodo.isEmpty() && metodo.equals("update")) {
            this.regras(j);
        }
        this.carregaPapeis(j);
        this.carregaPapeisUsuario(j);
    }

    // TODO Na pagina mudar para realmente encontrar a budega do checkbox
    // CHECKED
    private void regras(JSPAttr j) {
        Session s = HS.getSession();
        String[] papeisUsuario = j.getParameterValues("roles");
        List<RbacRelRoleUserBean> toAdd = new ArrayList<RbacRelRoleUserBean>();
        Transaction tx = s.beginTransaction();
        try {
            Long user_id = Long.parseLong(j.getParameter("crud_tabs_id"));
            List<RbacRelRoleUserBean> usuarioPapeis = this.carregaPapeisUsuario(j);
            if (papeisUsuario != null && papeisUsuario.length > 0) {
                for (String papel : papeisUsuario) {
                    RbacRelRoleUserBean bean = (RbacRelRoleUserBean) s.createCriteria(RbacRelRoleUserBean.class).add(
                            Restrictions.eq("role_id", Long.parseLong(papel))).add(Restrictions.eq("user_id", user_id)).uniqueResult();
                    if (bean == null) {
                        bean = new RbacRelRoleUserBean();
                        bean.setUser_id(user_id);
                        bean.setRole_id(Long.parseLong(papel));
                    }
                    if (!usuarioPapeis.contains(bean)) {
                        s.save(bean);
                    }
                    toAdd.add(bean);
                }
                usuarioPapeis.removeAll(toAdd);
                if (!usuarioPapeis.isEmpty()) {
                    for (RbacRelRoleUserBean bean : usuarioPapeis) {
                        s.delete(bean);
                    }
                }
                tx.commit();
                j.set("default_success_msg", "Operação realizada com sucesso");
            } else {
                if (usuarioPapeis != null && !usuarioPapeis.isEmpty()) {
                    // XXX usar esse res para alguma coisa?
                    int res = s.createQuery(
                            "delete from " + RbacRelRoleUserBean.class.getSimpleName() + " where user_id = :user_id").setParameter(
                            "user_id", user_id).executeUpdate();
                    tx.commit();
                    j.set("default_success_msg", "Operação realizada com sucesso");
                }
            }
        }
        catch (Exception e) {
            tx.rollback();
            j.set("default_error_msg", "Falha na operação. Entre em contato com um administrador");
        }
        finally {
            s.close();
        }
    }

    @SuppressWarnings ("unchecked")
    private void carregaPapeis(JSPAttr j) {
        List<RbacRoleBean> listaRoles = new ArrayList<RbacRoleBean>();
        Session session = HS.getSession();
        try {
            Criteria critRoles = session.createCriteria(RbacRoleBean.class);
            listaRoles = critRoles.list();
            j.set("crud_roles", listaRoles);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @SuppressWarnings ("unchecked")
    private List<RbacRelRoleUserBean> carregaPapeisUsuario(JSPAttr j) {
        String id = j.getParameter("crud_tabs_id");
        List<RbacRelRoleUserBean> listaRoles = new ArrayList<RbacRelRoleUserBean>();
        Session session = HS.getSession();
        List<Long> list = new ArrayList<Long>();
        try {
            Criteria critRoles = session.createCriteria(RbacRelRoleUserBean.class);
            listaRoles = critRoles.add(Restrictions.eq("user_id", Long.parseLong(id))).list();
            j.set("crud_user_roles", listaRoles);
            for (RbacRelRoleUserBean role : listaRoles) {
                list.add(role.getRole_id());
            }
            j.set("user_roles", list);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return listaRoles;
    }
}
