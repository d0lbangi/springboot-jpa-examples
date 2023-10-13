package com.example.jpa.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeInput {

    @Size(min = 10, max = 100, message = "제목은 10-100자 사이의 값 입니다.")
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @Size(min = 50, max = 1000, message = "내용은 50-1000자 사이의 값 입니다.")
    @NotBlank(message = "내용은 필수입니다.")
    private String contents;
}
