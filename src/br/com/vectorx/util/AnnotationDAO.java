package br.com.vectorx.util;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Pegar anotação para o JSTL
 */
public class AnnotationDAO {

    private Map<String,Annotation> anotacoes = new HashMap<String, Annotation>();
    
    /**
     * 
     */
    public AnnotationDAO () {
    }
    
    /**
     * Carrega anotação no mapa
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
