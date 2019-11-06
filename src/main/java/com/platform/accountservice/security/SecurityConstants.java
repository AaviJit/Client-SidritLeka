package com.platform.accountservice.security;

public class SecurityConstants {
    public static final long  EXPIRATION_TIME = 864000000; //token is valid for 10 days.
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String LOGIN_URL = "/users/login";
    public static final String EMAIL_VERIFICATION_URL = "/registrationConfirm";
    public static final String GET_USER_URL = "/users/getUser";
    public static final String GET_ALL_USER_URL = "/users/allUsers";
    public static final String UPDATE_USER_URL = "/users/update";
    public static final String CHANGE_USER_ROLE_URL = "/users/changeRole";
    public static final String CHANGE_USER_PASSWORD_URL = "/users/changePassword";
    public static final String ADMIN_URL = "/users/admin";
    public static final String MODERATOR_URL = "/users/moderator";
    public static final String RESET_PASSWORD_URL = "/resetPassword";
    public static final String SAVE_PASSWORD_URL = "/savePassword";
    public static final String TOKEN_SECRET = "jf9i4jgu83nf10";

}
