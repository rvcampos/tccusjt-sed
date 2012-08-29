package br.com.usjt.ead.aluno;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.usjt.ICrud;
import br.com.usjt.ead.cidadestado.CidadeBean;
import br.com.usjt.ead.cidadestado.EstadoUFBean;
import br.com.usjt.ead.contato.ContatoBean;
import br.com.usjt.ead.contato.EnderecoBean;
import br.com.usjt.ead.contato.TelefoneBean;
import br.com.usjt.ead.contato.TipoTelefoneBean;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.Entidade;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.util.CryptoXFacade;
import br.com.usjt.util.HS;

@Path("/aluno")
public class AlunoRest implements ICrud
{

    private static final Logger LOG = LoggerFactory.getLogger(AlunoRest.class);
    
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
    @Stylesheet(href = "aluno/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.CRIAR, entity = Entidade.DUMMY)
    public void edit_insert() {
        JSPAttr j = new JSPAttr();
        Session session = HS.getSession();
        try {
            j.set("lista_uf", session.createCriteria(EstadoUFBean.class).addOrder(Order.asc("id_estado")).list());
            j.set("uf_id", 26);
            j.set("list_city", session.createCriteria(CidadeBean.class).add(Restrictions.eq("estado.id_estado", 26)).list());
        }
        catch (Exception e) {
            LOG.error("Falha ao carregar",e);
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
    @Stylesheet(href = "aluno/cadastrar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.CRIAR, entity = Entidade.DUMMY)
    public void create() {
        JSPAttr j = new JSPAttr();
        AlunoBean b = new AlunoBean();
        Session session = HS.getSession();
        Transaction tx = session.beginTransaction();
        try {
            b.setEmail(j.getParameter("txtEmail"));
            b.setSenha(CryptoXFacade.crypt(j.getParameter("txtSenha")));
            b.setCpf(Long.parseLong(j.getParameter("txtCPF").replaceAll("[^0-9]", "")));
            EnderecoBean end = new EnderecoBean();
            end.setCep(Integer.parseInt(j.getParameter("txtCep").replaceAll("[^0-9]", "")));
            end.setLogradouro(j.getParameter("txtEndereco"));
            end.setBairro(j.getParameter("txtBairro"));
            end.setCidade((CidadeBean) session.load(CidadeBean.class, Integer.parseInt(j.getParameter("cidade"))));
            b.setEndereco(end);
            ContatoBean contato = new ContatoBean();
            TelefoneBean phone = new TelefoneBean();
            phone.setDdd(Integer.parseInt(j.getParameter("txtTelefoneDDD")));
            phone.setTelefone(Long.parseLong(j.getParameter("txtTelefone").replaceAll("[^0-9]", "")));
            phone.setTipo((TipoTelefoneBean) session.load(TipoTelefoneBean.class, 1));
            contato.getTelefones().add(phone);
            TelefoneBean celular = new TelefoneBean();
            celular.setDdd(Integer.parseInt(j.getParameter("txtCelularDDD")));
            celular.setTelefone(Long.parseLong(j.getParameter("txtCelular").replaceAll("[^0-9]", "")));
            celular.setTipo((TipoTelefoneBean) session.load(TipoTelefoneBean.class, 2));
            contato.getTelefones().add(celular);
            b.setContato(contato);
            session.save(b);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            j.set("txtSenha", "txtSenha");
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
