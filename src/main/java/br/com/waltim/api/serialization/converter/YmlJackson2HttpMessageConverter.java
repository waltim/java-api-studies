package br.com.waltim.api.serialization.converter;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class YmlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    public YmlJackson2HttpMessageConverter() {
        super(new YAMLMapper()
                        .registerModule(new JavaTimeModule())  // Registra o módulo para lidar com LocalDateTime
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)  // Usa formato ISO em vez de timestamps
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL),
                MediaType.parseMediaType("application/x-yaml")
        );
    }
}
