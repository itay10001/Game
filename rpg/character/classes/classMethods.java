package rpg.character.classes;
import rpg.character.classes.classes;
import static rpg.util.HelperFuncs.*;

public class classMethods {
    public static int pickClass() {
    int manualClassType = askInt("Pick your class: (pick a number 1-5)");
    if (manualClassType < 1 || manualClassType > 5) {
        SOPL("Error. Invalid class type entered. Pick again.");
        return pickClass();
    }

    pickMageType();
    return manualClassType;
    }
    public static int pickMageType() {
    int manualMageType = askInt("Pick your mage type: (pick a number 1-4)");
    if(manualMageType < 1 || manualMageType > 4) {
        SOPL("Error. Invalid mage type entered. Pick again.");
        return pickMageType();
    }
    return manualMageType;
    }
}
