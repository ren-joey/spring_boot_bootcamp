package example.validator;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentValidator {

    private static final Dotenv dotenv = Dotenv.load();

    public static String getEnv(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("Environment variable " + key + " is not set or is empty");
        }
        return value;
    }

    public static void validateEnv() {
        String[] requiredVars = {
                "SPRING_DATASOURCE_URL",
                "SPRING_DATASOURCE_USERNAME",
                "SPRING_DATASOURCE_PASSWORD",
                "MARIADB_ROOT_PASSWORD",
                "MARIADB_DATABASE",
                "JWT_SECRET"
        };

        for (String var : requiredVars) {
            getEnv(var);
        }
    }
}
