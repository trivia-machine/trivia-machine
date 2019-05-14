package com.triviamachine.triviamachine.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class DateBean {
    @Bean
    public DateFormat dateFomatter(){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format;
    }
}
