package ru.graduation_project_topjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import ru.graduation_project_topjava.web.json.JacksonObjectMapper;

import java.util.List;

@SpringBootApplication
@EnableCaching
public class GraduationProjectTopjavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationProjectTopjavaApplication.class, args);
    }

}
