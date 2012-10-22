package br.com.usjt.ead;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = { METHOD, FIELD, ANNOTATION_TYPE } )
@Retention(value = RUNTIME)
@Documented
public @interface DateValidation {

    String message() default "Data deve ser..";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public enum WHEN {
        AFTER, BEFORE;
    }
    
    WHEN period();
    
    String properties();
}
