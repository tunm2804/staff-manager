package com.example.staffmanager.Exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailFeValidator implements ConstraintValidator<EmailFe, String> {

    private static final String EMAIL_SUFFIX = "@fe.edu.vn";

    @Override
    public void initialize(EmailFe constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.endsWith(EMAIL_SUFFIX);
    }
}