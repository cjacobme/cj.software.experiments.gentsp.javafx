package cj.software.experiments.gentsp.entity;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class World implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(50)
    private int width;

    @Min(50)
    private int height;

    private final List<City> cities = new ArrayList<>();

    private World() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<City> getCities() {
        return Collections.unmodifiableList(cities);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void addAll(Collection<City> cities) {
        this.cities.addAll(cities);
    }

    public static class Builder {
        protected World instance;

        protected Builder() {
            instance = new World();
        }

        public World build() {
            World result = instance;
            instance = null;
            return result;
        }

        public Builder withWidth(int width) {
            instance.width = width;
            return this;
        }

        public Builder withHeight(int height) {
            instance.height = height;
            return this;
        }
    }
}