package team.okky.personnel_management.config.jwt;

public interface JwtProperties {
    String SECRET = "okky";
    int EXPIRATION_TIME = 1000 * 60 * 30;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
