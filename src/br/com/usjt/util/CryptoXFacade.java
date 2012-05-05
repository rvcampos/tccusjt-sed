package br.com.usjt.util;

import br.com.blowfishcryptox.BlowfishCryptox;

/**
 * Criptografia
 */
public class CryptoXFacade {

    // HARDCODE
    private static String                  passPhrase        = "rbacpass ";
    /**
     * Criptografia
     */
    public static final BlowfishCryptox    cryptox           = BlowfishCryptox.getInstance(passPhrase, 30);
    
    /**
     * Senha
     */
    public static void main(String[] args) {
        try {
            System.out.println(cryptox.crypt(args[0]));
        }
        catch (Exception e) {
            System.out.println(cryptox.crypt("admin"));
        }
    }

}
