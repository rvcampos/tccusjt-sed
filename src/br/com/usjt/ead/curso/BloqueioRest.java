package br.com.usjt.ead.curso;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.usjt.ICrud;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.util.HS;
import br.com.usjt.util.Utils;

@Path("/admin")
public class BloqueioRest implements ICrud
{
    private static final Logger LOG = LoggerFactory.getLogger(BloqueioRest.class);

    @Override
    @Path("listarBloqueios")
    @POST
    @GET
    @Stylesheet(href = "admin/listarbloqueios.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ADMIN)
    public void read() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        Criteria c = session.createCriteria(BloqueioBean.class);
        try {
            // Usar um método para receber e devolver um CRITERIA
            c = filtrar(c, j);
            // Usar como paginação padrão
            Utils.paginar(j, c);
            j.set("bloqueios", c.list());
        }
        catch (Exception e) {
            LOG.error("Erro ao listar bloqueios", e);
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

        if (!Utils.isEmpty(j.getParameter("filtro_disciplina"))) {
            c.createCriteria("disciplina", "disciplina").add(Restrictions.like("disciplina.nome", j.getParameter("filtro_disciplina"), MatchMode.ANYWHERE));
        }
        return c;
    }

    @Override
    @Path("delete")
    @POST
    @Stylesheet(href = "admin/listarbloqueios.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(role = SecType.ADMIN)
    public void delete() {
        JSPAttr j = new JSPAttr("metodo", "delete");
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            BloqueioBean bean = (BloqueioBean) session.get(BloqueioBean.class, Integer.parseInt(j.getParameter("id_bloqueio")));
            session.delete(bean);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("detalha")
    @POST
    @Stylesheet(href = "/read.jsp", type = "application/jsp")
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void edit_update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Path("adicionar")
    @GET
    @Stylesheet(href = "/adicionar.jsp", type = "application/jsp")
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void edit_insert() {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Path("create")
    @POST
    @Stylesheet(href = "/read.jsp", type = "application/jsp")
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void create() {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Path("update")
    @POST
    @Stylesheet(href = "/read.jsp", type = "application/jsp")
    @SecurityPrivate(role = { SecType.ADMIN, SecType.PROFESSOR, SecType.ALUNO })
    public void update() {
        // TODO Auto-generated method stub
        
    }
}
