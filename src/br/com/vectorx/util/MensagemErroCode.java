package br.com.vectorx.util;


/**
 * Classe utilit�ria para mensagens de erro contendo os c�digos apenas
 */
public final class MensagemErroCode {

    /**
     * Mensagem para as transa��es OK
     */
    public static final String TRANSACAO_OK                        = "00000001";
    /**
     * Mensagem para as transa��es ERRO
     */
    public static final String TRANSACAO_ERRO                      = "00000002";
    /**
     * Mensagem para as registro �nico
     */
    public static final String TRANSACAO_ERRO_DB_REGISTRO_UNICO    = "00000003";
    /**
     * Mensagem para falha de inser��o
     */
    public static final String TRANSACAO_ERRO_DB_REGISTRO_INSERCAO = "00000004";
    /**
     * Mensagem para dados inv�lidos
     */
    public static final String TRANSACAO_ERRO_DADOS_INVALIDO       = "00000005";
    /**
     * Mensagem para erro POPULABEAN
     */
    public static final String POPULABEAN                          = "00000006";

}
