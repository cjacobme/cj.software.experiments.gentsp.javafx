package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CityFactory {
    @Autowired
    private RandomUtil randomUtil;

    public List<City> createCities(int numCities) {
        Set<City> set = createCitiesSet(numCities);
        List<City> result = new ArrayList<>(set);
        return result;
    }

    private Set<City> createCitiesSet(int numCities) {
        Set<City> result = new HashSet<>();
        while (result.size() < numCities) {
            int x = (int) (100 * randomUtil.nextRandomValue());
            int y = (int) (100 * randomUtil.nextRandomValue());
            City candidate = City.builder()
                    .withX(x)
                    .withY(y)
                    .build();
            result.add(candidate);
        }
        return result;
    }

}
