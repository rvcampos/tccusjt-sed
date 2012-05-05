package br.com.usjt.jaxrs.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para informar o recurso travado. A anotação é opcional
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityPrivate {

    /**
     * info
     */
    String value() default "private";
}
