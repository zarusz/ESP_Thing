package com.zarusz.control.config;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.fasterxml.jackson.datatype.joda.ser.JacksonJodaFormat;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JodaModule jacksonJodaModule() {
        DateTimeFormatterFactory dateTimeFormatterFactory = new DateTimeFormatterFactory();
        dateTimeFormatterFactory.setIso(DateTimeFormat.ISO.DATE);
        DateTimeSerializer dateTimeFormatter = new DateTimeSerializer(new JacksonJodaFormat(dateTimeFormatterFactory.createDateTimeFormatter().withZoneUTC()));

        JodaModule module = new JodaModule();
        module.addSerializer(DateTime.class, dateTimeFormatter);
        return module;
    }
}
