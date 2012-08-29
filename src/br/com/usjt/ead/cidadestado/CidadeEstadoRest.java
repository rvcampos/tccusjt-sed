package br.com.usjt.ead.cidadestado;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

import br.com.usjt.jaxrs.JSPAttr;
import br.com.usjt.jaxrs.MediaTypeMore;
import br.com.usjt.jaxrs.security.SecurityPublic;
import br.com.usjt.util.HS;

@Path("/cidade")
public class CidadeEstadoRest
{
    @Path("carrega_uf")
    @SecurityPublic
    @GET
    @POST
    @Stylesheet (href = "ufcidade.jsp", type = MediaTypeMore.APP_JSP)
    public static void carregaUf() {
        JSPAttr j = new JSPAttr();
        int id = 25;
        try {
            id = Integer.parseInt(j.getParameter("estado_id"));
        }
        catch (Exception e) {}
        j.set("list_city", carregaCidades(id));
    }

    /**
     * @param state_id
     */
    @SuppressWarnings ("unchecked")
    public static List<CidadeBean> carregaCidades(Integer state_id) {
        List<CidadeBean> listaCity = new ArrayList<CidadeBean>();
        Session session = HS.getSession();
        try {
            Criteria crit = session.createCriteria(CidadeBean.class);
            crit.add(Restrictions.eq("estado.id_estado", state_id)).addOrder(Order.asc("id_cidade"));
            listaCity = crit.list();
            return listaCity;
        }
        catch (Exception e) {
//            LOG.error("Falha ao carregar lista de Cidade", e);
        }
        finally {
            session.clear();
            session.close();
            new JSPAttr("uf_id", state_id);
        }
        return null;
    }
}
