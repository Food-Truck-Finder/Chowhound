package com.chowhound.chowhound.services;

import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.repos.TruckRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SortTrucksService {
//    private final TruckRepo trucks;
//    private final List<Truck> trucks;

    public List<Truck> sortTrucks(List<Truck> trucks, String sortType) {
        switch (sortType) {
            case "rating_a":
                trucks.sort(Comparator.comparing(Truck::getAverageReviewRating));
                break;
            case "rating_d":
                trucks.sort(Comparator.comparing(Truck::getAverageReviewRating));
                Collections.reverse(trucks);
                break;
            case "name_a":
                trucks.sort((Truck t1, Truck t2) -> t1.getName().compareToIgnoreCase(t2.getName()));
                break;
            case "name_d":
                trucks.sort((Truck t1, Truck t2) -> t1.getName().compareToIgnoreCase(t2.getName()));
                Collections.reverse(trucks);
                break;
            default:
                break;
        }
        return trucks;
    }
}
