package com.tasktracker.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskTrackerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerBackendApplication.class, args);
    }

}
