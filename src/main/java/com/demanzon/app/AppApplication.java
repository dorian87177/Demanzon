package com.demanzon.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demanzon.app.javafx.JavaFxApp;

import javafx.application.Application;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		Application.launch(JavaFxApp.class, args);
	}

}
