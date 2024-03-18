package in.er.annamalai.userservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordHashUtil {
    private static BCryptPasswordEncoder BCRYPT = new BCryptPasswordEncoder();
    
    public static final String hashPassword(String password) {
        return BCRYPT.encode(password);
    }

    public static final boolean checkPassword(String password, String passwordHash) {
        return BCRYPT.matches(password, passwordHash);
    }
}
