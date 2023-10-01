package org.example.tools;

import org.example.beginningClasses.HumanBeing;

import java.util.Comparator;

public class HumanComparator implements Comparator<HumanBeing> {
    Comparator<HumanBeing> realHeroComparator = Comparator.comparing(HumanBeing::isRealHero);
    Comparator<HumanBeing> coordinateXComparator = Comparator.comparing(
            HumanBeing::getCoordinateX
    );
    Comparator<HumanBeing> coordinateYComparator = Comparator.comparing(
            HumanBeing::getCoordinateY
    );
    Comparator<HumanBeing> carCoolComparator = Comparator.comparing(
    human -> human.getCar().isCool()
        );
     public int compare(HumanBeing o1, HumanBeing o2){
    return realHeroComparator
           .thenComparing(coordinateXComparator)
            .thenComparing(coordinateYComparator)
            .thenComparing(carCoolComparator)
            .compare(o2,o1);
    }
}
