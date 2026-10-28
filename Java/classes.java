import java.util.Random;
import java.util.Scanner;

public class classes {
    private Random mageTypeR = new Random();
    private Random combatTypeR = new Random();
    public boolean manualPickCombatType;
    public boolean manualPickMageType;

    
    
    
    private int rollMageTypeInt(int min, int max) {
        return mageTypeR.nextInt(max - min + 1) + min;
    }
    private int rollClassInt(int min, int max) {
        return combatTypeR.nextInt(max - min + 1) + min;
    }



    public final String[] mageTypes ={
        "fire mage", "water mage", "earth mage", "air mage", "mage not picked"
    };




    public final String[] classes ={
        "balanced", "tank", "barbaric", "archer", "mage"
    };



    public String rollMageType(){
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


    public String rollCombatType(){
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

    public String setRandomCombatType(String combatTypeString){
        return combatTypeString = rollCombatType();
    }
    public String setRandomMageType(String mageTypeString){
        return mageTypeString = rollMageType();
    }
    
}
