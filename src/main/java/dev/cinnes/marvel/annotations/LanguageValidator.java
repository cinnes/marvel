package dev.cinnes.marvel.annotations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Slf4j
public class LanguageValidator implements ConstraintValidator<ValidLanguage, String> {

    @Value("${app.supported-languages}")
    private String[] supportedLanguages;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        log.info("checking if {} is valid", s);
        return Arrays.stream(this.supportedLanguages).toList().contains(s);
    }
}
