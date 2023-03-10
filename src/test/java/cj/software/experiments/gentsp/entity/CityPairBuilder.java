package cj.software.experiments.gentsp.entity;

public class CityPairBuilder extends CityPair.Builder {
    public CityPairBuilder() {
        super.withCity1(City.builder().withX(13).withY(14).build())
                .withCity2(City.builder().withX(145).withY(31).build());
    }
}
