package cj.software.experiments.gentsp.entity;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CityPairTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = CityPair.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }


    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        CityPair.Builder builder = CityPair.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                CityPair.class);

        CityPair instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getCity1()).as("city 1").isNull();
        softy.assertThat(instance.getCity2()).as("city 2").isNull();
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        City city1 = mock(City.class);
        City city2 = mock(City.class);
        CityPair instance = CityPair.builder()
                .withCity1(city1)
                .withCity2(city2)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getCity1()).as("city 1").isSameAs(city1);
        softy.assertThat(instance.getCity2()).as("city 2").isSameAs(city2);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() {
        CityPair instance = new CityPairBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<CityPair>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}