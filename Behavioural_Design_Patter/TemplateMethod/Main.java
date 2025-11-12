package Behavioural_Design_Patter.TemplateMethod;
import java.util.*;

/*
 * Understanding the Problem

    Let’s assume we are building a Notification Service where we need to send notifications via multiple channels, such as Email and SMS. Below is a simple way of how it might be implemented:
 */

class EmailNotification1{
    public void send(String to, String message){
        System.out.println("Checking rate limits for: " + to);
        System.out.println("Validating email recipients: " + to);
        String formatted = message.trim();
        System.out.println("Logging before send: " + formatted + " to " + to);

        // compose email:
        String composedMessage = "<html><body><p>" + formatted + "</p></body></html>";

        // Send Email;
        System.out.println("Sending EMAIL to: " + to + " with content:\n" + composedMessage);

        //Analytics:
        System.out.println("Analytics updated for: " + to);
    }
}

class SMSNotification1{
    public void send(String to, String message){
        System.out.println("Checking rate limits for: " + to);
        System.out.println("Validating phone number: " + to);
        String formatted = message.trim();
        System.out.println("Logging before send: " + formatted + " to " + to);

        // Compose SMS
        String composedMessage = "[SMS] " + formatted;

        // Send SMS
        System.out.println("Sending SMS to " + to + " with message: " + composedMessage);

        // Analytics (custom)
        System.out.println("Custom SMS analytics for: " + to);
    }
}


// public class Main {
//     public static void main(String[] args) {
//         EmailNotification1 emailNotification = new EmailNotification1();
//         SMSNotification1 smsNotification = new SMSNotification1();

//         //Sending Email Notification:
//         emailNotification.send("example@example.com",  "Your order has been placed!");

//         System.out.println(" ");

//         // Sending SMS Notification
//         smsNotification.send("1234567890", "Your OTP is 1234.");
//     }
// }


/*
 * Issues In This Code
 * 
 * Code Duplication
 * 
 * Hardcoded Behaviour
 * 
 * Lack of Entensibility
 * 
 * Maintenance Overhead
 * 
 */


 /*
  * The Solution
  The Template Pattern can be used to improve the structure of the previous code. By using the Template Pattern, we can eliminate duplicated logic (e.g., rate limit checks, recipient validation, logging, etc.) and define a skeleton method in a base class, while allowing the subclasses to define the specific steps such as message composition and sending.


  Here's is the revised code using the Template Pattern:

*/

abstract class NotificationSender{
    public final void send(String to, String rawMessage){
        //common logic
        rateLimitCheck(to);
        validateRecipient(to);
        String formatted = formatMessage(rawMessage);
        preSendAuditLog(to, formatted);

        //specific Logic: Defined by subclasses
        String composedMessage = composeMessage(formatted);
        sendMessage(to, composedMessage);

        //Optional Hook 
    }

    private void rateLimitCheck(String to){
        System.out.println("Checking rate limit for: " + to);
    }

    private void validateRecipient(String to){
        System.out.println("validating recipient: " + to);
    }

    private String formatMessage(String message){
        return message.trim();
    }

    private void preSendAuditLog(String to, String formatted){
        System.out.println("Loggin before send: " + formatted + " to " + to);
    }

    //Hook for subclasses to implement custom message composition
    protected abstract String composeMessage(String formattedMessage);

    //Hook for subclasses to implement custom message sending
    protected abstract void sendMessage(String to, String message);

    // Optional Hook for analytics (can be overridden)
    protected void postSendAnalytics(String to){
        System.out.println("Analytics updated for: " + to);
    }

}

class EmailNotification extends NotificationSender{
    @Override
    protected String composeMessage(String formattedMessage){
        return "<html><body><p>" + formattedMessage + "</p></body></html>";
    }

    @Override
    protected void sendMessage(String to, String message){
        System.out.println("Sending EMAIL to: " + to + " with content:\n" + message);
    }
}

class SMSNotification extends NotificationSender{
    @Override
    protected String composeMessage(String formattedMessage) {
        return "[SMS] " + formattedMessage;
    }

    // Implement SMS sending logic
    @Override
    protected void sendMessage(String to, String message) {
        System.out.println("Sending SMS to " + to + " with message: " + message);
    }

    @Override
    protected void postSendAnalytics(String to){
        System.out.println("Custom SMS Analytics for: " + to);
    }
}

class Main{
    public static void main(String[] args) {
        NotificationSender emailSender = new EmailNotification();
        emailSender.send("john@example.com" , " Welcome to The Company");

        System.out.println(" ");

        NotificationSender smsSender = new SMSNotification();
        smsSender.send("9876543210", "Your OTP is: 7261");
    }
}

