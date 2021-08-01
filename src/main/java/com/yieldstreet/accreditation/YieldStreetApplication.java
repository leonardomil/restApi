package com.yieldstreet.accreditation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yieldstreet.accreditation.repo.RepoAccreditation;

@SpringBootApplication()
public class YieldStreetApplication {

	public static void main(String[] args) {
		SpringApplication.run(YieldStreetApplication.class, args);
	}

}
