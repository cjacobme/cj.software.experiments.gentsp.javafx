package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.entity.ProblemSetup;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class NewProblemDialog extends Dialog<ProblemSetup> {
    public NewProblemDialog() {
        setTitle("Setup a new Travalling Salesperson Problem");
        setResizable(true);
        DialogPane dialogPane = getDialogPane();
        PositiveIntegerStringConverter posConverter = new PositiveIntegerStringConverter();
        PositiveIntegerFilter posFilter = new PositiveIntegerFilter();
        TextFormatter<Integer> numCitiesFormatter = new TextFormatter<>(posConverter, 100, posFilter);
        Label numCitiesLabel = new Label("number of cities:");
        TextField numCities = new TextField();
        numCities.setTextFormatter(numCitiesFormatter);

        GridPane gridPane = new GridPane();
        gridPane.add(numCitiesLabel, 1, 1);
        gridPane.add(numCities, 2, 1);
        dialogPane.setContent(gridPane);

        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ObservableList<ButtonType> buttonTypes = dialogPane.getButtonTypes();
        buttonTypes.add(cancel);
        buttonTypes.add(ok);

        setResultConverter(new Callback<>() {
            @Override
            public ProblemSetup call(ButtonType buttonType) {
                ProblemSetup result;
                if (buttonType == ok) {
                    int numCities_ = Integer.parseInt(numCities.getText());
                    result = new ProblemSetup(
                            numCities_, 100, 10000, 2, 0.9, 3, 0.009);
                } else {
                    result = null;
                }
                return result;
            }
        });
    }
}
