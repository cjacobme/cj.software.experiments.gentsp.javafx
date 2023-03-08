package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.entity.ProblemSetup;
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

import java.util.Optional;

@Component
@FxmlView("GenTspMain.fxml")
public class GenTspMainController {
    private static final String INT_FORMAT = "%30s = %d";

    private static final String DOUBLE_FORMAT = "%30s = %10.4f";

    @Autowired
    private ConfigurableApplicationContext applicationContext;
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
            logger.info(INT_FORMAT, "number of cities", problemSetup.getNumCities());
            logger.info(INT_FORMAT, "population size", problemSetup.getPopulationSize());
            logger.info(INT_FORMAT, "number of cycles", problemSetup.getMaxGenerations());
            logger.info(INT_FORMAT, "elitism count", problemSetup.getElitismCount());
            logger.info(DOUBLE_FORMAT, "crossover rate", problemSetup.getCrossoverRate());
            logger.info(INT_FORMAT, "tournament size", problemSetup.getTournamentSize());
            logger.info(DOUBLE_FORMAT, "mutation rate", problemSetup.getMutationRate());
        } else {
            logger.info("that was cancelled");
        }
    }
}
