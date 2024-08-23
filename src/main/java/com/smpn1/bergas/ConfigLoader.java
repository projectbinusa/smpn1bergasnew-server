package com.smpn1.bergas;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigLoader {
    private final Dotenv dotenv = Dotenv.load();

    public String loadCredentials() {
        return String.format(
                "{\n" +
                        "  \"type\": \"%s\",\n" +
                        "  \"project_id\": \"%s\",\n" +
                        "  \"private_key_id\": \"%s\",\n" +
                        "  \"private_key\": \"%s\",\n" +
                        "  \"client_email\": \"%s\",\n" +
                        "  \"client_id\": \"%s\",\n" +
                        "  \"auth_uri\": \"%s\",\n" +
                        "  \"token_uri\": \"%s\",\n" +
                        "  \"auth_provider_x509_cert_url\": \"%s\",\n" +
                        "  \"client_x509_cert_url\": \"%s\",\n" +
                        "  \"universe_domain\": \"%s\"\n" +
                        "}",
                dotenv.get("TYPE"),
                dotenv.get("PROJECT_ID"),
                dotenv.get("PRIVATE_KEY_ID"),
                dotenv.get("PRIVATE_KEY"),
                dotenv.get("CLIENT_EMAIL"),
                dotenv.get("CLIENT_ID"),
                dotenv.get("AUTH_URI"),
                dotenv.get("TOKEN_URI"),
                dotenv.get("AUTH_PROVIDER_X509_CERT_URL"),
                dotenv.get("CLIENT_X509_CERT_URL"),
                dotenv.get("UNIVERSE_DOMAIN")
        );
    }
}
