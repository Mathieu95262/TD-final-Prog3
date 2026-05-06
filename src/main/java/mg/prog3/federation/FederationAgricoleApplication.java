package mg.prog3.federation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FederationAgricoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(FederationAgricoleApplication.class, args);
    }
}