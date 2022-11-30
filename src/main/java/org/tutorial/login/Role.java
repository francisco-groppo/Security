package org.tutorial.login;

public enum Role {

    ADMIN(Values.ADMIN),
    USER(Values.USER);

    Role(String role) {
        if (!this.name().equals(role)) {
            throw new IllegalArgumentException("Incorrect enum value");
        }
    }

    public static class Values {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }
}
