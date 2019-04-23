package com.kivimango.nimhub.data;

public final class AuthorDto {
    private final Long id;
    private final String username, email;

    private AuthorDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public static AuthorDto of(Long id, String username, String email) {
        return new AuthorDto(id, username, email);
    }

    static AuthorDto of(User user) {
        return new AuthorDto(user.getId(), user.getUsername(), user.getEmail());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
