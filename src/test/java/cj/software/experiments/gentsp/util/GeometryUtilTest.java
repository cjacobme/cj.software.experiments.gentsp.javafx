package cj.software.experiments.gentsp.util;

import static org.assertj.core.api.Assertions.*;

import cj.software.experiments.gentsp.entity.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {GeometryUtil.class})
public class GeometryUtilTest {
    @Autowired
    private GeometryUtil geometryUtil;

    @Test
    void distance1() {
        City city1 = City.builder().withX(5).withY(5).build();
        City city2 = City.builder().withX(7).withY(8).build();
        distance(city1, city2, 3.60556);
    }

    @Test
    public void distance2() {
        City city1 = City.builder().withX(18).withY(27).build();
        City city2 = City.builder().withX(12).withY(4).build();
        distance(city1, city2, 23.76973);
    }

    private void distance (City city1, City city2, double expected) {
        double distance = geometryUtil.calcDistance(city1, city2);
        assertThat(distance).as("distance").isEqualTo(expected, offset(0.00001));
    }
}
