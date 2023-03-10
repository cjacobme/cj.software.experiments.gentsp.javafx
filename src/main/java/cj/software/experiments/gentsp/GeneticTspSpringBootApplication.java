package cj.software.experiments.gentsp;

import cj.software.experiments.gentsp.javafx.GenTspApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeneticTspSpringBootApplication {

	public static void main(String[] args) {
		Application.launch(GenTspApplication.class, args);
	}

}
