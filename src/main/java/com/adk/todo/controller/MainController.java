package com.adk.todo.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@RestController
@Tag(name = "Todo API Endpoints")
public class MainController {

}
