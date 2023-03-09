package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.entity.City;
import cj.software.experiments.gentsp.entity.ProblemSetup;
import cj.software.experiments.gentsp.util.CityFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@FxmlView("GenTspMain.fxml")
public class GenTspMainController {
    private static final String INT_FORMAT = "%30s = %d";

    private static final String DOUBLE_FORMAT = "%30s = %10.4f";

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private CityFactory cityFactory;

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
            logger.info(INT_FORMAT, "world width", problemSetup.getWidth());
            logger.info(INT_FORMAT, "world height", problemSetup.getHeight());
            logger.info(INT_FORMAT, "number of cities", problemSetup.getNumCities());
            logger.info(INT_FORMAT, "population size", problemSetup.getPopulationSize());
            logger.info(INT_FORMAT, "number of cycles", problemSetup.getMaxGenerations());
            logger.info(INT_FORMAT, "elitism count", problemSetup.getElitismCount());
            logger.info(DOUBLE_FORMAT, "crossover rate", problemSetup.getCrossoverRate());
            logger.info(INT_FORMAT, "tournament size", problemSetup.getTournamentSize());
            logger.info(DOUBLE_FORMAT, "mutation rate", problemSetup.getMutationRate());

            createCities(problemSetup.getNumCities());
        } else {
            logger.info("that was cancelled");
        }
    }

    private void createCities (int numCities) {
        List<City> cities = cityFactory.createCities(numCities);
        logger.info("%d cities created", cities.size());
    }
}
