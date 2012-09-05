package br.com.usjt.jaxrs.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação de seguranca publica de REST
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityPublic {}
