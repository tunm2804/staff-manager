package com.example.staffmanager.Exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailFptValidator implements ConstraintValidator<EmailFpt, String> {

    private static final String EMAIL_SUFFIX = "@fpt.edu.vn";

    @Override
    public void initialize(EmailFpt constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.endsWith(EMAIL_SUFFIX);
    }
}