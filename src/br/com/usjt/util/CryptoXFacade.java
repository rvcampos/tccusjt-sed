package br.com.usjt.util;

import br.com.blowfishcryptox.BlowfishCryptox;

/**
 * Criptografia
 */
public class CryptoXFacade
{

    // HARDCODE
    private static String               passPhrase = "tccusjt";
    /**
     * Criptografia
     */
    private static final BlowfishCryptox cryptox    = BlowfishCryptox.getInstance(passPhrase, 30);

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

    public static String crypt(String pass) {
        return cryptox.crypt(pass);
    }

    public static String decrypt(String encryptedPass) {
        return cryptox.decrypt(encryptedPass);
    }

    public static boolean checaSenha(String senha, String senhaEncriptada) {
        return cryptox.checaSenha(senha, senhaEncriptada);
    }

}
