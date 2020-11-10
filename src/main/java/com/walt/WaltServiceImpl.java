package com.walt;

import com.walt.exceptions.CitiesDontMatchException;
import com.walt.exceptions.NoDriversAvailableException;
import com.walt.dao.DeliveryRepository;
import com.walt.dao.DriverRepository;
import com.walt.model.*;
import com.walt.sort.SortByDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WaltServiceImpl implements WaltService {


    /**
     * minimum possible distance for a delivery
     */
    private static final int MIN_DIST = 0;

    /**
     * maximum possible distance for a delivery
     */
    private static final int MAX_DIST = 20;


    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DriverRepository driverRepository;


    /**
     * createOrderAndAssignDriver - create delivery and assign a driver that is free at te given time and
     * is the least busy one.
     * @param customer customer to create a delivery for
     * @param restaurant the delivery is from restaurant
     * @param deliveryTime given time for delivery
     * @return new Delivery for customer, from restaurant, at deliveryTime, with the least busy driver.
     * @throws NoDriversAvailableException
     * @throws CitiesDontMatchException
     */
    @Override
    public Delivery createOrderAndAssignDriver(Customer customer, Restaurant restaurant, Date deliveryTime)
            throws NoDriversAvailableException, CitiesDontMatchException {

            if (!customer.getCity().getId().equals(restaurant.getCity().getId())) {
                throw new CitiesDontMatchException();
            }

        List<Driver> availableDrivers = new ArrayList<>();
        List<Driver> driverArr = this.driverRepository.findAllDriversByCity(customer.getCity());
        for (Driver driver : driverArr) {

            if (isDriverAvailable(driver, deliveryTime)) {
                availableDrivers.add(driver);
            }
        }
        if (availableDrivers.isEmpty()) {
            throw new NoDriversAvailableException();
        }

        Driver leastBusyDriver = getLeastBusyDriver(availableDrivers);
        Delivery newDelivery = new Delivery(leastBusyDriver, restaurant, customer, deliveryTime);
        int random = getRandom();
        newDelivery.setDistance(random);

        return newDelivery;
    }

    /**
     * @param drivers list of available drivers
     * @return least busy driver from all available drivers
     */
    private Driver getLeastBusyDriver(List<Driver> drivers) {
        Map<Long, Integer> business = new HashMap<>(); //business maps driver's ID (Long) to the number of
                                                        // deliveries this driver has(Integer).

        for (Driver driver : drivers) {
            business.put(driver.getId(), 0); //sets the initial number of deliveries to 0
        }

        for (Delivery delivery : this.deliveryRepository.findAll()) {
            if (business.containsKey(delivery.getDriver().getId())) { //update the number of deliveries for this driver
                business.put(delivery.getDriver().getId(), business.get(delivery.getDriver().getId()) + 1);
            }
        }

        //get the the driver id with the lowest number of deliveries
        Long leastBusyDriverID = GetDriverIdWithMinDeliveries(business);
        if (this.driverRepository.findById(leastBusyDriverID).isPresent()) {
            return this.driverRepository.findById(leastBusyDriverID).get();
        }
        return null;
    }

    /**
     * iterate over the business map and find the driver id with the lowest number of deliveries
     * @param business maps driver ID to his number of deliveries
     * @return least Busy Driver ID
     */
    private Long GetDriverIdWithMinDeliveries(Map<Long, Integer> business) {
        Long leastBusyDriverID = null;
        for (Map.Entry<Long, Integer> entry : business.entrySet()) {
            if (leastBusyDriverID == null) leastBusyDriverID = entry.getKey();
            else {
                if (entry.getValue() < business.get(leastBusyDriverID)) {
                    leastBusyDriverID = entry.getKey();
                }
            }
        }
        return leastBusyDriverID;
    }

    /**
     * isDriverAvailable - checks if a driver is available for a delivery at a given time.
     * going over all other deliveries and check if this driver has already been booked for a different delivery.
     * @param driver check if the given driver is available
     * @param deliveryTime wanted time for the delivery
     * @return true - if the driver is available at deliveryTime, false otherwise.
     */
    private boolean isDriverAvailable(Driver driver, Date deliveryTime) {
        Iterable<Delivery> allDeliveries = this.deliveryRepository.findAll();
        for (Delivery delivery : allDeliveries) {
            if (delivery.getDriver().getId().equals(driver.getId())
                    && (delivery.getDeliveryTime().getTime() ==  deliveryTime.getTime())) {
                return false; //the driver is booked for a different delivery at deliveryTime
            }
        }
        return true;
    }

    /**
     * getDriverRankReport - create a List of DriverDistance (reportList), iterate over all deliveries
     * and update the total distance of each driver in reportList, according to the distance of the delivery.
     * sort DriverDistance objects in reportList by their totalDistance
     * @return reportList
     */
    @Override
    public List<DriverDistance> getDriverRankReport() {

        List<DriverDistance> reportList = new ArrayList<>();
        Iterable<Driver> drivers = this.driverRepository.findAll();
        for (Driver driver : drivers) {
            reportList.add(new DriverDistanceImpl(driver, 0L));
        }
        for (Delivery delivery : this.deliveryRepository.findAll()) {
            Driver driver = delivery.getDriver();
            for (DriverDistance dd: reportList)
            {
                if (driver.getId().equals(dd.getDriver().getId()))
                {
                    dd.setTotalDistance(dd.getTotalDistance() + Math.round(delivery.getDistance()));
                    break;
                }
            }

        }

        Collections.sort(reportList, new SortByDistance());
        return reportList;
    }


    /**
     * getDriverRankReport - create a List of DriverDistance with drivers from the given city(reportList),
     * iterate over all deliveries and update the total distance of each driver in reportList,
     * according to the distance of the delivery.
     * sort DriverDistance objects in reportList by their totalDistance.
     * @param city
     * @return reportList
     */
    @Override
    public List<DriverDistance> getDriverRankReportByCity(City city) {
        List<DriverDistance> reportList = new ArrayList<>();
        Iterable<Driver> drivers = this.driverRepository.findAllDriversByCity(city);
        for (Driver driver : drivers) {
            reportList.add(new DriverDistanceImpl(driver, 0L));
        }
        for (Delivery delivery : this.deliveryRepository.findAll()) {
            Driver driver = delivery.getDriver();
            for (DriverDistance dd: reportList) //TODO BAD SEARCH
            {
                if (driver.getId().equals(dd.getDriver().getId()))
                {
                    dd.setTotalDistance(dd.getTotalDistance() + Math.round(delivery.getDistance())); //TODO ROUND
                    break;
                }
            }

        }

        Collections.sort(reportList, new SortByDistance());
        return reportList;

    }


    /**
     * @return random number is between MIN_DIST to MAX_DIST
     */
    private int getRandom() {
        Random r = new Random();
        return r.nextInt(MAX_DIST - MIN_DIST) + MIN_DIST;

    }
}
