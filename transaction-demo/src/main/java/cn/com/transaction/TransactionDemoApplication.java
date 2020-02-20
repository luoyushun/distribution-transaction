package cn.com.transaction;

import cn.com.client.hetao.enables.EnableTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransaction
public class TransactionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionDemoApplication.class, args);
	}

}
