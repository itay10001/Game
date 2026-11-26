
package rpg.character.classes;

import java.util.Random;
import static rpg.util.HelperFuncs.*;

public class classes {

    private static final Random mageTypeR = new Random();
    private static final Random combatTypeR = new Random();

    public static String classType;
    public static String mageTypeString = "mage not picked";

    public static boolean manualPickCombatType;
    public static boolean manualPickMageType;
    public static boolean manualclassPick;
    public static boolean manualCharacterCreation;
    public static boolean isMagePicked = false;

    public static final String[] mageTypes = {
        "fire mage",
        "water mage",
        "earth mage",
        "air mage",
        "general mage",
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
        return mageTypes[rollMageTypeInt(0, mageTypes.length - 2)];
    }

    public static String rollClass() {
        return classType = classes[rollClassInt(0, classes.length - 1)];
    }

    public static String setRandomCombatType(String combatTypeString) {
        return classType = rollClass();
    }

    public static String setRandomMageType(String currentMageType) {
        return mageTypeString = rollMageType();
    }

    public static String setManualClassType(String classString) {

        int manualClassType = askInt(
            "Pick your class: (pick a number 1-" + classes.length + ")"
        );

        if (manualClassType < 1 || manualClassType > classes.length) {
            SOPL("Error. Invalid class type entered. Pick again.");
            return setManualClassType(classString);
        }

        return classType = classes[manualClassType - 1];
    }

    public static String setManualMageType(String currentMageType) {

        int manualMageType = askInt(
            "Pick your mage type: (pick a number 1-" 
            + (mageTypes.length - 1) + ")"
        );

        if (manualMageType < 1 || manualMageType > mageTypes.length - 1) {
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

    public static String selectClass() {

        isMagePicked = false;
        manualPickMageType = false;
        mageTypeString = mageTypes[mageTypes.length - 1];

        manualCharacterCreation = askYesNo(
            "Do you want to manually create your character? (y/n)"
        );

        manualPickCombatType = manualCharacterCreation
            && askYesNo(
                "Do you want to manually pick your class? (y/n)"
            );

        manualclassPick = manualPickCombatType;

        if (manualPickCombatType) {

            for (int i = 0; i < classes.length; i++) {
                SOPL((i + 1) + ". " + classes[i]);
            }

            classType = setManualClassType(classType);

        } else {
            classType = setRandomCombatType(classType);
        }

        isMagePicked = classType.equals("mage");

        if (isMagePicked) {

            manualPickMageType = manualCharacterCreation
                && askYesNo(
                    "Do you want to manually pick your mage type? (y/n)"
                );

            if (manualPickMageType) {

                for (int i = 0; i < mageTypes.length - 1; i++) {
                    SOPL((i + 1) + ". " + mageTypes[i]);
                }

                mageTypeString = setManualMageType(mageTypeString);

            } else {
                mageTypeString = setRandomMageType(mageTypeString);
            }

            return classType + " (" + mageTypeString + ")";
        }

        return classType;
    }
}
