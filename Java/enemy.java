
import java.util.Random;

public class enemy {
    private final Random random = new Random();
    private final helperFuncs helper = new helperFuncs();

    public int health;
    public double mana;
    public int stamina;
    public int cooldown = 0;

    private final String[] enemyTypes = {
        "balanced", "tank", "mage", "assassin"
    };

    private String type;

    private void enemy_stats() {
        if (type.equals("balanced")) {
            health = 100;
            mana = 100.0;
            stamina = 100;
            cooldown = 3;
        } else if (type.equals("tank")) {
            health = 150;
            mana = 50.0;
            stamina = 100;
            cooldown = 5;
        } else if (type.equals("mage")) {
            health = 50;
            mana = 150.0;
            stamina = 100;
            cooldown = 4;
        } else if (type.equals("assassin")) {
            health = 75;
            mana = 100.0;
            stamina = 150;
            cooldown = 1;
        }

        helper.SOPL("Enemy type: " + type);
    }

    public int typeAttackStats() {
        if (type.equals("balanced")) {
            return random.nextInt(11);
        } else if (type.equals("tank")) {
            return random.nextInt(6);
        } else if (type.equals("mage")) {
            return random.nextInt(16);
        } else if (type.equals("assassin")) {
            return random.nextInt(9);
        }

        return 0;
    }

    public int enemy_takeDamage(int damage) {
        health -= Math.max(0, damage);

        if (health < 0) {
            health = 0;
        }

        helper.SOPL("Enemy HP: " + health);
        return damage;
    }

    public int enemyAttacks(player target) {
        int damage = typeAttackStats();

        target.TakesDamage(damage);

        helper.SOPL(
            "Enemy dealt " + damage +
            " damage. Player HP: " + target.health
        );

        return damage;
    }

    public void newEnemy() {
        type = enemyTypes[random.nextInt(enemyTypes.length)];
        enemy_stats();
    }
}
