package com.example.jpa.logs.repository;

import org.springframework.stereotype.Repository;

public interface LogService  {

    void add(String text);

    void deleteLog();
}