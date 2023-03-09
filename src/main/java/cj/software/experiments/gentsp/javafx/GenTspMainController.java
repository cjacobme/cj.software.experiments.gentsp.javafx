package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.entity.ProblemSetup;
import cj.software.experiments.gentsp.entity.World;
import cj.software.experiments.gentsp.javafx.control.WorldPane;
import cj.software.experiments.gentsp.util.WorldFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@FxmlView("GenTspMain.fxml")
public class GenTspMainController implements Initializable {
    private static final String INT_FORMAT = "%30s = %d";

    private static final String DOUBLE_FORMAT = "%30s = %10.4f";

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private WorldFactory worldFactory;

    @FXML
    private BorderPane mainBorder;

    private WorldPane worldPane;

    private final Logger logger = LogManager.getFormatterLogger();

    @FXML
    public void exitApplication() {
        logger.info("exiting now...");
        Platform.exit();
    }

    @FXML
    public void newProblem() {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        FxmlNewProblemDialog dialog = new FxmlNewProblemDialog(applicationContext, owner);
        Optional<ProblemSetup> optionalProblemSetup = dialog.showAndWait();
        if (optionalProblemSetup.isPresent()) {
            logger.info("new problem setup was defined");
            ProblemSetup problemSetup = optionalProblemSetup.get();
            int width = problemSetup.getWidth();
            int height = problemSetup.getHeight();
            int numCities = problemSetup.getNumCities();
            logger.info(INT_FORMAT, "world width", width);
            logger.info(INT_FORMAT, "world height", height);
            logger.info(INT_FORMAT, "number of cities", numCities);
            logger.info(INT_FORMAT, "population size", problemSetup.getPopulationSize());
            logger.info(INT_FORMAT, "number of cycles", problemSetup.getMaxGenerations());
            logger.info(INT_FORMAT, "elitism count", problemSetup.getElitismCount());
            logger.info(DOUBLE_FORMAT, "crossover rate", problemSetup.getCrossoverRate());
            logger.info(INT_FORMAT, "tournament size", problemSetup.getTournamentSize());
            logger.info(DOUBLE_FORMAT, "mutation rate", problemSetup.getMutationRate());

            World world = worldFactory.createWorld(width, height, numCities);
            logger.info("world created");
            worldPane.setWorld(world);

        } else {
            logger.info("that was cancelled");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        worldPane = new WorldPane();
        mainBorder.setCenter(worldPane);
    }
}
