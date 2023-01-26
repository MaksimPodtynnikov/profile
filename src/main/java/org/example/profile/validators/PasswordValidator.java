package org.example.profile.validators;

public class PasswordValidator {
    /**
     * @param param entered password
     * @return result of validation
     */
    public static boolean validate(String param)
    {
        return param.length() > 5;
    }
}
