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

class IndividualTest {

    @Test
    void implementsSerializable() {
        Class<?>[] interfaces = Individual.class.getInterfaces();
        assertThat(interfaces).as("interfaces").contains(Serializable.class);
    }


    @Test
    void constructEmpty()
            throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        Individual.Builder builder = Individual.builder();
        assertThat(builder).as("builder").isNotNull();

        Field field = builder.getClass().getDeclaredField("instance");

        Object instanceBefore = field.get(builder);
        assertThat(instanceBefore).as("instance in builder before build").isNotNull().isInstanceOf(
                Individual.class);

        Individual instance = builder.build();
        assertThat(instance).as("built instance").isNotNull();

        Object instanceAfter = field.get(builder);
        assertThat(instanceAfter).as("instance in builder after build").isNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getChromosome()).as("chromosomes").isNull();
        softy.assertThat(instance.getCycleCounter()).as("cycle counter").isZero();
        softy.assertThat(instance.getPopulationCounter()).as("population counter").isZero();
        softy.assertThat(instance.getDistanceSum()).as("distance some").isEqualTo(0.0);
        softy.assertThat(instance.getFitnessValue()).as("fitness value").isEqualTo(0.0);
        softy.assertAll();
    }

    @Test
    void constructFilled() {
        int cycleCounter = 3;
        int populationCounter = 5;
        double distanceSum = 5.678;
        double fitnessValue = 0.5432;
        int[] chromosomes = new int[]{1, 2, 3, 4, 5};
        Individual instance = Individual.builder()
                .withCycleCounter(cycleCounter)
                .withPopulationCounter(populationCounter)
                .withChromosomes(chromosomes)
                .withDistanceSum(distanceSum)
                .withFitnessValue(fitnessValue)
                .build();
        assertThat(instance).as("built instance").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(instance.getCycleCounter()).as("cycle counter").isEqualTo(cycleCounter);
        softy.assertThat(instance.getPopulationCounter()).as("population counter").isEqualTo(populationCounter);
        softy.assertThat(instance.getChromosome()).as("chromosomes").isEqualTo(chromosomes);
        softy.assertThat(instance.getDistanceSum()).as("distance sum").isEqualTo(distanceSum);
        softy.assertThat(instance.getFitnessValue()).as("fitness value").isEqualTo(fitnessValue);
        softy.assertAll();

        distanceSum = 6.789;
        fitnessValue = 10.2334;
        instance.setDistanceSum(distanceSum);
        instance.setFitnessValue(fitnessValue);
        softy = new SoftAssertions();
        softy.assertThat(instance.getDistanceSum()).as("distance sum").isEqualTo(distanceSum);
        softy.assertThat(instance.getFitnessValue()).as("fitness value").isEqualTo(fitnessValue);
        softy.assertAll();

        instance.setGene(3, 25);
        assertThat(instance.getGene(3)).as("gene[3]").isEqualTo(25);
    }

    @Test
    void defaultIsValid() {
        Individual instance = new IndividualBuilder().build();
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Individual>> violations = validator.validate(instance);
            assertThat(violations).as("constraint violations").isEmpty();
        }
    }

    @Test
    void stringPresentation() {
        Individual individual = new IndividualBuilder().build();
        String asString = individual.toString();
        assertThat(asString)
                .as("String presentation")
                .isEqualTo("Individual[id=(156,2),order=33,22,15,78]");
    }

    @Test
    void equalObjects() {
        Individual individual1 = new IndividualBuilder().build();
        Individual individual2 = new IndividualBuilder().build();
        assertThat(individual1).as("equal objects").isEqualTo(individual2);
    }

    @Test
    void equalHashCodes() {
        Individual individual1 = new IndividualBuilder().build();
        Individual individual2 = new IndividualBuilder().build();
        int hash1 = individual1.hashCode();
        int hash2 = individual2.hashCode();
        assertThat(hash1).as("equal hashes").isEqualTo(hash2);
    }

    @Test
    void unequalCycleCounter() {
        Individual individual1 = new IndividualBuilder().build();
        Individual individual2 = new IndividualBuilder().withCycleCounter(0).build();
        assertThat(individual1).as("unequal cycle counter").isNotEqualTo(individual2);
    }
    @Test
    void unequalCycleCounterHashes() {
        Individual individual1 = new IndividualBuilder().build();
        Individual individual2 = new IndividualBuilder().withCycleCounter(0).build();
        int hash1 = individual1.hashCode();
        int hash2 = individual2.hashCode();
        assertThat(hash1).as("unequal cycle counter hashes").isNotEqualTo(hash2);
    }

    @Test
    void unequalPopulationCounter() {
        Individual individual1 = new IndividualBuilder().build();
        Individual individual2 = new IndividualBuilder().withPopulationCounter(0).build();
        assertThat(individual1).as("unequal Population counter").isNotEqualTo(individual2);
    }
    @Test
    void unequalPopulationCounterHashes() {
        Individual individual1 = new IndividualBuilder().build();
        Individual individual2 = new IndividualBuilder().withPopulationCounter(0).build();
        int hash1 = individual1.hashCode();
        int hash2 = individual2.hashCode();
        assertThat(hash1).as("unequal Population counter hashes").isNotEqualTo(hash2);
    }

    @Test
    void unequalOtherObject() {
        Individual individual = new IndividualBuilder().build();
        Object other = "asdf";
        assertThat(individual).as("individual").isNotEqualTo(other);
    }
}