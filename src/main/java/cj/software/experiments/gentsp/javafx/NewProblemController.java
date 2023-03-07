package cj.software.experiments.gentsp.javafx;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initInt(tfNumberCities, 100);
    }

    private void initInt(TextField textField, int value) {
        PositiveIntegerStringConverter posConverter = new PositiveIntegerStringConverter();
        PositiveIntegerFilter posFilter = new PositiveIntegerFilter();
        TextFormatter<Integer> formatter = new TextFormatter<>(posConverter, value, posFilter);
        textField.setTextFormatter(formatter);
    }

    public TextField getTfNumberCities() {
        return tfNumberCities;
    }
}
