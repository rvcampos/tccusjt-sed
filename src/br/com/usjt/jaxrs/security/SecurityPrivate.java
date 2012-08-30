package br.com.usjt.jaxrs.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para informar se o recurso é travado. A anotação é opcional
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityPrivate {

    /**
     * @return privilegios
     */
    SecType[] role() default SecType.ALUNO;

    /**
     * Tipos de autenticacoes
     */
    public static enum SecType {
        ADMIN,
        /**
         */
        PROFESSOR,
        /**
         */
        ALUNO
    }
    
    public static enum Entidade
    {
        ALUNO,
        PROFESSOR,
        CURSO,
        DUMMY;
    }

}
