package br.com.usjt.jaxrs.security;

/**
 * Autenticacao do JAXRS
 */
public interface Security {

    /**
     * @return autenticado
     */
    boolean isAuthenticated();

    /**
     * Permissão inteira. Exemplo:<br>
     * method:resource:type<br>
     * 
     * @return autorizado
     */
    boolean isPermitted(String permission);
    
    /**
     * Logout
     */
    void logout();
    
    /**
     * Login
     */
    boolean login(String username, String password);
    
}
