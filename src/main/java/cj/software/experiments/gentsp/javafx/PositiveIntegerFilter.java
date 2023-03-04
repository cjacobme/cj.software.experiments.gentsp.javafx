package cj.software.experiments.gentsp.javafx;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class PositiveIntegerFilter implements UnaryOperator<TextFormatter.Change> {
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        TextFormatter.Change result;
        if (change.getControlNewText().matches("[0-9]*")) {
            result = change;
        } else {
            result = null;
        }
        return result;
    }
}
