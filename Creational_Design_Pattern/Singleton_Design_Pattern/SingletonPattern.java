package Creational_Design_Pattern.Singleton_Design_Pattern;

class EagerSingleton{
    private static final EagerSingleton instance = new EagerSingleton();   
    
    private EagerSingleton(){
        // Declare it private prevents creation of its object using the new keyword
        System.out.println("EagerSingleton instance created");
    }

    public static EagerSingleton getInstance(){
        return instance;
    }

}


class LazySingleton{
    private static LazySingleton instance;

    private LazySingleton(){
        System.out.println("Lazy Singleton instance created");
    }

    public static LazySingleton getInstance(){
        if(instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }
}


public class SingletonPattern {
    public static void main(String args[]){
        // EagerSingleton obj1 = EagerSingleton.getInstance();
        // EagerSingleton obj2 = EagerSingleton.getInstance();

        // System.out.println(obj1 == obj2);

        LazySingleton obj1 = LazySingleton.getInstance();
        LazySingleton obj2 = LazySingleton.getInstance();

        System.out.println(obj1 == obj2);
    }
}
