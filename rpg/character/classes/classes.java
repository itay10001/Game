package rpg.character.classes;

import java.util.Random;
import static rpg.util.HelperFuncs.*;

public class classes {

    private static final Random mageTypeR = new Random();
    private static final Random combatTypeR = new Random();

    public String classString;
    public static String classType;
    public static String mageTypeString = "mage not picked";

    public static boolean manualPickCombatType;
    public static boolean manualPickMageType;
    public static boolean manualclassPick;
    public static boolean manualCharacterCreation;
    public static boolean isClassPick;

    public static final String[] mageTypes = {
        "fire mage",
        "water mage",
        "earth mage",
        "air mage",
        "mage not picked"
    };

    public static final String[] classes = {
        "balanced",
        "tank",
        "barbaric",
        "archer",
        "mage"
    };

    private static int rollMageTypeInt(int min, int max) {
        return mageTypeR.nextInt(max - min + 1) + min;
    }

    private static int rollClassInt(int min, int max) {
        return combatTypeR.nextInt(max - min + 1) + min;
    }

    public static String rollMageType() {
        return mageTypes[rollMageTypeInt(0, 3)];
    }

    public static String rollClass() {
        return classType = classes[rollClassInt(0, 4)];
    }

    public static String setRandomCombatType(String combatTypeString) {
        return classType = rollClass();
    }

    public static String setRandomMageType(String currentMageType) {
        return mageTypeString = rollMageType();
    }

    public static String setManualClassType(String classString) {

        int manualClassType = askInt(
            "Pick your class: (pick a number 1-5)"
        );

        if (manualClassType < 1 || manualClassType > 5) {
            SOPL("Error. Invalid class type entered. Pick again.");

            return setManualClassType(classString);
        }

        return classType = classes[manualClassType - 1];
    }

    public static String setManualMageType(String currentMageType) {

        int manualMageType = askInt(
            "Pick your mage type: (pick a number 1-4)"
        );

        if (manualMageType < 1 || manualMageType > 4) {
            SOPL("Error. Invalid mage type entered. Pick again.");

            return setManualMageType(currentMageType);
        }

        return mageTypeString = mageTypes[manualMageType - 1];
    }

    public static boolean askYesNo(String question) {

        String answer = ask(question);

        if (answer.equalsIgnoreCase("y")) {
            return true;
        }

        if (answer.equalsIgnoreCase("n")) {
            return false;
        }

        SOPL("Error. Please enter y or n.");

        return askYesNo(question);
    }

    public static void main(String[] args) {
        isClassPick = true;
        if(isClassPick) {
            manualCharacterCreation = askYesNo(
                "Do you want to manually create your character? (y/n)"
            );
            manualPickCombatType = manualCharacterCreation
                && askYesNo(
                    "Do you want to manually pick your class? (y/n)"
                );
            manualclassPick = manualPickCombatType;
            if (manualPickCombatType) {
                SOPL("1. balanced");
                SOPL("2. tank");
                SOPL("3. barbaric");
                SOPL("4. archer");
                SOPL("5. mage");
                classType = setManualClassType(classType);
            } else {
                classType = setRandomCombatType(classType);
            }
            manualPickMageType = false;
            mageTypeString = mageTypes[4];
            if (classType.equals(classes[4])) {
                manualPickMageType = manualCharacterCreation
                    && askYesNo(
                        "Do you want to manually pick your mage type? (y/n)"
                    );
                if (manualPickMageType) {
                    SOPL("1. fire mage");
                    SOPL("2. water mage");
                    SOPL("3. earth mage");
                    SOPL("4. air mage");
                    mageTypeString = setManualMageType(mageTypeString);
                } else {
                    mageTypeString = setRandomMageType(mageTypeString);
                }
            }
            SOPL("Character creation complete!");
            SOPL("Class: " + classType);

            if (classType.equals(classes[4])) {
                SOPL("Mage type: " + mageTypeString);
            }
        }
    }
}