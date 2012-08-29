package br.com.usjt.ead.curso;

import java.sql.Date;
import java.sql.SQLData;
import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.velocity.runtime.directive.Parse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

import br.com.usjt.ICrud;
import br.com.usjt.ead.EntityDAO;
import br.com.usjt.ead.cidadestado.CidadeBean;
import br.com.usjt.ead.cidadestado.EstadoUFBean;
import br.com.usjt.ead.contato.ContatoBean;
import br.com.usjt.ead.contato.EnderecoBean;
import br.com.usjt.ead.contato.TelefoneBean;
import br.com.usjt.ead.contato.TipoTelefoneBean;
import br.com.usjt.ead.professor.ProfessorBean;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.Entidade;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.util.CryptoXFacade;
import br.com.usjt.util.HS;

@Path("/curso")
public class DisciplinaRest implements ICrud
{

    @Override
    @Path("")
    @POST
    @GET
    @Stylesheet(href = "pag.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.LER, entity = Entidade.ALUNO)
    public void read() {
    }

    @Override
    @Path("detalha")
    @POST
    @Stylesheet(href = "pag.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.DETALHAR, entity = Entidade.DUMMY)
    public void edit_update() {
    }

    @Override
    @Path("cadastrar")
    @GET
    @Stylesheet(href = "curso/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.CRIAR, entity = Entidade.DUMMY)
    public void edit_insert() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        try {
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        finally {
            session.close();
        }
    }

    @Override
    @Path("delete")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.DELETAR, entity = Entidade.DUMMY)
    public void delete() {
        // TODO Auto-generated method stub

    }

    @Override
    @Path("create")
    @POST
    @Stylesheet(href = "curso/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.CRIAR, entity = Entidade.DUMMY)
    public void create() {
        JSPAttr j = new JSPAttr();
        DisciplinaBean objDisciplina = new DisciplinaBean();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            objDisciplina.setNome_disciplina(j.getParameter("txtNomeDisciplina").toString());
            objDisciplina.setData_inicio(dtFormat.parse(j.getParameter("txtDataInicio").toString()));
            objDisciplina.setData_termino(dtFormat.parse(j.getParameter("txtDataTermino").toString()));
            objDisciplina.setProfessor((ProfessorBean) session.load(ProfessorBean.class, 3));
            System.out.println("Antes do save");
            session.save(objDisciplina);
            System.out.println("depois do save");
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
    @Path("update")
    @POST
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.UPDATE, entity = Entidade.DUMMY)
    public void update() {
        // TODO Auto-generated method stub

    }

}
