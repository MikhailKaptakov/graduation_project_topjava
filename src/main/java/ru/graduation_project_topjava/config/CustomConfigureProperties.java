package ru.graduation_project_topjava.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@ConfigurationProperties(prefix="restaurant.service")
public class CustomConfigureProperties {

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime maxRevoteTime = LocalTime.of(11,00);

    public LocalTime getMaxRevoteTime() {
        return maxRevoteTime;
    }

    public void setMaxRevoteTime(LocalTime maxRevoteTime) {
        this.maxRevoteTime = maxRevoteTime;
    }
}
