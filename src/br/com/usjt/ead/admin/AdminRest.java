package br.com.usjt.ead.admin;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ICrud;
import br.com.usjt.ead.EntityDAO;
import br.com.usjt.ead.aluno.AlunoBean;
import br.com.usjt.ead.cidadestado.CidadeBean;
import br.com.usjt.ead.cidadestado.CidadeEstadoRest;
import br.com.usjt.ead.cidadestado.EstadoUFBean;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.jaxrs.security.SecurityPublic;
import br.com.usjt.shiro.Security;
import br.com.usjt.shiro.SecurityShiro;
import br.com.usjt.util.CryptoXFacade;
import br.com.usjt.util.HS;
import br.com.usjt.util.Utils;

@Path("/admin")
public class AdminRest implements ICrud
{

    private static final Logger LOG = LoggerFactory.getLogger(AdminRest.class);

    @Override
    @Path("listar")
    @POST
    @GET
    @Stylesheet(href = "admin/listar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ADMIN)
    public void read() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Criteria c = session.createCriteria(AdminBean.class);
        try {
            // Usar um método para receber e devolver um CRITERIA
            c = filtrar(c, j);
            // Usar como paginação padrão
            Utils.paginar(j, c);
            j.set("admins", c.list());
        }
        catch (Exception e) {
            LOG.error("Erro ao listar administradores", e);
            j.repopular();
        }
        finally {
            session.close();
            j.repopular();
        }
    }

    private Criteria filtrar(Criteria c, JSPAttr j) {
        // Quando o parametro de filtro for de outro bean, no caso um
        // relacionamento, utilizar 'createCriteria(nomeBean,alias)'
        // Restrictions.like = like
        // Restrictions.eq = igual
        // PS: Prestar atenção no tipo de dado. Deve ser igual ao do bean. No
        // caso, String com String, Integer com Integer
        if (!Utils.isEmpty(j.getParameter("filtro_nome"))) {
            c.createCriteria("contato", "contato").add(
                    Restrictions.like("contato.nome", j.getParameter("filtro_nome"), MatchMode.ANYWHERE));
        }

        if (!Utils.isEmpty(j.getParameter("filtro_email"))) {
            c.add(Restrictions.like("email", j.getParameter("filtro_email"), MatchMode.ANYWHERE));
        }
        return c;
    }

    @Override
    @Path("alterarDados")
    @GET
    @Stylesheet(href = "admin/alterar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN })
    public void edit_update() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Security sh = SecurityShiro.init();
        EntityDAO dao = new EntityDAO();
        try {
            AdminBean admin = dao.searchID(sh.getUserId(), session, AdminBean.class);
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            populaEditUpdate(j, admin);
        }
        catch (Exception e) {
            LOG.error("Falha ao carregar", e);
        }
        finally {
            session.close();
        }
    }

    private void populaEditUpdate(JSPAttr j, AdminBean admin) {
        j.set("txtEmail", admin.getEmail());
        j.set("txtNome", admin.getNome());
    }

    @Override
    @Path("cadastrar")
    @GET
    @Stylesheet(href = "admin/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void edit_insert() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        try {
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            j.set("uf_id", 26);
            j.set("list_city", session.createCriteria(CidadeBean.class).add(Restrictions.eq("estado.id_estado", 26)).list());
        }
        catch (Exception e) {
            LOG.error("Falha ao carregar", e);
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("delete")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN })
    public void delete() {
    }

    @Override
    @Path("create")
    @POST
    @Stylesheet(href = "admin/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void create() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            AdminBean b = popula(j, session, false);
            if (Utils.isValid(b)) {
                session.save(b);
                tx.commit();
                j.sucessMsg("Cadastro realizado com sucesso.");
            }
            else {
                j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
                CidadeEstadoRest.carregaUf();
                j.repopular();
            }
        }
        catch (Exception e) {
            tx.rollback();
            j.repopular();
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            CidadeEstadoRest.carregaUf();
        }
        finally {
            session.close();
        }
    }

    private AdminBean popula(JSPAttr j, Session session, boolean update) {
        AdminBean b = new AdminBean();
        if (update) {
            b = (AdminBean) session.get(AdminBean.class, SecurityShiro.init().getUserId());
        }
        
        try {
           
            b.setEmail(j.getParameter("txtEmail"));
            b.setSenha(CryptoXFacade.crypt(j.getParameter("txtSenha")));
            b.setNome(j.getParameter("txtNome"));
        }
        catch (Exception e) {
            LOG.error("Falha ao popular admin", e);
        }

        return b;
    }

    @Override
    @Path("alterar")
    @POST
    @Stylesheet(href = "admin/alterar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = { SecType.ADMIN })
    public void update() {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        AdminBean b = new AdminBean();
        try {
            if(Utils.isValid(b))
            {
                session.save(b);
            }
            else
            {
                j.repopular();
            }
        }
        catch (Exception e) {
            LOG.error("Falha ao alterar dados cadastrais de admin",e);
            j.repopular();
        }
        finally
        {
            
        }
    }
  
    /**
     * Carrega Cidade
     */
    @POST
    @Path("checaEmail")
    @Stylesheet(href = "admin/label.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPublic
    public void validaEmail() {
        Session session = HS.getSession();
        JSPAttr j = new JSPAttr();
        String email = null;
        try {
            email = j.getParameter("email");
            if (Utils.isValidMail(email)) {
                j.set("labelemail", "E-mail válido");
                j.set("label", "label label-success");
                j.set("canPost", true);

                Criteria c = session.createCriteria(AdminBean.class);
                c.add(Restrictions.eq("email", email));

                if (c.list().size() > 0) {
                    j.set("labelemail", "E-mail já cadastrado");
                    j.set("label", "label label-info");
                    j.set("canPost", false);
                }
            }
            else {
                j.set("labelemail", "E-mail Inválido");
                j.set("label", "label label-info");
                j.set("canPost", false);
            }
        }
        catch (Exception e) {
            LOG.error("", e);
        }
        finally {
            session.clear();
            session.close();
        }
    }
}
