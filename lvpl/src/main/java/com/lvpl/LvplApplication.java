package com.lvpl;

import com.lvpl.domain.Lists;
import com.lvpl.domain.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class LvplApplication {

	public static void main(String[] args) {
		SpringApplication.run(LvplApplication.class, args);

	}

}
