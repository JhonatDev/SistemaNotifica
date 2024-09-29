package Notifica.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Notifica.NotificaApplication;

//mvn jacoco:prepare-agent test install jacoco:report
@SpringBootTest(classes = NotificaApplication.class)
class NotificaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
