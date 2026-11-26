
package rpg.character;

import rpg.character.classes.classes;

public class classesStats {

    private final String[] classList = classes.classes;
    private final String[] mageElementList = classes.mageTypes;

    private String classType;
    private String elementType;

    private int health;
    private int mana;

    public String getsClass(int indexNumber) {
        return classList[indexNumber];
    }

    public String getsMageType(int indexNumber) {
        return mageElementList[indexNumber];
    }

    public void setStats() {

        classType = classes.classType;
        elementType = classes.mageTypeString;

        if (classType == null) {
            throw new IllegalStateException(
                "Character class has not been selected."
            );
        }

        if (classType.equals(getsClass(0))) {
            // Balanced
            health = 100;
            mana = 100;

        } else if (classType.equals(getsClass(1))) {
            // Tank
            health = 160;
            mana = 40;

        } else if (classType.equals(getsClass(2))) {
            // Barbaric
            health = 130;
            mana = 70;

        } else if (classType.equals(getsClass(3))) {
            // Archer
            health = 90;
            mana = 110;

        } else if (classType.equals(getsClass(4))) {

            if (elementType.equals(getsMageType(0))) {
                // Fire Mage
                health = 85;
                mana = 135;

            } else if (elementType.equals(getsMageType(1))) {
                // Water Mage
                health = 90;
                mana = 150;

            } else if (elementType.equals(getsMageType(2))) {
                // Earth Mage
                health = 115;
                mana = 110;

            } else if (elementType.equals(getsMageType(3))) {
                // Air Mage
                health = 75;
                mana = 145;

            } else if (elementType.equals(getsMageType(4))) {
                // General Mage
                health = 70;
                mana = 100;

            } else {
                throw new IllegalStateException(
                    "Invalid mage type."
                );
            }

        } else {
            throw new IllegalStateException(
                "Invalid character class."
            );
        }
    }

    // Compatibility with the original method
    public int setStats(int health, int mana) {
        setStats();
        return this.health;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public String getClassType() {
        return classType;
    }

    public String getElementType() {
        return elementType;
    }
}
