package Behavioural_Design_Patter.ChainOfResponsibility;
import java.util.*;

/*
    Assume we are building a customer support system for an e-commerce platform, where users raise tickets that can be of various types, such as general inquiries, refund requests, technical issues, and delivery complaints.

The following code snippet demonstrates how this functionality might be implemented in a naive way:
*/

// class SupportService{

//     public void handleRequest(String type){
//         if(type.equals("general")){
//             System.out.println("Handled by General Support");
//         }else if(type.equals("refund")){
//             System.out.println("Handled by Billing Team");
//         }else if(type.equals("technical")){
//             System.out.println("Handled by Technical Support");
//         }else if(type.equals("delivery")){
//             System.out.println("Handled by Delivery Team");
//         }else{
//             System.out.println("No handler available");
//         }
//     }
// }



/*
    The Solution

The previous code implementation can be refactored using the Chain of Responsibility principle, which helps break down the system into individual handlers responsible for different types of requests. Each handler will either process the request or pass it on to the next handler in the chain, creating a more modular and maintainable structure.

In this refactor, the SupportHandler class acts as the base class, and each specific support type (General, Billing, Technical, Delivery) will extend the SupportHandler class. This allows us to create a chain of responsibility, where each handler checks if it can process the request and, if not, passes it to the next handler in the chain.

*/

abstract class SupportHandler{
    protected SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(String requestType);
}

class GeneralSupport extends SupportHandler{
    public void handleRequest(String requestType){
        if(requestType.equalsIgnoreCase("general")){
            System.out.println("GeneralSupport: Handling general query");
        }
        else if(nextHandler != null){
            nextHandler.handleRequest(requestType);
        }
    }
}

class BillingSupport extends SupportHandler{
    public void handleRequest(String requestType){
        if(requestType.equalsIgnoreCase("refund")){
            System.out.println("BillingSupport: Handling refund request");
        }else if(nextHandler != null){
            nextHandler.handleRequest(requestType);
        }
    }
}


// Concrete Handler for Technical Support
class TechnicalSupport extends SupportHandler {
    public void handleRequest(String requestType) {
        if (requestType.equalsIgnoreCase("technical")) {
            System.out.println("TechnicalSupport: Handling technical issue");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        }
    }
}

// Concrete Handler for Delivery Support
class DeliverySupport extends SupportHandler {
    public void handleRequest(String requestType) {
        if (requestType.equalsIgnoreCase("delivery")) {
            System.out.println("DeliverySupport: Handling delivery issue");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(requestType);
        } else {
            System.out.println("DeliverySupport: No handler found for request");
        }
    }
}




public class Main {
    public static void main(String[] args) {
        // SupportService supportService = new SupportService();
    
        // supportService.handleRequest("general");
        // supportService.handleRequest("refund");
        // supportService.handleRequest("technical");
        // supportService.handleRequest("delivery");
        // supportService.handleRequest("unknown");    

        SupportHandler general = new GeneralSupport();
        SupportHandler billing = new BillingSupport();
        SupportHandler technical = new TechnicalSupport();
        SupportHandler delivery = new DeliverySupport();

        general.setNextHandler(billing);
        billing.setNextHandler(technical);
        technical.setNextHandler(delivery);

        general.handleRequest("refund");
        general.handleRequest("delivery");
        general.handleRequest("unknown");
    }
    
}
