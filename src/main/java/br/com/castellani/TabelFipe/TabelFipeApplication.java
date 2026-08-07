package br.com.castellani.TabelFipe;

import br.com.castellani.TabelFipe.principal.Principal;
import br.com.castellani.TabelFipe.visao.Interface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TabelFipeApplication implements CommandLineRunner {


	public static void main(String[] args) {
		//SpringApplication.run(TabelFipeApplication.class, args);
		new SpringApplicationBuilder(TabelFipeApplication.class)
				.headless(false)
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
