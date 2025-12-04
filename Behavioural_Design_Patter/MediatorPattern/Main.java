package Behavioural_Design_Patter.MediatorPattern;
import java.util.*;


/*
Understanding the Problem

Let’s imagine a collaborative document editor where users can make changes to a shared document. Each user has the ability to give access to other users, enabling them to collaborate on the same document. The following code snippet demonstrates how this functionality might be implemented:
*/


// class User{
//     private String name;
//     private List<User> others;

//     public User(String name){
//         this.name = name;
//         this.others = new ArrayList<>();
//     }

//     public void addCollaborator(User user){
//         others.add(user);
//     }

//     public void makeChange(String change){
//         System.out.println(name + " made a change: " + change);
//         for(User u : others){
//             u.receiveChange(change, this);
//         }
//     }

//     public void receiveChange(String change, User from){
//         System.out.println(name + " received: \"" + change + "\" from" + from.name);
//     }
// }


interface DocumentSessionMediator{
    void broadcastChange(String change, User sender);
    void join(User user);
}

class CollaborativeDocument implements DocumentSessionMediator{
    private List<User> users = new ArrayList<>();

    @Override
    public void join(User user){
        users.add(user);
    }

    @Override
    public void broadcastChange(String change, User sender){
        for(User user: users){
            if(user != sender){
                user.receiveChange(change, sender);
            }
        }
    }
}

class User{
    protected String name;
    protected DocumentSessionMediator mediator;

    public User(String name, DocumentSessionMediator mediator){
        this.name = name;
        this.mediator = mediator;
    }

    public void makeChange(String change){
        System.out.println(name + " edited the document: " + change);
        mediator.broadcastChange(change, this);
    }

    public void receiveChange(String change, User sender){
        System.out.println(name + " saw change from " + sender.name + ": \"" + change + "\"");
    }
}


public class Main {
    public static void main(String[] args) {
//         User alice = new User("Alice");
//         User bob = new User("Bob");
//         User charlie = new User("Charlie");

//         // Adding collaborators (Alice gives access to Bob and Charlie)
//         alice.addCollaborator(bob);
//         alice.addCollaborator(charlie);

//         // Alice makes a change, notifying Bob and Charlie
//         alice.makeChange("Updated the document title");
// ˀ
//         // Bob makes a change, notifying Alice and Charlie
//         bob.makeChange("Added a new section to the document");

        CollaborativeDocument doc = new CollaborativeDocument();

        User alice = new User("Alice", doc);
        User bob = new User("bod", doc);
        User charlie = new User("Charlie", doc);

        doc.join(alice);
        doc.join(bob);
        doc.join(charlie);

        alice.makeChange("Added project title");
        bob.makeChange("Corrected grammer in paragraph 2");
    }
}
