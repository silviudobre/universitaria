package be.kdg.programming5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import be.kdg.programming3.presentation.Menu;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StartApplication.class, args);
    }
}
