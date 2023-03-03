package cj.software.experiments.gentsp.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@FxmlView("GenTspMain.fxml")
public class GenTspMainController {
    private final Logger logger = LogManager.getFormatterLogger();
    @FXML
    public void exitApplication() {
        logger.info("exiting now...");
        Platform.exit();
    }
}
