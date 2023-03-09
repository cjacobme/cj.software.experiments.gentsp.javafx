package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.City;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

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

    @Test
    public void calcMinDistance1() {
        City toBeChecked = City.builder().withX(2).withY(8).build();
        Collection<City> collection = List.of(
                City.builder().withX(1).withY(2).build(),
                City.builder().withX(5).withY(12).build(),
                City.builder().withX(4).withY(9).build(),
                City.builder().withX(7).withY(3).build()
        );
        calcMinDistance(toBeChecked, collection, 2.23607);
    }

    @Test
    public void calcMinDistance2() {
        City toBeChecked = City.builder().withX(5).withY(4).build();
        Collection<City> collection = List.of(
                City.builder().withX(2).withY(12).build(),
                City.builder().withX(6).withY(5).build()
        );
        calcMinDistance(toBeChecked, collection, 1.41421);
    }

    @Test
    public void calcMinDistanceWithEmptyList() {
        City toBeChecked = City.builder().withX(131).withY(242).build();
        Collection<City> empty = new ArrayList<>();
        calcMinDistance(toBeChecked, empty, Double.MAX_VALUE);
    }

    private void calcMinDistance(City toBeChecked, Collection<City> collection, double expected) {
        double minDistance = geometryUtil.calcMinDistance(toBeChecked, collection);
        assertThat(minDistance).as("min distance").isEqualTo(expected, offset(0.00001));
    }

    @Test
    public void transformTo2D1() {
        City city = City.builder().withX(5).withY(10).build();
        double widthRatio = 1.2;
        double heightRation = 2.5;
        Point2D expected = new Point2D(6.0, 25.0);
        transform2d(city, widthRatio, heightRation, expected);
    }

    @Test
    public void transformTo2D2() {
        City city = City.builder().withX(20).withY(30).build();
        Point2D expected = new Point2D(40.0, 90.0);
        transform2d(city, 2.0, 3.0, expected);
    }

    private void transform2d(City city, double widthRatio, double heightRatio, Point2D expected) {
        Point2D actual = geometryUtil.transformToPoint2D(city, widthRatio, heightRatio);
        assertThat(actual).as("tranformed Point2D").isEqualTo(expected);
    }
}
