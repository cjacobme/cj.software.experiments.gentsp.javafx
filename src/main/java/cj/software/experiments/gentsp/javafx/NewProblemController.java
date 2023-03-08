package cj.software.experiments.gentsp.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("NewProblemDialog.fxml")
public class NewProblemController implements Initializable {

    @FXML
    private TextField tfNumberCities;

    @FXML
    private TextField tfPopulationSize;

    @FXML
    private TextField tfNumCycles;

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
        initInt(tfNumberCities, 100);
        initInt(tfPopulationSize, 100);
        initInt(tfNumCycles, 10000);
        initInt(tfElitismCount, 2);
        initDouble(tfCrossoverRate, 0.9);
        initInt(tfTournamentSize, 3);
        initDouble(tfMutationRate, 0.009);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfNumberCities.requestFocus();
            }
        });
    }

    private void initInt(TextField textField, int value) {
        PositiveIntegerStringConverter posConverter = new PositiveIntegerStringConverter();
        PositiveIntegerFilter posFilter = new PositiveIntegerFilter();
        TextFormatter<Integer> formatter = new TextFormatter<>(posConverter, value, posFilter);
        textField.setTextFormatter(formatter);
    }

    private void initDouble(TextField textField, double value) {
        PositiveDoubleStringConverter posConverter = new PositiveDoubleStringConverter();
        PositiveDoubleFilter posFilter = new PositiveDoubleFilter();
        TextFormatter<Double> formatter = new TextFormatter<>(posConverter, value, posFilter);
        textField.setTextFormatter(formatter);
    }

    public int getNumberCities() {
        return getInt(tfNumberCities);
    }

    public int getPopulationSize() {
        return getInt(tfPopulationSize);
    }

    public int getNumCycles() {
        return getInt(tfNumCycles);
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
