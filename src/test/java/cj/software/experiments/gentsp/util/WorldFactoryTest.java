package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.City;
import cj.software.experiments.gentsp.entity.World;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = WorldFactory.class)
public class WorldFactoryTest {
    @Autowired
    private WorldFactory factory;

    @MockBean
    private RandomUtil randomUtil;

    @MockBean
    private GeometryUtil geometryUtil;

    @Test
    public void buildWith3Cities() {
        // setup data
        int width = 100;
        int height = 200;
        int numCities = 3;
        City city1 = City.builder().withX(3).withY(121).build();
        City city2 = City.builder().withX(4).withY(131).build();
        City city3 = City.builder().withX(5).withY(141).build();
        List<City> list1 = List.of(city1);
        List<City> list2 = List.of(city1, city2);
        List<City> list3 = List.of(city1, city2, city3);

        // mock beans
        when(randomUtil.nextRandomValue(100)).thenReturn(3, 4, 5);
        when(randomUtil.nextRandomValue(200)).thenReturn(121, 131, 141);
        when(geometryUtil.calcMinDistance(city1, Collections.emptyList())).thenReturn(WorldFactory.DIAMETER + 0.01);
        when(geometryUtil.calcMinDistance(city2, list1)).thenReturn(WorldFactory.DIAMETER + 0.1);
        when(geometryUtil.calcMinDistance(city3, list2)).thenReturn(WorldFactory.DIAMETER + 0.1);

        // invoke
        World world = factory.createWorld(width, height, numCities);

        // checks
        assertThat(world).as("world").isNotNull();
        verify(randomUtil, times(3)).nextRandomValue(width);
        verify(randomUtil, times(3)).nextRandomValue(height);
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(world.getWidth()).as("width").isEqualTo(width);
        softy.assertThat(world.getHeight()).as("height").isEqualTo(height);
        softy.assertThat(world.getCities()).as("cities").isEqualTo(list3);
        softy.assertAll();
    }
    
    @Test
    public void build4CitiesWithOneTooClose() {
        // setup data
        int width = 150;
        int height = 250;
        int numCities = 4;
        City city1 = City.builder().withX(25).withY(25).build();
        City city2 = City.builder().withX(30).withY(35).build();
        City city3 = City.builder().withX(35).withY(45).build();
        City city4 = City.builder().withX(40).withY(55).build();
        City tooClose = City.builder().withX(26).withY(26).build();
        List<City> list1 = List.of(city1);
        List<City> list2 = List.of(city1, city2);
        List<City> list3 = List.of(city1, city2, city3);
        List<City> list4 = List.of(city1, city2, city3, city4);

        // mock beans
        when(randomUtil.nextRandomValue(150)).thenReturn(25, 30, 26, 35, 40);
        when(randomUtil.nextRandomValue(250)).thenReturn(25, 35, 26, 45, 55);
        when(geometryUtil.calcMinDistance(city1, Collections.emptyList())).thenReturn(3453.8);
        when(geometryUtil.calcMinDistance(city2, list1)).thenReturn(223344.55);
        when(geometryUtil.calcMinDistance(city3, list2)).thenReturn(334455.66);
        when(geometryUtil.calcMinDistance(tooClose, list3)).thenReturn((double)WorldFactory.DIAMETER);
        when(geometryUtil.calcMinDistance(city4, list3)).thenReturn(334432.2);

        // invoke
        World world = factory.createWorld(width, height, numCities);

        // checks
        assertThat(world).as("world").isNotNull();
        verify(randomUtil, times(5)).nextRandomValue(width);
        verify(randomUtil, times(5)).nextRandomValue(height);
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(world.getWidth()).as("width").isEqualTo(width);
        softy.assertThat(world.getHeight()).as("height").isEqualTo(height);
        softy.assertThat(world.getCities()).as("cities").isEqualTo(list4);
        softy.assertAll();

    }
}
