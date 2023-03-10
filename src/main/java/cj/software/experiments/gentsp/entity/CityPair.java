package cj.software.experiments.gentsp.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class CityPair implements Serializable {
    private static final long serialVersionUID = 1L;

    private City city1;

    private City city2;

    private CityPair() {
    }

    public City getCity1() {
        return city1;
    }

    public City getCity2() {
        return city2;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder()
                .append(city1)
                .append(city2);
        int result = builder.build();
        return result;
    }

    @Override
    public boolean equals(Object otherObject) {
        boolean result;
        if (otherObject instanceof CityPair) {
            CityPair other = (CityPair) otherObject;
            EqualsBuilder builder = new EqualsBuilder()
                    .append(this.city1, other.city1)
                    .append(this.city2, other.city2);
            result = builder.build();
        } else {
            result = false;
        }
        return result;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected CityPair instance;

        protected Builder() {
            instance = new CityPair();
        }

        public CityPair build() {
            CityPair result = instance;
            instance = null;
            return result;
        }

        public Builder withCity1(City city1) {
            instance.city1 = city1;
            return this;
        }

        public Builder withCity2(City city2) {
            instance.city2 = city2;
            return this;
        }
    }
}