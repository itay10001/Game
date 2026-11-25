package rpg.combat;

import java.util.Scanner;

import rpg.character.player;
import rpg.character.classes.classes;
import rpg.core.game;
import static rpg.util.HelperFuncs.*;

public class combat {
    public boolean isCombatActive = false;

    
    private final player player = new player();
    private final enemy enemy = new enemy();
    private final game game = new game();
    private final classes classes = new classes();
    private final Scanner input = new Scanner(System.in);
    public boolean isGameActive = game.isGameActive;

    public boolean startCombat() {
        if (!game.isGameActive) {
            isCombatActive = false;
            return false;
        }

        enemy.newEnemy();
        isCombatActive = true;
        SOPL("Combat has started!");
        return true;
    }

    public boolean LossConditionMet() {
        if (isCombatActive && !player.isAlive()) {
            SOPL("You have been defeated!");
            isCombatActive = false;
            return true;
        }
        return false;
    }

    private boolean winConditionMet() {
        if (isCombatActive && enemy.health <= 0) {
            SOPL("You defeated the enemy!");
            isCombatActive = false;
            return true;
        }
        return false;
    }

    public boolean startTurns() {
        if (startCombat()) {
            SOPL("Combat is active, starting turns...");
            return true;
        }

        SOPL("Combat is not active, cannot start turns.");
        return false;
    }

    // Returns null at end of input, -1 for invalid input.
    private Integer readChoice() {
        if (!input.hasNextLine()) {
            return null;
        }

        String text = input.nextLine().trim();

        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    

    // True means an action consumed a turn.
    private boolean handleActionsMenu() {
        SOPL("Actions:");
        SOPL("1. Use mana potion");
        SOPL("2. Use health potion");
        SOPL("3. Repair sword");
        SOPL("4. Back");

        Integer action = readChoice();

        if (action == null) {
            isCombatActive = false;
            return false;
        }
        

        switch (action) {
            case 1:
                if (!player.hasManaPotion()) {
                    SOPL("No mana potions left.");
                    return false;
                }

                double manaBefore = player.getMana();
                player.useMP();

                SOPL("Current mana: " + player.getMana());
                return player.getMana() > manaBefore;

            case 2:
                if (!player.hasHp()) {
                    SOPL("No health potions left.");
                    return false;
                }

                int hpBefore = player.getHP();
                player.useHP();

                SOPL("Current HP: " + player.getHP());
                return player.getHP() > hpBefore;

            case 3:
                if(!player.hasSword()) {
                    SOPL("No sword to repair.");
                    return false;
                }else if(player.getSwordDurability() >= 100) {
                    SOPL("Sword is already at full durability.");
                    return false;
                }else{
                    player.repairSword();
                    SOPL("Sword repaired.");
                    return true;
                }

            case 4:

                return false;

            default:
                SOPL("Invalid choice.");
                return false;
        }
    }

    public void Turns() {
        if (!startTurns()) {
            return;
        }

        while (isCombatActive) {
            SOPL("Pick an action:");
            SOPL("1. Attack");
            SOPL("2. Actions");
            SOPL("4. Reset HP (dev)");

            Integer choice = readChoice();

            if (choice == null) {
                isCombatActive = false;
                break;
            }

            boolean turnUsed;

            switch (choice) {
                case 1:
                    int damage = player.doDamage();
                    enemy.enemy_takeDamage(damage);
                    turnUsed = true;
                    break;

                case 2:
                    turnUsed = handleActionsMenu();
                    break;

                case 4:
                    player.resetHp();
                    SOPL(
                        "HP reset. New HP: " + player.getHP()
                    );
                    turnUsed = false;
                    break;

                default:
                    SOPL("Invalid choice.");
                    turnUsed = false;
                    break;
            }

            if (!turnUsed) {
                continue;
            }

            if (winConditionMet()) {
                break;
            }

            enemy.enemyAttacks(player);

            if (LossConditionMet()) {
                break;
            }
        }
    }
}