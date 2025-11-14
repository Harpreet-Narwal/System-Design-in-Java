package Behavioural_Design_Patter.CommanPattern;
import java.util.*;

/*
 * Understanding the Problem

Let's say we're building a simple remote control system where devices like lights and air conditioner can be turned on and off. Here's a naive implementation of the code:
 */


class Light{
    public void on(){
        System.out.println("Light turned ON");
    }
    public void off(){
        System.out.println("Light turned OFF");
    }
}

class AC{
    public void on(){
        System.out.println("AC Turned ON");
    }
    public void off(){
        System.out.println("AC turned OFF");
    }
}

// Invoker -> NaiveRemoteControl class to control devices
class NaiveRemoteControl{
    private Light light;
    private AC ac;
    private String lastAction = "";

    public NaiveRemoteControl(Light light, AC ac){
        this.light = light;
        this.ac = ac;
    }

    public void pressLightOn(){
        light.on();
        lastAction = "LIGHT_ON";
    }

    public void pressLightOff(){
        light.off();
        lastAction = "LIGHT_OFF";
    }

    public void pressACOn(){
        ac.on();
        lastAction = "AC_ON";
    }

    public void pressACOff(){
        ac.off();
        lastAction = "AC_OFF";
    }

    public void pressUndo(){
        switch (lastAction) {
            case "LIGHT_ON": light.off(); lastAction = "LIGHT_OFF"; break;
            case "LIGHT_OFF": light.on(); lastAction = "LIGHT_ON" ; break;
            case "AC_ON" : ac.off(); lastAction = "AC_OFF"; break;
            case "AC_OFF": ac.on(); lastAction = "AC_ON"; break;
            default:
                System.out.println("No action to undo."); break;
        }
    }
}



// public class Main {

//     public static void main(String[] args) {
//         Light light = new Light();
//         AC ac = new AC();
//         NaiveRemoteControl remote = new NaiveRemoteControl(light, ac);

//         remote.pressLightOn();
//         remote.pressACOn();
//         remote.pressLightOff();
//         remote.pressUndo(); // Should undo LIGHT_OFF -> Light ON
//         remote.pressUndo(); // Should undo AC_ON -> AC OFF
//     }
    
// }
/*
 * Issues in the code:
 * Tight coupling
 * Lack of Flexibility
 * Undo Functionality
 * Hardcoded Commands
 * Maintaining Command History
 */

 /*
  * The SOLUTION
  he issues in the previous implementation can be addressed by using the Command Pattern. By applying this pattern, it becomes easier to encapsulate requests as objects, allowing for flexible and reusable command handling. The command pattern decouples the request sender (Invoker) from the receiver (Light/AC) and provides a unified way to handle multiple commands and actions.
  */


// ========= Command interface ===========
//    defines the command structure

interface Command{
    void execute();
    void undo();
}

class LightOnCommand implements Command{
    private Light light;

    public LightOnCommand(Light light){
        this.light = light;
    }

    public void execute(){
        light.on();
    }

    public void undo(){
        light.off();
    }
}


class LightOffCommand implements Command{

    private Light light;

    public LightOffCommand(Light light){
        this.light = light;
    }

    public void execute(){
        light.off();
    }
    
    public void undo(){
        light.on();
    }
}


class AConCommand implements Command{
    private AC ac;

    public AConCommand(AC ac) {
        this.ac = ac;
    }

    public void execute() {
        ac.on();
    }

    public void undo() {
        ac.off();
    }
}

class ACOffCommand implements Command {
    private AC ac;

    public ACOffCommand(AC ac) {
        this.ac = ac;
    }

    public void execute() {
        ac.off();
    }

    public void undo() {
        ac.on();
    }
}

// ========== Remote control class (Invoker) ==========
class RemoteControl{
    private Command[] buttons = new Command[4];
    private Stack<Command> commandHistory = new Stack<>();

    public void setCommand(int slot, Command command){
        buttons[slot] = command;
    }

    public void pressButton(int slot){
        if(buttons[slot] != null){
            buttons[slot].execute();
            commandHistory.push(buttons[slot]);
        }else{
            System.out.println("No command assigned to slot" + slot);
        }
    }

    public void pressUndo(){
        if(!commandHistory.isEmpty()){
            commandHistory.pop().undo();
        }else{
            System.out.println("No commands to undo");
        }
    }

}

class Main{
    public static void main(String[] args) {
        Light light = new Light();
        AC ac = new AC();

        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);

        Command acOn = new AConCommand(ac);
        Command acOff = new ACOffCommand(ac);

        RemoteControl remote = new RemoteControl();
        remote.setCommand(0, lightOn);
        remote.setCommand(1, lightOff);
        remote.setCommand(2, acOn);
        remote.setCommand(3, acOff);

        remote.pressButton(0);
        remote.pressButton(2);
        remote.pressButton(1);
        remote.pressUndo();
        remote.pressUndo();
    }
}