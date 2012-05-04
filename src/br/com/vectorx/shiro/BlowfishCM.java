package br.com.vectorx.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.vectorx.util.CryptoXFacade;

/**
 * Shiro Blowfish Credential Matcher with Blowfish Cryptox
 */
public class BlowfishCM implements CredentialsMatcher {

    private static final Logger LOG = LoggerFactory.getLogger(BlowfishCM.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        try {
            String pass = String.copyValueOf((char[]) token.getCredentials());
            return CryptoXFacade.cryptox.checaSenha(pass, info.getCredentials().toString());
        }
        catch (Exception e) {
            BlowfishCM.LOG.error("Credenciais: " + token + "::" + info, e);
        }
        return false;
    }

}
