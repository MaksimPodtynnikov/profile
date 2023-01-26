package org.example.profile.validators;

public class NameValidator {
    /**
     * @param param entered name
     * @return result of validation
     */
    public static boolean validate(String param)
    {
        return param.length() > 4;
    }

}
