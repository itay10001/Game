package rpg.character;
import java.util.Random;
import static rpg.util.HelperFuncs.*;

public class classes {
    private static Random mageTypeR = new Random();
    private static Random combatTypeR = new Random();
    public String classString;
    public static String classType;
    public static String mageTypeString;
    public static boolean manualPickCombatType;
    public static boolean manualPickMageType;
    public static boolean manualclassPick;
    public static boolean manualCharacterCreation;
    

    
    
    
    private static int rollMageTypeInt(int min, int max) {
        return mageTypeR.nextInt(max - min + 1) + min;
    }
    private static int rollClassInt(int min, int max) {
        return combatTypeR.nextInt(max - min + 1) + min;
    }



    public static final String[] mageTypes ={
        "fire mage", "water mage", "earth mage", "air mage", "mage not picked"
    };




    public static final String[] classes ={
        "balanced", "tank", "barbaric", "archer", "mage"
    };



    public static String rollMageType(){
        int mageInt = rollMageTypeInt(1, 4);

        if(mageInt == 1){
            return mageTypes[0];
        }else if(mageInt == 2){
            return mageTypes[1];
        }else if(mageInt == 3){
            return mageTypes[2];
        }else if(mageInt == 4){
            return mageTypes[3];
        }else{
            return mageTypes[4];
        }
        
    }


    public static String rollClass(){
        int classInt = rollClassInt(1, 5);

        if(classInt == 1){
            return classes[0];
        }else if(classInt == 2){
            return classes[1];
        }else if(classInt == 3){
            return classes[2];
        }else if(classInt == 4){
            return classes[3];
        }else{
            return classes[4];
        }
        
    }

    public static String setRandomCombatType(String combatTypeString){
        return combatTypeString = rollClass();
    }
    public static String setRandomMageType(String mageTypeString){
        return mageTypeString = rollMageType();
    }
    public static String setManualClassType(String classString){
        SOPL("1. balanced");
        SOPL("2. tank");
        SOPL("3. barbaric");
        SOPL("4. archer");
        SOPL("5. mage");
        final int manualClassType = askInt("Pick your class: balanced, tank, barbaric, archer, mage, (pick a number 1-5)");
        if(manualClassType < 1 || manualClassType > 5){
            SOPL("Error. Invalid class type entered. pick again.");
            return classes[0];
        }else{
            if(manualClassType == 1){
                return classes[0];
            }else if(manualClassType == 2){
                return classes[1];
            }else if(manualClassType == 3){
                return classes[2];
            }else if(manualClassType == 4){
                return classes[3];
            }else{
                return classes[4];
            }
        }
    }
    


    public static void main(String[] args) {
        final String manualCreation = ask("Do you want to manually pick your class? (y/n)");
        if(manualCreation.equalsIgnoreCase("y")){
            manualCharacterCreation = true;
        }else{
            manualCharacterCreation = false;
        }
        if(manualCharacterCreation){
            final String manualCombatType = ask("Do you want to manually pick your combat type? (y/n)");
            if(manualCombatType.equalsIgnoreCase("y")){
                manualPickCombatType = true;
                final int manualClassType = askInt("Pick your class: (pick a number 1-5)");
                if(manualClassType < 1 || manualClassType > 5){
                    SOPL("Error. Invalid class type entered. pick again.");
                    setManualClassType(classes[0]);
                }else{
                    manualPickCombatType = false;
                }
                if(!manualPickCombatType){
                    classType = setRandomCombatType(classType);
                    if(classType.equals(classes[4])){
                        mageTypeString = setRandomMageType(mageTypeString);
                }
            }
        
        

            
            }
        }
    }
}
