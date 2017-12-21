package com.gradleandroidlibrary.peterarct.jokejavalibrary;


public class JokeSupplier {

    // Method that returns a random joke.
    // Jokes source: https://www.buzzfeed.com/mikespohr/75-dad-jokes-that-are-so-bad-theyre-actually-good?utm_term=.mb45bmYvp#.bjy2QqmpA
    public static String getRandomJoke(){

        int randomNum = getRandomInteger(0,10);

        switch (randomNum){
            case 1:
                return "What time did the man go to the dentist? Tooth hurt-y.";
            case 2:
                return "I am reading a book about anti-gravity. It is impossible to put down!";
            case 3:
                return "Did you know the first French fries were not actually cooked in France? They were cooked in Greece.";
            case 4:
                return "Want to hear a joke about a piece of paper? Never mind... it is tearable.";
            case 5:
                return "If you see a robbery at an Apple Store does that make you an iWitness?";
            case 6:
                return "You know what the loudest pet you can get is? A trumpet.";
            case 7:
                return "When a dad drives past a graveyard: Did you know that's a popular cemetery? Yep, people are just dying to get in there!";
            case 8:
                return "5/4 of people admit that they’re bad with fractions.";
            case 9:
                return "What is the best part about living in Switzerland? I do not know, but the flag is a big plus.";
            default:
                return "How many tickles does it take to make an octopus laugh? Ten-tickles.";
        }
    }

    // Source: http://www.java67.com/2015/01/how-to-get-random-number-between-0-and-1-java.html
    private static int getRandomInteger(int maximum, int minimum){ return ((int) (Math.random()*(maximum - minimum))) + minimum; }

}
