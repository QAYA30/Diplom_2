package praktikum.pojo;

public class RefreshToken {
    private String token;

    @Override
    public String toString() {
        return String.format("praktikum.pojo.RefreshToken{'token=='%s' }", token);
    }
    public String getToken() {
        return token;
    }
    public RefreshToken(String token) {
        this.token = token;
    }
}
