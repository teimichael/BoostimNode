package stu.napls.boostimnode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoostimNodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoostimNodeApplication.class, args);
    }

}
