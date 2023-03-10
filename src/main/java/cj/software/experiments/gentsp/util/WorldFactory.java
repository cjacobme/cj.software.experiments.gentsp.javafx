package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.City;
import cj.software.experiments.gentsp.entity.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorldFactory {
    @Autowired
    private RandomUtil randomUtil;

    @Autowired
    private GeometryUtil geometryUtil;

    private final Logger logger = LogManager.getFormatterLogger();

    public World createWorld(int width, int height, int numCities) {
        World result = World.builder().withWidth(width).withHeight(height).build();
        List<City> allCities = new ArrayList<>();
        for (int iCity = 0; iCity < numCities; iCity++) {
            boolean tooClose = true;
            while (tooClose) {
                int x = randomUtil.nextRandomValue(width);
                int y = randomUtil.nextRandomValue(height);
                City city = City.builder().withX(x).withY(y).build();
                double minDistance = geometryUtil.calcMinDistance(city, allCities);
                if (minDistance > City.DIAMETER) {
                    tooClose = false;
                    allCities.add(city);
                } else {
                    logger.warn("min distance %7.3f for city #%d is too close, try once more",
                            minDistance,
                            iCity);
                }
            }
        }
        result.addAll(allCities);
        return result;
    }
}
