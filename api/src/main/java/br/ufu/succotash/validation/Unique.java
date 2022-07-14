package br.ufu.succotash.validation;

import br.ufu.succotash.validation.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {
    String message() default "{br.ufu.succotash.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();
    String field();
}
