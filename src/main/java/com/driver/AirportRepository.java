package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class AirportRepository {

    HashMap<String, Airport> airportDb = new HashMap<>();               //airportName --> airport
    HashMap<Integer,Flight> flightDb = new HashMap<>();                 //flightId --> flight
    HashMap<Integer, Passenger> passengerDb = new HashMap<>();          //passengerId --> passenger
    HashMap<Integer, List<Integer>> flightPassengerMapDb = new HashMap<>();    //flightId --> List of passengerId


    public void addAirport(Airport airport){                            //Airport added in database
        String key = airport.getAirportName();
        airportDb.put(key,airport);
    }
    public String addFlight(Flight flight){                             //Flight added in database
        Integer key = flight.getFlightId();
        flightDb.put(key,flight);
        return "SUCCESS";
    }
    public String addPassenger(Passenger passenger){                    //Passenger added in database
        Integer key = passenger.getPassengerId();
        passengerDb.put(key,passenger);
        return "SUCCESS";
    }
    public String bookATicket(Integer flightId,Integer passengerId){    //FlightId & PassengerId mapping

        if(flightPassengerMapDb.get(flightId).size() == flightDb.get(flightId).getMaxCapacity()){
            return "FAILURE";
        }else if(flightPassengerMapDb.get(flightId).contains(passengerId)){
            return "FAILURE";
        }
        if(flightPassengerMapDb.containsKey(flightId)){
            List<Integer> list = flightPassengerMapDb.get(flightId);
            list.add(passengerId);
            flightPassengerMapDb.put(flightId,list);
        }else{
            List<Integer> list = new ArrayList<>();
            list.add(passengerId);
            flightPassengerMapDb.put(flightId,list);
        }
        return "SUCCESS";

    }
    public String getLargestAirportName(){
        String name = "";
        Integer termial = 0;

        for(Airport airport : airportDb.values()){
            if(airport.getNoOfTerminals() > termial){
                termial = airport.getNoOfTerminals();
                name = airport.getAirportName();
            }else if(airport.getNoOfTerminals() == termial){
                if(airport.getAirportName().compareTo(name) < 0){
                    name = airport.getAirportName();
                    termial = airport.getNoOfTerminals();
                }
            }
        }
        return name;
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        Double shortestDuration = -1.0;

        for(Flight flight : flightDb.values()){
            if(flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity)){
                shortestDuration = Math.max(shortestDuration,flight.getDuration());
            }
        }
        return shortestDuration;
    }

    //NOT COMPLETED ---> PLEASE COMPLETE IT
    public int getNumberOfPeopleOn(Date date, String airportName){
        int peopleCount = 0;
        for(Flight flight : flightDb.values()){
            if((flight.getFromCity().equals(airportName) && flight.getFlightDate().compareTo(date) == 0) || (flight.getToCity().equals(airportName) && flight.getFlightDate().compareTo(date) == 0)){
                Integer requiredFlightId = flight.getFlightId();
                List<Integer>list = flightPassengerMapDb.get(requiredFlightId);
                peopleCount = peopleCount + list.size();
            }
        }
        return peopleCount;
    }

    public String cancelATicket(Integer flightId,Integer passengerId){
        try{
            List<Integer> list = flightPassengerMapDb.get(flightId);
            list.remove(passengerId);
            flightPassengerMapDb.put(flightId,list);
            return "SUCCESS";
        }catch (Exception e){
            return "FAILURE";
        }
    }
    public int calculateFlightFare(Integer flightId){

        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight for the third person will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be just checking price
         int fare = 0;
        if(flightPassengerMapDb.containsKey(flightId)){
            int count = flightPassengerMapDb.get(flightId).size();
            fare = 3000 + count * 50;
        }else if(flightDb.containsKey(flightId)){
            fare = 3000;
        }

        return fare;
    }
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){

        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :
        int count = 0;

        for(List<Integer> list : flightPassengerMapDb.values()){
            if(list.contains(passengerId)){
                count += 1;
            }
        }
        return count;
    }
    public String getAirportNameFromFlightId(Integer flightId) {

        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName
        if (flightDb.containsKey(flightId)) {
            Flight flight = flightDb.get(flightId);
            City city = flight.getFromCity();
            return city.name();
        } else {
            return null;
        }
    }
    public int calculateRevenueOfAFlight(Integer flightId){

        //Calculate the total revenue that a flight could have
        //That is of all the passengers that have booked a flight till now and then calculate the revenue
        //Revenue will also decrease if some passenger cancels the flight
        int revenue = 0;
        int noOfPassenger = 0;
        if(flightPassengerMapDb.containsKey(flightId)){
            List<Integer>list = flightPassengerMapDb.get(flightId);
            noOfPassenger = list.size();
        }
        int n = noOfPassenger;
        int first = 3000;
        int diff = 50;

        revenue = (n/2)*(2*first + (n-1)*diff);
        return revenue;
    }

}
