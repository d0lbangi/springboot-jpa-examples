package com.example.jpa.user.model;

import com.example.jpa.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.DoubleStream;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseMessage {

    private ResponseMessageHeader header;
    private Object data;

    public static ResponseMessage fail(String message) {

        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(false)
                        .resultCode("")
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build())
                    .data(null)
                    .build();
    }

    public static ResponseMessage success(Object data) {

        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultCode("")
                        .message("")
                        .status(HttpStatus.OK.value())
                        .build())
                .data(data)
                .build();
    }
}
