package com.walt;

import com.walt.exceptions.CitiesDontMatchException;
import com.walt.exceptions.NoDriversAvailableException;
import com.walt.dao.*;
import com.walt.model.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest()
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WaltTest {

    @TestConfiguration
    static class WaltServiceImplTestContextConfiguration {

        @Bean
        public WaltService waltService() {
            return new WaltServiceImpl();
        }
    }

    @Autowired
    WaltService waltService;

    @Resource
    CityRepository cityRepository;

    @Resource
    CustomerRepository customerRepository;

    @Resource
    DriverRepository driverRepository;

    @Resource
    DeliveryRepository deliveryRepository;

    @Resource
    RestaurantRepository restaurantRepository;

    @BeforeEach()
    public void prepareData() throws NoDriversAvailableException, CitiesDontMatchException {

        City jerusalem = new City("Jerusalem");
        City tlv = new City("Tel-Aviv");
        City bash = new City("Beer-Sheva");
        City haifa = new City("Haifa");

        cityRepository.save(jerusalem);
        cityRepository.save(tlv);
        cityRepository.save(bash);
        cityRepository.save(haifa);

        createDrivers(jerusalem, tlv, bash, haifa);

        createCustomers(jerusalem, tlv, haifa);

        createRestaurant(jerusalem, tlv);

        createDeliveries();
    }

    private void createRestaurant(City jerusalem, City tlv) {
        Restaurant meat = new Restaurant("meat", jerusalem, "All meat restaurant");
        Restaurant vegan = new Restaurant("vegan", tlv, "Only vegan");
        Restaurant cafe = new Restaurant("cafe", tlv, "Coffee shop");
        Restaurant chinese = new Restaurant("chinese", tlv, "chinese restaurant");
        Restaurant mexican = new Restaurant("restaurant", tlv, "mexican restaurant ");

        restaurantRepository.saveAll(Lists.newArrayList(meat, vegan, cafe, chinese, mexican));
    }

    private void createCustomers(City jerusalem, City tlv, City haifa) {
        Customer beethoven = new Customer("Beethoven", tlv, "Ludwig van Beethoven");
        Customer mozart = new Customer("Mozart", jerusalem, "Wolfgang Amadeus Mozart");
        Customer chopin = new Customer("Chopin", haifa, "Frédéric François Chopin");
        Customer rachmaninoff = new Customer("Rachmaninoff", tlv, "Sergei Rachmaninoff");
        Customer bach = new Customer("Bach", tlv, "Sebastian Bach. Johann");

        customerRepository.saveAll(Lists.newArrayList(beethoven, mozart, chopin, rachmaninoff, bach));
    }

    private void createDrivers(City jerusalem, City tlv, City bash, City haifa) {
        Driver mary = new Driver("Mary", tlv);
        Driver patricia = new Driver("Patricia", tlv);
        Driver jennifer = new Driver("Jennifer", haifa);
        Driver james = new Driver("James", bash);
        Driver john = new Driver("John", bash);
        Driver robert = new Driver("Robert", jerusalem);
        Driver david = new Driver("David", jerusalem);
        Driver daniel = new Driver("Daniel", tlv);
        Driver noa = new Driver("Noa", haifa);
        Driver ofri = new Driver("Ofri", haifa);
        Driver nata = new Driver("Neta", jerusalem);

        driverRepository.saveAll(Lists.newArrayList(mary, patricia, jennifer, james, john, robert, david, daniel, noa, ofri, nata));
    }

    private void createDeliveries() {

        Delivery delivery1 = new Delivery(
                driverRepository.findByName("Robert"),
                restaurantRepository.findByName("meat"),
                customerRepository.findByName("Mozart"),
                new Date(2020, Calendar.DECEMBER, 6)
        );
        delivery1.setDistance(3);
        Delivery delivery2 = new Delivery(
                driverRepository.findByName("Robert"),
                restaurantRepository.findByName("meat"),
                customerRepository.findByName("Mozart"),
                new Date(2020, Calendar.DECEMBER, 7)
        );
        delivery2.setDistance(6);
        Delivery delivery3 = new Delivery(
                driverRepository.findByName("Robert"),
                restaurantRepository.findByName("meat"),
                customerRepository.findByName("Mozart"),
                new Date(2020, Calendar.DECEMBER, 8)
        );
        delivery3.setDistance(1);
        Delivery delivery4 = new Delivery(
                driverRepository.findByName("David"),
                restaurantRepository.findByName("meat"),
                customerRepository.findByName("Mozart"),
                new Date(2020, Calendar.DECEMBER, 6)
        );
        delivery4.setDistance(11);
        Delivery delivery5 = new Delivery(
                driverRepository.findByName("David"),
                restaurantRepository.findByName("meat"),
                customerRepository.findByName("Mozart"),
                new Date(2020, Calendar.DECEMBER, 7)
        );
        delivery5.setDistance(5);
        Delivery delivery6 = new Delivery(
                driverRepository.findByName("Neta"),
                restaurantRepository.findByName("meat"),
                customerRepository.findByName("Mozart"),
                new Date(2020, Calendar.DECEMBER, 8)
        );
        delivery6.setDistance(19);
        Delivery delivery7 = new Delivery(
                driverRepository.findByName("Mary"),
                restaurantRepository.findByName("cafe"),
                customerRepository.findByName("Beethoven"),
                new Date(2020, Calendar.DECEMBER, 8)
        );
        delivery7.setDistance(2);
        Delivery delivery8 = new Delivery(
                driverRepository.findByName("Mary"),
                restaurantRepository.findByName("cafe"),
                customerRepository.findByName("Beethoven"),
                new Date(2020, Calendar.DECEMBER, 8)
        );
        delivery8.setDistance(1);
        Delivery delivery9 = new Delivery(
                driverRepository.findByName("Daniel"),
                restaurantRepository.findByName("cafe"),
                customerRepository.findByName("Beethoven"),
                new Date(2020, Calendar.DECEMBER, 8)
        );
        delivery9.setDistance(7);
        Delivery delivery10 = new Delivery(
                driverRepository.findByName("Patricia"),
                restaurantRepository.findByName("cafe"),
                customerRepository.findByName("Beethoven"),
                new Date(2020, Calendar.DECEMBER, 8)
        );
        delivery10.setDistance(16);
        Delivery delivery11 = new Delivery(
                driverRepository.findByName("Patricia"),
                restaurantRepository.findByName("cafe"),
                customerRepository.findByName("Beethoven"),
                new Date(2020, Calendar.DECEMBER, 9)
        );
        delivery11.setDistance(10);
        Delivery delivery12 = new Delivery(
                driverRepository.findByName("Patricia"),
                restaurantRepository.findByName("cafe"),
                customerRepository.findByName("Beethoven"),
                new Date(2020, Calendar.DECEMBER, 10)
        );
        delivery12.setDistance(4);
        Iterable<Delivery> deliveries = Lists.newArrayList(
                delivery1, delivery2,
                delivery3, delivery4, delivery5, delivery6, delivery7, delivery8, delivery9, delivery10,
                delivery11,delivery12);
        deliveryRepository.saveAll(deliveries);
    }


    /**
     * Tests createOrderAndAssignDriver
     * Test case - customers are trying to order from restaurants, where the customer's city is different from
     * the restaurant's city.
     * Expected result - CitiesDontMatchException
     */
    @Test
    public void testCitiesDontMatch() {

//        Delivery d1 = waltService.createOrderAndAssignDriver(
//                c1 ,r1,new Date());
//        assertNull(d1);
        assertThrows(CitiesDontMatchException.class, () -> {
            Customer c1 = customerRepository.findByName("Chopin"); //Haifa
            Restaurant r1 = restaurantRepository.findByName("cafe"); //Tel-Aviv
            waltService.createOrderAndAssignDriver(c1, r1, new Date());
        });

        assertThrows(CitiesDontMatchException.class, () -> {
            Customer c1 = customerRepository.findByName("Beethoven"); //Tel-Aviv
            Restaurant r1 = restaurantRepository.findByName("meat"); //Jerusalem
            waltService.createOrderAndAssignDriver(c1, r1, new Date());
        });
//        c1 = customerRepository.findByName("Beethoven"); //Tel-Aviv
//        r1 = restaurantRepository.findByName("meat"); //Jerusalem
//        d1 = waltService.createOrderAndAssignDriver(
//                c1 ,r1,new Date());
//        assertNull(d1);
    }


    /**
     * Tests createOrderAndAssignDriver
     * Test case - a few drivers are available, the least busy one should be chosen ( for example -
     * David has 3 deliveries, Robert had 2 deliveries, Neta has 1 delivery. hence - Neta should be chosen)
     * @throws NoDriversAvailableException
     * @throws CitiesDontMatchException
     */
    @Test
    public void leastBusyDriverIsChosen() throws NoDriversAvailableException, CitiesDontMatchException {
        Customer c1 = customerRepository.findByName("Mozart"); //Jerusalem
        Restaurant r1 = restaurantRepository.findByName("meat"); //Jerusalem
        Delivery d1 = waltService.createOrderAndAssignDriver(
                c1, r1, new Date());

        assertEquals("Neta", d1.getDriver().getName());

        c1 = customerRepository.findByName("Bach"); //Tel-Aviv
        r1 = restaurantRepository.findByName("vegan"); //Tel-Aviv
        d1 = waltService.createOrderAndAssignDriver(
                c1, r1, new Date());

        assertEquals("Daniel", d1.getDriver().getName());
    }

    /**
     * Tests createOrderAndAssignDriver
     * Test case - a customer from Tel-Aviv trying to order from a restaurant in Tel-Aviv,
     * but all the drivers from Tel-Aviv are currently busy with other deliveries.
     * Expected result - NoDriversAvailableException
     */
    @Test
    public void NoAvailableDriverInCity1() {
        assertThrows(NoDriversAvailableException.class, () -> {
            Customer c1 = customerRepository.findByName("Rachmaninoff"); //Tel-Aviv
            Restaurant r1 = restaurantRepository.findByName("cafe"); //Tel-Aviv
            waltService.createOrderAndAssignDriver(c1, r1, new Date(2020, Calendar.DECEMBER, 8));
        });
    }


    /**
     * Tests createOrderAndAssignDriver
     * Test case - a customer from Tel-Aviv trying to order from a restaurant in Tel-Aviv,
     * but all the drivers from Tel-Aviv are going to be busy with other deliveries within the next
     * hour. hence-there is no driver available.
     * Expected result - NoDriversAvailableException
     */
//    @Test
//    public void NoAvailableDriverInCity2() {
//        assertThrows(NoDriversAvailableException.class, () -> {
//            Customer c1 = customerRepository.findByName("Rachmaninoff"); //Tel-Aviv
//            Restaurant r1 = restaurantRepository.findByName("cafe"); //Tel-Aviv
//            waltService.createOrderAndAssignDriver(c1, r1, new Date(2020, 11, 8,0,30));
//        });
//    }

    /**
     * Test case - a customer from Raanana trying to order from a restaurant in Raanana, where there are
     * no drivers
     * Expected result - NoDriversAvailableException
     */
    @Test
    public void NoAvailableDriverInCity2() {

        City raanana = new City("Raanana");
        cityRepository.save(raanana);
        Restaurant restaurantInRaanana = new Restaurant("restaurant", raanana, "restaurantInRaanana");
        Customer raanan = new Customer("Raanan", raanana, "Raanan from raanana");

        assertThrows(NoDriversAvailableException.class, () -> {
            waltService.createOrderAndAssignDriver(raanan, restaurantInRaanana, new Date(2020, Calendar.DECEMBER, 8));
        });
    }


    /**
     * checks if the output of DriverRankReportTest is correct according to the given database of the
     * deliveries
     */
    @Test
    public void DriverRankReportTest()
    {
        List<DriverDistance> dd = waltService.getDriverRankReport();
        assertEquals(driverRepository.findByName("Patricia").getId(),dd.get(0).getDriver().getId());
        assert(dd.get(0).getTotalDistance() == 30);
        assertEquals(driverRepository.findByName("Neta").getId(),dd.get(1).getDriver().getId());
        assert(dd.get(1).getTotalDistance() == 19);
        assertEquals(driverRepository.findByName("David").getId(),dd.get(2).getDriver().getId());
        assert(dd.get(2).getTotalDistance() == 16);
        assertEquals(driverRepository.findByName("Robert").getId(),dd.get(3).getDriver().getId());
        assert(dd.get(3).getTotalDistance() == 10);
        assertEquals(driverRepository.findByName("Daniel").getId(),dd.get(4).getDriver().getId());
        assert(dd.get(4).getTotalDistance() == 7);
        assertEquals(driverRepository.findByName("Mary").getId(),dd.get(5).getDriver().getId());
        assert(dd.get(5).getTotalDistance() == 3);

        for(int i=6;i<dd.size();i++) //the rest must be 0, in no particular order
        {
            assert(dd.get(i).getTotalDistance() == 0);
        }

    }

    /**
     * checks if the output of getDriverRankReportByCity (city - Jerusalem), is correct according
     * to the given database of the deliveries.
     */
    @Test
    public void DriverRankReportTestJerusalem()
    {
        List<DriverDistance> dd = waltService.getDriverRankReportByCity(cityRepository.findByName("Jerusalem"));
        assertEquals(driverRepository.findByName("Neta").getId(),dd.get(0).getDriver().getId());
        assert(dd.get(0).getTotalDistance() == 19);
        assertEquals(driverRepository.findByName("David").getId(),dd.get(1).getDriver().getId());
        assert(dd.get(1).getTotalDistance() == 16);
        assertEquals(driverRepository.findByName("Robert").getId(),dd.get(2).getDriver().getId());
        assert(dd.get(2).getTotalDistance() == 10);
        assert(dd.size() == 3); //no more drivers in Jerusalem
    }


    /**
     * checks if the output of getDriverRankReportByCity (city - Tel-Aviv), is correct according
     * to the given database of the deliveries.
     */
    @Test
    public void DriverRankReportTestTelAviv() {

        List<DriverDistance> dd = waltService.getDriverRankReportByCity(cityRepository.findByName("Tel-Aviv"));
        assertEquals(driverRepository.findByName("Patricia").getId(),dd.get(0).getDriver().getId());
        assert(dd.get(0).getTotalDistance() == 30);
        assertEquals(driverRepository.findByName("Daniel").getId(),dd.get(1).getDriver().getId());
        assert(dd.get(1).getTotalDistance() == 7);
        assertEquals(driverRepository.findByName("Mary").getId(),dd.get(2).getDriver().getId());
        assert(dd.get(2).getTotalDistance() == 3);
        assert(dd.size() == 3); //no more drivers in Tel Aviv
    }


    /**
     * checks if the output of getDriverRankReportByCity (city - Haifa), is correct according
     * to the given database of the deliveries. there are no deliveries in Haifa, hence total distance of
     * each driver is 0.
     */
    @Test
    public void DriverRankReportTestHaifa() //drivers in Haifa have total distance of 0
    {
        List<DriverDistance> dd = waltService.getDriverRankReportByCity(cityRepository.findByName("Haifa"));
        for (DriverDistance driverDistance: dd)
        {
            assert (driverDistance.getTotalDistance() == 0);
        }

    }





}
