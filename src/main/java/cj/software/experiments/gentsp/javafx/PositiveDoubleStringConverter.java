package cj.software.experiments.gentsp.javafx;

import javafx.util.converter.DoubleStringConverter;

public class PositiveDoubleStringConverter extends DoubleStringConverter {
    @Override
    public Double fromString(String s) {
        Double result = super.fromString(s);
        if (result < 0.0) {
            throw new IllegalArgumentException("negative number");
        }
        return result;
    }

    @Override
    public String toString(Double value) {
        String result;
        if (value != null) {
            if (value >= 0.0) {
                result = super.toString(value);
            } else {
                result = "0.0";
            }
        } else {
            result = "0.0";
        }
        return result;
    }
}
