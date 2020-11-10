package com.walt;

import com.walt.exceptions.CitiesDontMatchException;
import com.walt.exceptions.NoDriversAvailableException;
import com.walt.model.*;
import java.util.Date;
import java.util.List;

public  interface WaltService{

    Delivery createOrderAndAssignDriver(Customer customer, Restaurant restaurant, Date deliveryTime) throws NoDriversAvailableException, CitiesDontMatchException;

    List<DriverDistance> getDriverRankReport();

    List<DriverDistance> getDriverRankReportByCity(City city);
}

