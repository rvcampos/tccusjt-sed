package br.com.usjt.ead.aluno;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

import br.com.usjt.ICrud;
import br.com.usjt.ead.contato.ContatoBean;
import br.com.usjt.ead.contato.EnderecoBean;
import br.com.usjt.ead.contato.TelefoneBean;
import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPrivate;
import br.com.usjt.jaxrs.security.SecurityPrivate.Entidade;
import br.com.usjt.jaxrs.security.SecurityPrivate.SecType;
import br.com.usjt.util.CryptoXFacade;

@Path("/aluno")
public class AlunoRest implements ICrud
{

    @Override
    @Path("")
    @POST
    @GET
    @Stylesheet(href = "pag.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.LER, entity = Entidade.DUMMY)
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
    @Stylesheet(href = "/detalhar.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.CRIAR, entity = Entidade.DUMMY)
    public void edit_insert() {
        // TODO Auto-generated method stub

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
    @Stylesheet(href = "/read.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission = SecType.CRIAR, entity = Entidade.DUMMY)
    public void create() {
        JSPAttr j = new JSPAttr();
        AlunoBean b = new AlunoBean();
        b.setEmail(j.getParameter("txtEmail"));
        b.setSenha(CryptoXFacade.cryptox.crypt(j.getParameter("txtSenha")));
        b.setCpf(Long.parseLong(j.getParameter("txtCPF").replaceAll("[^0-9]", "")));
        EnderecoBean end = new EnderecoBean();
        end.setCep(Integer.parseInt(j.getParameter("txtCEP").replaceAll("[^0-9]", "")));
        end.setLogradouro(j.getParameter("txtEndereco"));
        end.setBairro(j.getParameter("txtBairro"));
        b.setEndereco(end);
        ContatoBean contato = new ContatoBean();
        TelefoneBean phone = new TelefoneBean();
        phone.setDdd(Integer.parseInt(j.getParameter("txtDDD")));
        phone.setTelefone(Long.parseLong(j.getParameter("txtTelefone")));
        contato.getTelefones().add(phone);
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
