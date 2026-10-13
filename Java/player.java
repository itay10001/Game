import java.util.Random;

public class player {
    public int health;
    public double mana;
    public int stamina;

    private inventory inv = new inventory();
    private helperFuncs helper = new helperFuncs();
    private boolean isPlayerAlivequestionmark = true; 

    game game = new game();

    public boolean attacksuccessful = false;

    public player() {
        if (game.isGameActive == true) {
            health = 100;
            mana = 100.0;
            stamina = 100;
            isPlayerAlivequestionmark = true;

            if (health <= 0) {
                isPlayerAlivequestionmark = false;
            }
        }
    }

    public int player_doDamage() {
        final int Damage = new Random().nextInt(11);

        if (!inv.hasSword) {
            helper.SOPL("You don't have a sword to attack with!");
            attacksuccessful = false;
            return 0;
        }

        if (Damage == 0) {
            helper.SOPL("You missed your attack!");
            attacksuccessful = false;
            inv.useSword(attacksuccessful);
            return 0;
        }

        attacksuccessful = true;
        helper.SOPL("Damage done: " + Damage);
        inv.useSword(attacksuccessful);
        return Damage;
    }

    public boolean isAttackSuccessful() {
        return attacksuccessful;
    }
}
