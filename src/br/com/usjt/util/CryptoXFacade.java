package br.com.usjt.util;

import br.com.blowfishcryptox.BlowfishCryptox;

/**
 * Criptografia
 */
public class CryptoXFacade
{

    private static final String               passPhrase = "tccusjt";
    /**
     * Criptografia
     */
    private static final BlowfishCryptox cryptox    = BlowfishCryptox.getInstance(passPhrase, 5);

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
