package cj.software.experiments.gentsp.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class City implements Serializable {
    static final long serialVersionUID = 1L;

    private int x;

    private int y;

    private City() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(x)
                .append(y);
        String result = builder.build();
        return result;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder()
                .append(x)
                .append(y);
        int result = builder.build();
        return result;
    }

    @Override
    public boolean equals(Object otherObject) {
        boolean result;
        if (otherObject instanceof City) {
            City other = (City)otherObject;
            EqualsBuilder builder = new EqualsBuilder()
                    .append(this.x, other.x)
                    .append(this.y, other.y);
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
        protected City instance;

        protected Builder() {
            instance = new City();
        }

        public Builder withX(int x) {
            instance.x = x;
            return this;
        }

        public Builder withY(int y) {
            instance.y = y;
            return this;
        }

        public City build() {
            City result = instance;
            instance = null;
            return result;
        }
    }
}
