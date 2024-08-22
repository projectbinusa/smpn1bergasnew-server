package com.smpn1.bergas;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class ConfigLoader {

    private final Dotenv dotenv;

    // Constructor
    public ConfigLoader() {
        dotenv = Dotenv.configure()
                .directory("src/main/resources") // Menentukan direktori jika diperlukan
                .filename("smpn1bergas.env") // Menentukan nama file jika berbeda
                .load();
    }

    // Method to get environment variable with default value
    public String get(String key, String defaultValue) {
        return dotenv.get(key, defaultValue);
    }

    // Method to get environment variable
    public String get(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Environment variable " + key + " is not set or is empty.");
        }
        return value;
    }

    public String getType() {
        return get("TYPE");
    }

    public String getProjectId() {
        return get("PROJECT_ID");
    }

    public String getPrivateKeyId() {
        return get("PRIVATE_KEY_ID");
    }

    public String getPrivateKey() {
        return get("PRIVATE_KEY");
    }

    public String getClientEmail() {
        return get("CLIENT_EMAIL");
    }

    public String getClientId() {
        return get("CLIENT_ID");
    }

    public String getAuthUri() {
        return get("AUTH_URI");
    }

    public String getTokenUri() {
        return get("TOKEN_URI");
    }

    public String getAuthProviderCertUrl() {
        return get("AUTH_PROVIDER_X509_CERT_URL");
    }

    public String getClientCertUrl() {
        return get("CLIENT_X509_CERT_URL");
    }

    public String getUniverseDomain() {
        return get("UNIVERSE_DOMAIN");
    }
}
