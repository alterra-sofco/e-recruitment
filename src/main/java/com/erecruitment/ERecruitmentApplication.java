package com.erecruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ERecruitmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ERecruitmentApplication.class, args);
    }

}
