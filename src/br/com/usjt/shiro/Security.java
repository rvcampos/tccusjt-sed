package br.com.usjt.shiro;

public interface Security {

	boolean isAuthenticated();
	
	void logout();
	
	boolean login(String username, String password);

	boolean isPermitted(String permission);
}
