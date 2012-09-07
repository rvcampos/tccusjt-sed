package br.com.usjt.shiro;

public interface Security {

	boolean isAuthenticated();
	
	void logout();
	
	boolean login(String username, String password, int entidade);

	boolean isPermitted(String permission);
	
	boolean hasRole(String role);
	
	boolean hasAnyRole(String... roles);
    
    /**
     * @return UserId
     */
	Integer getUserId();
    
    /**
     * @return Como o "Principals" do Shiro retorne algum identificador único do usuário ou o Username
     */
    String getPrincipals();
    
    /**
     * @return Tipo de Usuário (ADMIN = 0, Professor = 1, Aluno = 2)
     */
    Integer getTipo();
}
