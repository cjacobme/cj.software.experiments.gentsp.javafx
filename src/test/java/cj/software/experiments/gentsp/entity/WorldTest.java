package cj.software.experiments.gentsp.entity;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WorldTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = World.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }


    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        World.Builder builder = World.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                World.class);

        World instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getWidth()).as("width").isEqualTo(0);
        softy.assertThat(instance.getHeight()).as("height").isEqualTo(0);
        softy.assertThat(instance.getCities()).as("cities").isEmpty();
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        int width = 120;
        int height = 140;
        World instance = World.builder()
                .withWidth(width)
                .withHeight(height)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getWidth()).as("width").isEqualTo(width);
        softy.assertThat(instance.getHeight()).as("height").isEqualTo(height);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() {
        World instance = new WorldBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<World>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }

    @Test
    public void addFourCities() {
        Collection<City> cities = List.of(
                City.builder().withX(3).withY(4).build(),
                City.builder().withX(13).withY(12).build(),
                City.builder().withX(25).withY(36).build(),
                City.builder().withX(32).withY(33).build()
        );
        World instance = new WorldBuilder().build();
        instance.addAll(cities);
        assertThat(instance.getCities()).as("list of cities").isEqualTo(cities);
    }
}