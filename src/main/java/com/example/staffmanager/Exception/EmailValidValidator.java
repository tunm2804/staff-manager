package com.example.staffmanager.Exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidValidator implements ConstraintValidator<EmailValid, String> {

    private static final String EMAIL_SUFFIX_FE = "@fe.edu.vn";
    private static final String EMAIL_SUFFIX_FPT = "@fpt.edu.vn";
    private static final String VIETNAMESE_CHARACTERS = "ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÛÜÝàáâãèéêìíòóôõùúûüýỳỴỶỸ";

    @Override
    public void initialize(EmailValid constraintAnnotation) {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // Adjust this if you want to accept empty values
        }

        if (value.contains(" ")) {
            return false; // Contains spaces
        }

        for (char c : VIETNAMESE_CHARACTERS.toCharArray()) {
            if (value.indexOf(c) >= 0) {
                return false; // Contains Vietnamese characters
            }
        }

        return value.endsWith(EMAIL_SUFFIX_FE) || value.endsWith(EMAIL_SUFFIX_FPT);
    }
}
