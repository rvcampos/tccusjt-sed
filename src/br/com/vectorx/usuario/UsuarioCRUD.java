package br.com.vectorx.usuario;

import java.util.Queue;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.resteasy.annotations.providers.jaxb.Stylesheet;

import br.com.vectorx.crud.CRUD;
import br.com.vectorx.crud.CRUDEventsDummy;
import br.com.vectorx.crud.CRUDTab;
import br.com.vectorx.jaxrs.MediaTypeMore;
import br.com.vectorx.jaxrs.security.SecurityPrivate;
import br.com.vectorx.jaxrs.security.SecurityPrivate.SecType;

/**
 * Ponto de entrada
 */
@Path ("/crud")
public class UsuarioCRUD {

	//FIXME remover
//    /**
//     * {@link RbacUserBean}
//     */
//    @GET
//    @POST
//    @Path ("usuario/{metodo}")
//    @Stylesheet (href = "crud.jsp", type = MediaTypeMore.APP_JSP)
//    public void cadastroRbacUser(@PathParam ("metodo") String metodo) {
//        CRUD.doit(metodo, new RbacUserBean(), new CRUDEventsDummy<RbacUserBean>() {
//
//            @Override
//            public void beforeSave(CRUD<RbacUserBean> crud, Session session) throws CRUDException {
//                if (crud.getOldBean() != null) {
//                    if (!crud.getOldBean().getDt_lastpasswordchange().equals(crud.getBean().getDt_lastpasswordchange())) {
//                        crud.getBean().setDt_lastpasswordchange(new Timestamp(new Date().getTime()));
//                        if (crud.getBean().getDt_lastlogin() == null) {
//                            crud.getBean().setDt_lastlogin(crud.getOldBean().getDt_lastlogin());
//                        }
//                    }
//                }
//                // TODO data do ultimo login
//                if (crud.getMetodo().equals(Metodo.create)) {
//                    crud.getBean().setDt_lastlogin(new Timestamp(new Date().getTime()));
//                    crud.getBean().setDt_lastpasswordchange(new Timestamp(new Date().getTime()));
//                } else if (crud.getMetodo().equals(Metodo.update)) {
//                    if (crud.getBean() != null && crud.getBean().getPassword().equals(crud.getOldBean().getPassword())) {
//                        crud.getBean().setDt_lastpasswordchange(new Timestamp(new Date().getTime()));
//                    }
//                }
//                crud.getBean().setPassword(CryptoXFacade.cryptox.crypt(crud.getBean().getPassword()));
//            }
//            
//            @Override
//            public void populaTab(Queue<CRUDTab> tabs) {
//                tabs.add(new CRUDTab("Pápeis","papeisUser",true));
//                tabs.add(new CRUDTab("Contato","contato",true));
//            }
//        });
//    }

    /**
     * {@link RbacRoleBean}
     */
    @GET
    @POST
    @Path ("papeis/{metodo}")
    @Stylesheet (href = "crud.jsp", type = MediaTypeMore.APP_JSP)
    public void cadastroRbacPapeis(@PathParam ("metodo") String metodo) {
        CRUD.doit(metodo, new RbacRoleBean(), new CRUDEventsDummy<RbacRoleBean>() {
            @Override
            public void populaTab(Queue<CRUDTab> tabs) {
                tabs.add(new CRUDTab("Permissões", "papeisPermissoes",true));
            }
        });
    }

    /**
     * {@link RbacRelRolePermissionBean}
     */
    @GET
    @POST
    @Path ("papeisPermissoes/{metodo}")
    @Stylesheet (href = "crud.jsp", type = MediaTypeMore.APP_JSP)
    public void cadastroRolePermission(@PathParam ("metodo") String metodo) {
        CRUD.doit(metodo, new RbacRelRolePermissionBean());
    }

    /**
     * {@link RbacPrivilegeBean}
     */
    @GET
    @POST
    @Path ("privilegio/{metodo}")
    @Stylesheet (href = "crud.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission=SecType.SELECT)
    public void cadastroRbacPrivelege(@PathParam ("metodo") String metodo) {
        CRUD.doit("read", new RbacPrivilegeBean());
    }

    /**
     * {@link RbacResourceBean}
     */
    @GET
    @POST
    @Path ("recursos/{metodo}")
    @Stylesheet (href = "crud.jsp", type = MediaTypeMore.APP_JSP)
    @SecurityPrivate(permission=SecType.SELECT)
    public void cadastroRbacResource(@PathParam ("metodo") String metodo) {
        CRUD.doit("read", new RbacResourceBean());
    }

    /**
     * {@link RbacPermissionBean}
     */
    @GET
    @POST
    @Path ("permissao/{metodo}")
    @Stylesheet (href = "crud.jsp", type = MediaTypeMore.APP_JSP)
    public void cadastroRbacPermission(@PathParam ("metodo") String metodo) {
        CRUD.doit(metodo, new RbacPermissionBean());
    }

}
