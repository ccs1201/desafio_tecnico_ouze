package br.com.ouze.interview.compoundinterest.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Value
@With
@JsonDeserialize(builder = ErrorMessageResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class ErrorMessageResponse {

    @Builder.Default
    LocalDateTime timestamp = now();
    String error;
    String message;
    String path;
    Integer status;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {}

}
