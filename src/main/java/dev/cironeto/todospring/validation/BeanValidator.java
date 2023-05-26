package dev.cironeto.todospring.validation;

import dev.cironeto.todospring.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class BeanValidator {

    public static void validate(Object object){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator() ;
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

        if (!constraintViolations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            constraintViolations.forEach(violation -> {
                sb.append(violation.getMessage());
                sb.append(" ");
            });

            throw new ValidationException("Validation error: " + sb.toString());
        }
    }
}
