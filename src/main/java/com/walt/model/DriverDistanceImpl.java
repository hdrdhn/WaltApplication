package com.walt.model;

import com.walt.model.Driver;
import com.walt.model.DriverDistance;

public class DriverDistanceImpl implements DriverDistance {
    private Driver driver;
    private Long totalDistance;

    public DriverDistanceImpl(Driver driver, Long totalDistance) {
        this.driver = driver;
        this.totalDistance = totalDistance;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public Long getTotalDistance() {
        return this.totalDistance;
    }

    public void setTotalDistance(Long distance) {
        this.totalDistance = distance;
    }
}
