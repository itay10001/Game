
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

    public void Turns() {
        if (!startTurns()) {
            return;
        }

        while (isCombatActive) {
            helper.SOPL("Pick an action:");
            helper.SOPL("1. Attack");

            if (!input.hasNext()) {
                isCombatActive = false;
                break;
            }

            if (!input.hasNextInt()) {
                input.next();
                helper.SOPL("Invalid choice");
                continue;
            }

            int choice = input.nextInt();

            if (choice != 1) {
                helper.SOPL("Invalid choice");
                continue;
            }

            int damage = player.doDamage();
            enemy.enemy_takeDamage(damage);

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
