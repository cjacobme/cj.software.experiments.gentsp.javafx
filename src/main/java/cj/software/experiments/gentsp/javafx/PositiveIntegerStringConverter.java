package cj.software.experiments.gentsp.javafx;

import javafx.util.converter.IntegerStringConverter;

public class PositiveIntegerStringConverter extends IntegerStringConverter {
    @Override
    public Integer fromString(String s) {
        int result = super.fromString(s);
        if (result < 0) {
            throw new IllegalArgumentException("negative number: " + result);
        }
        return result;
    }

    @Override
    public String toString(Integer value) {
        String result;
        if (value < 0) {
            result = "0";
        } else {
            result = super.toString(value);
        }
        return result;
    }
}
