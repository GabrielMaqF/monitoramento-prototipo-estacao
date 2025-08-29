package com.maqfiltros.sensors_contract.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Bean
    FirebaseApp firebaseApp() throws IOException  {
    	FirebaseOptions options = FirebaseOptions.builder()
    		    .setCredentials(GoogleCredentials.getApplicationDefault())
    		    .setDatabaseUrl("https://app-monitoramento-ete.firebaseio.com/")
    		    .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }
}
