package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.javafx.control.TextFieldFormatter;
import cj.software.experiments.gentsp.util.spring.SpringContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("NewProblemDialog.fxml")
public class NewProblemController implements Initializable {

    @FXML
    private TextField tfWorldWidth;

    @FXML
    private TextField tfWorldHeight;

    @FXML
    private TextField tfNumberCities;

    @FXML
    private TextField tfPopulationSize;

    @FXML
    private TextField tfElitismCount;

    @FXML
    private TextField tfCrossoverRate;

    @FXML
    private TextField tfTournamentSize;

    @FXML
    private TextField tfMutationRate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFieldFormatter textFieldFormatter = SpringContext.getBean(TextFieldFormatter.class);
        textFieldFormatter.initInt(tfWorldWidth, 100);
        textFieldFormatter.initInt(tfWorldHeight, 100);
        textFieldFormatter.initInt(tfNumberCities, 100);
        textFieldFormatter.initInt(tfPopulationSize, 100);
        textFieldFormatter.initInt(tfElitismCount, 2);
        textFieldFormatter.initDouble(tfCrossoverRate, 0.9);
        textFieldFormatter.initInt(tfTournamentSize, 3);
        textFieldFormatter.initDouble(tfMutationRate, 0.009);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfWorldWidth.requestFocus();
            }
        });
    }

    public int getWorldWidth() {
        return getInt(tfWorldWidth);
    }

    public int getWorldHeight() {
        return getInt(tfWorldHeight);
    }

    public int getNumberCities() {
        return getInt(tfNumberCities);
    }

    public int getPopulationSize() {
        return getInt(tfPopulationSize);
    }

    public int getElitismCount() {
        return getInt(tfElitismCount);
    }

    public double getCrossoverRate() {
        return getDouble(tfCrossoverRate);
    }

    public int getTournamentSize() {
        return getInt(tfTournamentSize);
    }

    public double getMutationRate() {
        return getDouble(tfMutationRate);
    }
    private int getInt(TextField source) {
        String content = source.getText();
        int result = Integer.parseInt(content);
        return result;
    }

    private double getDouble(TextField source) {
        String content = source.getText();
        double result = Double.parseDouble(content);
        return result;
    }
}
