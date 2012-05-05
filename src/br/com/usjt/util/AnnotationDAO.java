package br.com.usjt.util;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Pegar anota��o para o JSTL
 */
public class AnnotationDAO {

    private Map<String,Annotation> anotacoes = new HashMap<String, Annotation>();
    
    /**
     * 
     */
    public AnnotationDAO () {
    }
    
    /**
     * Carrega anota��o no mapa
     * @param name
     * @param annotation
     */
    public void loadAnnotation(String name, Annotation annotation)
    {
        anotacoes.put(name, annotation);
    }

    /**
     * Retorna o valor
     */
    public Object get(String nome, String valor) throws Exception {
        // XXX cached ou asm
        Annotation an = anotacoes.get(nome);
        if (an != null) {
            return an.getClass().getMethod(valor).invoke(an);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return anotacoes.toString();
    }

}
