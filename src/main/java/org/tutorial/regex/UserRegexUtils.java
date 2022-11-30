package org.tutorial.regex;

import java.util.regex.Pattern;

public class UserRegexUtils {

    private static final String emailRegex =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private static final String passwordRegex =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&-+=()])(?=\\S+$).{8,20}$";

    public static final Pattern emailPattern = Pattern.compile(emailRegex);

    public static final Pattern passwordPattern = Pattern.compile(passwordRegex);
}
