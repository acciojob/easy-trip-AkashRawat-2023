package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public class AirportService {

    @Autowired
    AirportRepository airportRepository = new AirportRepository();
    public void addAirport(Airport airport){
        airportRepository.addAirport(airport);
    }
    public String addFlight(Flight flight){
        return airportRepository.addFlight(flight);
    }
    public String addPassenger(Passenger passenger){
        return airportRepository.addPassenger(passenger);
    }
    public String bookATicket(Integer flightId,Integer passengerId){
        return airportRepository.bookATicket(flightId, passengerId);
    }
    public String getLargestAirportName(){
        return airportRepository.getLargestAirportName();
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity,City toCity){
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity, toCity);
    }
    public int getNumberOfPeopleOn(Date date, String airportName){
       return airportRepository.getNumberOfPeopleOn(date,airportName);
    }
    public String cancelATicket(Integer flightId,Integer passengerId){
        return airportRepository.cancelATicket(flightId, passengerId);
    }
    public int calculateFlightFare(Integer flightId){
        return airportRepository.calculateFlightFare(flightId);
    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }
    public String getAirportNameFromFlightId(Integer flightId){
        return airportRepository.getAirportNameFromFlightId(flightId);
    }
    public int calculateRevenueOfAFlight(Integer flightId){
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

}
