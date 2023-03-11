package cj.software.experiments.gentsp.javafx.control;

import cj.software.experiments.gentsp.javafx.PositiveDoubleFilter;
import cj.software.experiments.gentsp.javafx.PositiveDoubleStringConverter;
import cj.software.experiments.gentsp.javafx.PositiveIntegerFilter;
import cj.software.experiments.gentsp.javafx.PositiveIntegerStringConverter;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.springframework.stereotype.Service;

@Service
public class TextFieldFormatter {

    public void initInt(TextField textField, int value) {
        PositiveIntegerStringConverter posConverter = new PositiveIntegerStringConverter();
        PositiveIntegerFilter posFilter = new PositiveIntegerFilter();
        TextFormatter<Integer> formatter = new TextFormatter<>(posConverter, value, posFilter);
        textField.setTextFormatter(formatter);
    }

    public void initDouble(TextField textField, double value) {
        PositiveDoubleStringConverter posConverter = new PositiveDoubleStringConverter();
        PositiveDoubleFilter posFilter = new PositiveDoubleFilter();
        TextFormatter<Double> formatter = new TextFormatter<>(posConverter, value, posFilter);
        textField.setTextFormatter(formatter);
    }

}
