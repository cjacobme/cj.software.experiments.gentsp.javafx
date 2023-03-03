package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.entity.ProblemSetup;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
        NewProblemDialog newProblemDialog = new NewProblemDialog();
        Optional<ProblemSetup> optionalProblemSetup = newProblemDialog.showAndWait();
        if (optionalProblemSetup.isPresent()) {
            logger.info("new problem was set up");
        } else {
            logger.info("dialog was closed");
        }
    }
}
