package com.walt.sort;
import com.walt.model.DriverDistance;

import java.util.Comparator;

/**
 * sort DriverDistance by their total distance in descending order
 */
public class SortByDistance implements Comparator<DriverDistance> {

    /**
     * comapare 2 DriverDistance in descnding order
     * @param a DriverDistance
     * @param b DriverDistance
     * @return 0 is a's distance equals to b's. number < 0 if a's distance is bigger than b's.
     * number > 0 otherwise.
     */
    public int compare(DriverDistance a, DriverDistance b)
    {
        Long l = b.getTotalDistance() - a.getTotalDistance();
        return l.intValue();

    }

}
