package server.src.managers;

import server.src.builders.Person;
import server.src.enums.Color;
import server.src.enums.Difficulty;

public class Validator {
    public static boolean validDateForLabWork(java.util.Date date) {
        return true;
    }
    public static boolean validNameForLabWork(String name) {
        return true;
    }
    public static boolean validCoordinatesForLabWork(Coordinates coordinates) {
        return true;
    }
    public static boolean validMinimalPointForLabWork(int minimalPoint) {
        return true;
    }
    public static boolean validPersonalQualitiesMinimumForLabWork(int personalQualitiesMinimum) {
        return true;
    }
    public static boolean validDescriptionForLabWork(String description) {
        return true;
    }
    public static boolean validDifficultyForLabWork(Difficulty difficulty) {
        return true;
    }
    public static boolean validPerson(Person author) {
        return true;
    }
    
    public static boolean validNameForPerson(String name) {
        return true;
    }
    public static boolean validHeightForPerson(double height) {
        return true;
    }
    public static boolean validWeightForPerson(long weight) {
        return true;
    }
    public static boolean validPassportIDForPerson(String passportID) {
        return true;
    }
    public static boolean validHairColorForPerson(Color hairCol) {
        return true;
    }
    
}
