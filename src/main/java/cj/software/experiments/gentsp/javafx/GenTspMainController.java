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
            logger.info("%30s=%d", "number of cities", problemSetup.getNumCities());
        } else {
            logger.info("that was cancelled");
        }
    }
}
