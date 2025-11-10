package Behavioural_Design_Patter.StrategyPattern;

import java.util.*;

// Understanding the Problem

// Let’s say we are building a ride-matching service for a ride-hailing platform. The matching behavior changes depending on conditions such as proximity, surge areas, or airport queues.

// Here’s a naive implementation of this logic:



class RideMatchingService{
    public void matchRider(String riderLocation, String matchingType){
        if(matchingType.equals("NEAREST")){
            System.out.println("Matching rider at " + riderLocation + " with nearest driver.");
        }
        else if(matchingType.equals("SURGE_PRIORITY")){
            System.out.println("Matching rider at " + riderLocation + " based on surge pricing priority.");
        }else if(matchingType.equals("AIRPORT_QUEUE")){
            System.out.println("Matching rider at " + riderLocation + " from airport queue.");
        }else{
            System.out.println("Invalid matching strategy provided.");
        }
    }
}

// public class Main{
//     public static void main(String[] args) {
//         RideMatchingService service = new RideMatchingService();

//         service.matchRider("Downtown", "NEAREST");
//         service.matchRider("City Center", "SURGE_PRIORITY");
//         service.matchRider("Airport Terminal 3", "AIRPORT_QUEUE");
//     }
// }



// THE SOLUTION: 
//The Strategy Pattern helps eliminate complex conditional logic by encapsulating each matching algorithm into its own class. The ride-matching service then delegates the decision-making to the selected strategy at runtime. This makes the system flexible, extensible, and easier to maintain.


// ==============================
// Strategy Interface
// ==============================

interface MatchingStrategy{
    void match(String riderLocation);
}


// ==============================
// Concrete Strategy: Nearest Driver
// ==============================
class NearestDriverStrategy implements MatchingStrategy{
    @Override
    public void match(String riderLocation){
        System.out.println("Matching with the nearest available driver to " + riderLocation);
    }
}

// ==============================
// Concrete Strategy: Airport Queue
// ==============================
class AirportQueueStrategy implements MatchingStrategy{
    @Override
    public void match(String riderLacation){
        System.out.println("Matching using FIFO airport queue for: " + riderLacation);
    }
}

// ==============================
// Concrete Strategy: Surge Priority
// ==============================
class SurgePriorityStrategy implements MatchingStrategy{
    @Override
    public void match(String riderLocation){
        System.out.println("Matching rider using surge pricing priority near " + riderLocation);
    }
}


// ==============================
// Context Class: RideMatchingService
// ==============================
class RiderMatchingService{
    private MatchingStrategy strategy;

    public RiderMatchingService(MatchingStrategy strategy){
        this.strategy = strategy;
    }

    public void setStrategy(MatchingStrategy strategy){
        this.strategy = strategy;
    }

    public void matchRider(String location){
        strategy.match(location);
    }
    
}

// ==============================
// Client Code
// ==============================
public class Main{
    public static void main(String[] args) {
        RiderMatchingService riderMatchingService = new RiderMatchingService(new AirportQueueStrategy());
        riderMatchingService.matchRider("Terminal 3");

        RiderMatchingService riderMatchingService2 = new RiderMatchingService(new NearestDriverStrategy());

        riderMatchingService2.matchRider("Downtown");
        riderMatchingService2.setStrategy(new SurgePriorityStrategy());
        riderMatchingService2.matchRider("Downtown");
    }
}