package com.example.jpa.notice.model;

public class DuplicateNoticeException extends RuntimeException {
    public DuplicateNoticeException(String message) {
        super(message);
        System.out.println();
    }
}
