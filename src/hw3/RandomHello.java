package hw3;

import java.util.Random;

/**
 * RandomHello selects a random greeting to display to the user.
 */
public class RandomHello {
    
    /**
     * Uses a RandomHello object to print
     * a random greeting to the console.
     */
    public static void main(String[] argv) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }
    
    /**
     * @return a random greeting from a list of five different greetings.
     */
    public String getGreeting() {
        Random randomGenerator = new Random();
        String[] greetings = {"Hello world", "Hola Mundo", "Bonjour Monde",
                "Hallo Welt", "Ciao Mundo"};
        
        int num = randomGenerator.nextInt(greetings.length);
        
        return greetings[num];
    }
}
