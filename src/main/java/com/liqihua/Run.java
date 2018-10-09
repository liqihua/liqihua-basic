package com.liqihua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//扫描定时器，启用定时器功能
//@EnableScheduling
@SpringBootApplication
public class Run {

	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
	}
}
