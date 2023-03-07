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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initInt(tfNumberCities, 100);
        initInt(tfPopulationSize, 100);
        initInt(tfNumCycles, 10000);
        initInt(tfElitismCount, 2);

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

    private int getInt(TextField source) {
        String content = source.getText();
        int result = Integer.parseInt(content);
        return result;
    }
}
