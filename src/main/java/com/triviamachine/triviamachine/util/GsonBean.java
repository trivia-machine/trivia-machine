package com.triviamachine.triviamachine.util;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonBean {

    @Bean
    public Gson getGson() {
        Gson gson = new Gson();
        return gson;
    }
}


