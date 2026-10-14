import java.util.Scanner;

public class combat {
    public boolean isCombatActive = false;

    private final helperFuncs helper = new helperFuncs();
    private final player player = new player();
    private final enemy enemy = new enemy();
    private final game game = new game();
    private final Scanner input = new Scanner(System.in);

    private String SOPL(String print) {
        return helper.SOPL(print);
    }

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
            
            SOPL("Pick an action:");
            SOPL("1. Attack");
            SOPL("2. Use mana potion");
            SOPL("3. Use Health potion");
            SOPL("4. Reset HP");

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
            if (choice == 1) {
                int damage = player.doDamage();
                enemy.enemy_takeDamage(damage);
            } else if (choice == 2) {
                if (!player.hasManaPotion()) {
                    helper.SOPL("No mana potions left");
                    continue;
                }
                double before = player.getMana();
                player.useMP();
                helper.SOPL("Current mana: " + player.getMana());
                if (player.getMana() == before) {
                    continue; // Potion wasn't consumed, so don't use a turn.
                }
            } else if (choice == 3) {
                if(!player.hasHp()){
                    SOPL("No hp potions left");
                    continue;
                }
                int before = player.getHP();
                player.useHP();
                SOPL("Current HP: " + player.getHP());
                    if(player.getHP() == before){
                        continue;
                    }


            }else if(choice == 4){
                player.resetHp();
                SOPL("Hp reset. New hp: " + player.getHP());


            }else {
                helper.SOPL("Invalid choice");
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
