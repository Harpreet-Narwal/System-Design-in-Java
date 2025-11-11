package Creational_Design_Pattern.BuilderPattern;
import java.util.*;
//Understanding the Problem

// Imagine you're building a BurgerMeal in your application. A burger must have some mandatory components like: Bun and Patty. And it can also include option components like: Sides, Toppings, and Cheese.

// Now let’s try to implement this using a traditional constructor approach:

class BurgerMeal1{
    private String bun;
    private String patty;

    private String sides;
    private List<String> toppings;
    private boolean cheese;
    
    public BurgerMeal1(String bun, String patty, String sides, List<String> toppings, boolean cheese){
        this.bun = bun;
        this.patty = patty;
        this.sides = sides;
        this.toppings = toppings;
        this.cheese = cheese;
    }


}

//Issues in Code
/* 
 * Hard to Read and Maintain
 * Unnecessary null values
 * Risk of NullPointerException
 * Too Many Constructor Overloads
 * Tight Coupling Between Parameters and Construction
*/



// public class Main {
//     public static void main(String[] args) {
//         BurgerMeal burgerMeal = new BurgerMeal("Wheat", "Veg", null, null, false);
//     }
// }


//The Solution

// To solve the problems we saw earlier with constructors, we use the Builder Pattern. It separates object construction from its representation, allowing us to build step-by-step while keeping the object immutable and readable.


class BurgerMeal{
    //required components
    private final String bunType;
    private final String patty;

    //Optional components
    private final boolean hasCheese;
    private final List<String> toppings;
    private final String side;
    private final String drink;

    private BurgerMeal(BurgerBuilder builder){
        this.bunType = builder.bunType;
        this.patty = builder.patty;
        this.hasCheese = builder.hasCheese;
        this.toppings = builder.toppings;
        this.side = builder.side;
        this.drink = builder.drink;
    }

    public String toString(){
        return  "Burger Meal Details: \n" +
                "Bun Type    :" + bunType + "\n" + 
                "Patty Type  :" + patty + "\n" + 
                "Cheese      :" + hasCheese + "\n"+
                "Toppings    :" + toppings + "\n" + 
                "Side        :" + side + "\n" +
                "Drink       :" + drink + "\n";

    }

    public static class BurgerBuilder{
        private final String bunType;
        private final String patty;
        
        private boolean hasCheese;
        private List<String> toppings;
        private String side;
        private String drink;
    
        public BurgerBuilder(String bunType, String patty){
            this.bunType = bunType;
            this.patty = patty;
        }
    
        public BurgerBuilder withCheese(boolean hasCheese){
            this.hasCheese = hasCheese;
            return this;
        }
    
        public BurgerBuilder withToppings(List<String> toppings){
            this.toppings = toppings;
            return this;
        }
    
        public BurgerBuilder withSides(String side){
            this.side = side;
            return this;
        }
    
        public BurgerBuilder withDrink(String drink){
            this.drink = drink;
            return this;
        }
    
        public BurgerMeal build(){
            return new BurgerMeal(this);
        }
    
    
    }

}


class Main{
    public static void main(String[] args) {
        BurgerMeal plainBurger = new BurgerMeal.BurgerBuilder("Wheat", "veg").build();

        BurgerMeal burgerwithCheese = new BurgerMeal.BurgerBuilder("Wheat", "veg")
                                        .withCheese(true)
                                        .build();

        List<String> toppings = Arrays.asList("lettuce", "onion", "jalapeno");
        BurgerMeal loadedBurger = new BurgerMeal.BurgerBuilder("Multigrain", "chicken")
                                    .withCheese(true)
                                    .withToppings(toppings)
                                    .withSides("fries")
                                    .withDrink("Diet Coke")
                                    .build();
        System.out.println(plainBurger.toString());
        System.out.println(burgerwithCheese.toString());
        System.out.println(loadedBurger.toString());
    }
}