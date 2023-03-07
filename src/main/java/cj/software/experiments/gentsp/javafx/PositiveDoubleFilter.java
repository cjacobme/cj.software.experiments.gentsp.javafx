package cj.software.experiments.gentsp.javafx;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class PositiveDoubleFilter implements UnaryOperator<TextFormatter.Change> {
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        TextFormatter.Change result;
        String newText = change.getControlNewText();
        try {
            double parsed = Double.parseDouble(newText);
            if (parsed >= 0.0) {
                result = change;
            } else {
                result = null;
            }
        } catch (NumberFormatException numberFormatException) {
            result = null;
        }
        return result;
    }
}
