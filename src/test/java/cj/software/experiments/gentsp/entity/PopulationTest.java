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

class PopulationTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = Population.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }


    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        Population.Builder builder = Population.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                Population.class);

        Population instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getIndividuals()).as("individuals").isNull();
        softy.assertThat(instance.getPopulationFitness()).as("population fitness").isEqualTo(0.0);
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        Individual[] individuals = new Individual[]{
                Individual.builder().withChromosomes(new int[]{1, 2, 3, 4}).build(),
                new IndividualBuilder().build()};
        double populationFitness = 3.14;
        Population instance = Population.builder()
                .withIndividuals(individuals)
                .withPopulationFitness(populationFitness)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getIndividuals()).as("individuals").isEqualTo(individuals);
        softy.assertThat(instance.getPopulationFitness()).as("population fitness").isEqualTo(populationFitness);
        softy.assertAll();

        instance.setPopulationFitness(1234.234);
        assertThat(instance.getPopulationFitness()).as("modified population fitness").isEqualTo(1234.234);
    }

    @Test
    void defaultIsValid() {
        Population instance = new PopulationBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Population>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}