package br.com.usjt.shiro;

public interface Security {

	boolean isAuthenticated();
	
	void logout();
	
	boolean login(String username, String password, int entidade);

	boolean isPermitted(String permission);
}
