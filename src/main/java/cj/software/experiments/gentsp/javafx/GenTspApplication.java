package cj.software.experiments.gentsp.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class GenTspApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(GeneticTspSpringBootApplication.class)
                .run(args);
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent parent = fxWeaver.loadView(GenTspMainController.class);
        Scene scene = new Scene(parent);
        stage.setTitle("Genetic Travelling Salesperson");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
