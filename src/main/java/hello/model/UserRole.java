package hello.model;

/**
 * Created by orbot on 30.11.15.
 */
public enum UserRole {
    ADMIN, USER;

    public UserAuthority asAuthorityFor(final User user) {
        final UserAuthority authority = new UserAuthority();
        authority.setAuthority("ROLE_" + toString());
        authority.setUser(user);
        return authority;
    }

    public static UserRole valueOf(final UserAuthority authority) {
        switch (authority.getAuthority()) {
            case "ROLE_ADMIN":
                return ADMIN;
            case "ROLE_USER":
                return USER;
            default:
                throw new IllegalArgumentException("No existing role defined for authority: " + authority.getAuthority());
        }
    }
}
