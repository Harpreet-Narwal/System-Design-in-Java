// The Observer Pattern is a behavioral design pattern that defines a one-to-many dependency between objects so that when one object (the subject) changes its state, all its dependents (called observers) are notified and updated automatically.

package Behavioural_Design_Patter.ObserverPattern;

import java.util.*;

//Let’s say we’re building a simple YouTube-like Notification System. Whenever a creator uploads a new video, all their subscribers should get notified.

//Below is a naive implementation of this logic:

class YoutubeChannel1 {
    public void uploadNewVideo(String videoTitle){
        //upload the video
        System.out.println("Uploading: " + videoTitle + "\n");

        // Manually notify users:
        System.out.println("Sending email to user1@email.com");
        System.out.println("Pusing in-app notification to user3@example.com");

    }
}

// public class Main {
//     public static void main(String[] args) {
//         YoutubeChannel1 channel = new YoutubeChannel1();
//         channel.uploadNewVideo("Design Pattern in Java");

//     }    
// }

// What’s Wrong with This Approach?
// Tightly Coupled Code; No Reusability; Scalability Issues; Violation of Single Responsibility Principle.



// The solution:

// ==============================
// Observer Interface
// ==============================

interface Subscriber{
    void update(String videoTitle);
}

// ==============================
// Concrete Observer: Email
// ==============================

class EmailSubscriber implements Subscriber{
    private String email;

    public EmailSubscriber(String email){
        this.email = email;
    }

    @Override
    public void update(String videTitle){
        System.out.println("Email sent to " + email + ": New video published: " + videTitle);
    }
}

class MobileAppSubscriber implements Subscriber{
    private String username;

    public MobileAppSubscriber(String username){
        this.username = username;
    }

    @Override
    public void update(String videoTitle){
        System.out.println("In-app notification for " + username + ": New Video - " + videoTitle);
    }
}

// ==============================
// Subject Interface
// ==============================

interface Channel{
    void subscribe(Subscriber subscriber);
    void unsubscribe(Subscriber subscriber);
    void notifySubscriber(String VideoTitle);
}

// ==============================
// Concrete Subject: YouTubeChannel
// ==============================

class YoutubeChannel implements Channel{
    private List<Subscriber> subscribers = new ArrayList<>();
    private String channelName;


    public YoutubeChannel(String channelName){
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscriber(String VideoTitle){
        for(Subscriber subscriber : subscribers){
            subscriber.update(VideoTitle);
        }
    }

    public void uploadVideo(String videoTitle){
        System.out.println(channelName + " uploaded: " + videoTitle + "\n");
        notifySubscriber(videoTitle);
    }

}

// ==============================
// Client Code
// ==============================

class Main{
    public static void main(String[] args) {
        YoutubeChannel happy = new YoutubeChannel("Happy");
        happy.subscribe(new MobileAppSubscriber("Harshii"));
        happy.subscribe(new EmailSubscriber("Sudha"));

        happy.uploadVideo("Observer-pattern");
    }
}
