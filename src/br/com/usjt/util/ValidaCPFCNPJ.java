package br.com.usjt.util;

/**
 * Utilitário de validação de CNPJ e CPF
 */
public class ValidaCPFCNPJ {

    private static final int[] pesoCPF  = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
    private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    /**
     * Chama o método de validação para calculo de digito usando PESO de CPF
     * 
     * @param cpf
     * @return
     */
    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) { return false; }

        Integer digito1 = ValidaCPFCNPJ.calcularDigito(cpf.substring(0, 9), ValidaCPFCNPJ.pesoCPF);
        Integer digito2 = ValidaCPFCNPJ.calcularDigito(cpf.substring(0, 9) + digito1, ValidaCPFCNPJ.pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    /**
     * Chama o método de validação para cálculo de digito usando PESO de CNPJ
     * 
     * @param cnpj
     * @return
     */
    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.isEmpty())) { return false; }

        while (cnpj.length() < 14) {
            cnpj = "0" + cnpj;
        }
        
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
                || cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
                || cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888")
                || cnpj.equals("99999999999999")) {
            return false;
        }

        Integer digito1 = ValidaCPFCNPJ.calcularDigito(cnpj.substring(0, 12), ValidaCPFCNPJ.pesoCNPJ);
        Integer digito2 = ValidaCPFCNPJ.calcularDigito(cnpj.substring(0, 12) + digito1, ValidaCPFCNPJ.pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }
}
