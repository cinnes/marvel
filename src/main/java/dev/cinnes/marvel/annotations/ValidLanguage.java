package dev.cinnes.marvel.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LanguageValidator.class)
@Documented
public @interface ValidLanguage {
    String message() default "Unsupported language specified.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
