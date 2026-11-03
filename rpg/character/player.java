package rpg.character;
import java.util.Random;

import rpg.items.inventory;
import static rpg.util.HelperFuncs.*;

public class player {
    public int health;
    public int mana;
    public int stamina;

    private final inventory inv = new inventory();
    private final Random random = new Random();
    public  String mageType;
    
    public boolean attacksuccessful = false;

    public player() {
        
    }

    public int doDamage() {
        int hitRoll = random.nextInt(6);
        int damage = random.nextInt(5, 36);

        if (!inv.hasSword) {
            SOPL("You don't have a sword to attack with!");
            attacksuccessful = false;
            return 0;
        }

        if (hitRoll == 0) {
            SOPL("You missed your attack!");
            attacksuccessful = false;
            inv.useSword(attacksuccessful);
            return 0;
        }

        attacksuccessful = true;
            SOPL("Damage done: " + damage);
        inv.useSword(attacksuccessful);
        return damage;
    }


    public boolean hasSword() {
        return inv.hasSword;
    }

    public int getSwordDurability() {
        return inv.swordDurability;
    }

    public int repairSword() {
        return inv.RepairSword(2);
    }

    public boolean isAttackSuccessful() {
        return attacksuccessful;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int TakesDamage(int damage) {
        health -= Math.max(0, damage);
        if (health < 0) {
            health = 0;
        }
        return health;
    }

    public boolean hasManaPotion() {
        return inv.hasMPquestion();
    }

    public boolean hasHp(){
        return inv.hasHPquestion();
    }

    public int useMP() {
        mana = inv.useManaPotion(mana);
        return mana;
    }

    public int useHP(){
        health = inv.useHealthPotion(health);
        return health;
    }

    public double getMana() {
        return mana;
    }
    public int getHP(){
        return health;
    }

    public double resetMana() {
        mana = 0;
        return mana;
    }

    public double resetHp(){
        health = 15;
        return health;
    }  
}