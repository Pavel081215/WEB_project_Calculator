package work.dao;

/**
 * Created by Pavel on 18.05.2016.
 */
public class User {
    private String username;
    private String expression;
    private String time;

    public String getExpression() {
        return expression;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", expression='" + expression + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
