
import java.util.Scanner;

public class combat {
    public boolean isCombatActive = false;

    private final helperFuncs helper = new helperFuncs();
    private final player player = new player();
    private final enemy enemy = new enemy();
    private final game game = new game();
    private final Scanner input = new Scanner(System.in);

    public boolean startCombat() {
        if (!game.isGameActive) {
            isCombatActive = false;
            return false;
        }

        enemy.newEnemy();
        isCombatActive = true;
        helper.SOPL("Combat has started!");
        return true;
    }

    public boolean LossConditionMet() {
        if (isCombatActive && !player.isAlive()) {
            helper.SOPL("You have been defeated!");
            isCombatActive = false;
            return true;
        }
        return false;
    }

    private boolean winConditionMet() {
        if (isCombatActive && enemy.health <= 0) {
            helper.SOPL("You defeated the enemy!");
            isCombatActive = false;
            return true;
        }
        return false;
    }

    public boolean startTurns() {
        if (startCombat()) {
            helper.SOPL("Combat is active, starting turns...");
            return true;
        }

        helper.SOPL("Combat is not active, cannot start turns.");
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
        helper.SOPL("Actions:");
        helper.SOPL("1. Use mana potion");
        helper.SOPL("2. Use health potion");
        helper.SOPL("3. Repair sword (coming soon)");
        helper.SOPL("4. Back");

        Integer action = readChoice();

        if (action == null) {
            isCombatActive = false;
            return false;
        }

        switch (action) {
            case 1:
                if (!player.hasManaPotion()) {
                    helper.SOPL("No mana potions left.");
                    return false;
                }

                double manaBefore = player.getMana();
                player.useMP();

                helper.SOPL("Current mana: " + player.getMana());
                return player.getMana() > manaBefore;

            case 2:
                if (!player.hasHp()) {
                    helper.SOPL("No health potions left.");
                    return false;
                }

                int hpBefore = player.getHP();
                player.useHP();

                helper.SOPL("Current HP: " + player.getHP());
                return player.getHP() > hpBefore;

            case 3:
                
                return false;

            case 4:

                return false;

            default:
                helper.SOPL("Invalid choice.");
                return false;
        }
    }

    public void Turns() {
        if (!startTurns()) {
            return;
        }

        while (isCombatActive) {
            helper.SOPL("Pick an action:");
            helper.SOPL("1. Attack");
            helper.SOPL("2. Actions");
            helper.SOPL("4. Reset HP (dev)");

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
                    helper.SOPL(
                        "HP reset. New HP: " + player.getHP()
                    );
                    turnUsed = false;
                    break;

                default:
                    helper.SOPL("Invalid choice.");
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