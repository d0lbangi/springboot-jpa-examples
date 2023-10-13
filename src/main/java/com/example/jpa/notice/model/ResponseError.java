package com.example.jpa.notice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

@AllArgsConstructor
@NotNull
@Builder
@Data
public class ResponseError {

    private String field;
    private String message;

    public static ResponseError of(FieldError e) {
        return ResponseError.builder()
                .field(e.getField())
                .message(e.getDefaultMessage())
                .build();
    }
}
