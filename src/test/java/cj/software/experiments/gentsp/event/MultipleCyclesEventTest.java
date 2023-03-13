package cj.software.experiments.gentsp.event;

import static org.mockito.Mockito.*;

import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.IndividualBuilder;
import cj.software.experiments.gentsp.entity.Population;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleCyclesEventTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = MultipleCyclesEvent.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }

    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        MultipleCyclesEvent.Builder builder = MultipleCyclesEvent.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                MultipleCyclesEvent.class);

        MultipleCyclesEvent instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getIndividualsSorted()).as("individuals").isNull();
        softy.assertThat(instance.getCycleCounter()).as("cycle counter").isNull();
        softy.assertThat(instance.getPopulationFitness()).as("population fitness").isNull();
        softy.assertThat(instance.getPopulation()).as("population").isNull();
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        List<Individual> individuals = List.of(new IndividualBuilder().build());
        Integer cycleCounter = 321;
        Double populationFitness = 47.11;
        Population population = mock(Population.class);
        MultipleCyclesEvent instance = MultipleCyclesEvent.builder()
                .withPopulation(population)
                .withIndividualsSorted(individuals)
                .withCycleCounter(cycleCounter)
                .withPopulationFitness(populationFitness)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getPopulationFitness()).as("population fitness").isEqualTo(populationFitness);
        softy.assertThat(instance.getCycleCounter()).as("cycle counter").isEqualTo(cycleCounter);
        softy.assertThat(instance.getIndividualsSorted()).as("individuals").isEqualTo(individuals);
        softy.assertThat(instance.getPopulationFitness()).as("population").isSameAs(population);
        softy.assertAll();
    }

    @Test
    void defaultIsValid() {
        MultipleCyclesEvent instance = new MultipleCyclesEventBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<MultipleCyclesEvent>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }
}