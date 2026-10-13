
import java.util.Random;

public class enemy {
    private final Random random = new Random();
    private final helperFuncs helper = new helperFuncs();

    private final String[] enemyTypes = {
        "buff", "quick", "balanced"
    };

    private String type;

    public int health;
    public double mana;
    public int stamina;
    public double speed;
    public double cooldown;

    private int damage;

    // Random integer, both endpoints included.
    private int rollInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    // Random decimal within a range.
    private double rollDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    // Assign stats once when the enemy spawns.
    private void enemy_stats() {
        switch (type) {
            case "buff":
                health = rollInt(150, 250);
                damage = rollInt(10, 20);
                speed = rollDouble(1.0, 3.0);
                cooldown = rollDouble(1.0, 3.0);
                mana = 50.0;
                stamina = 100;
                break;

            case "quick":
                health = rollInt(25, 75);
                damage = rollInt(5, 10);
                speed = rollDouble(5.0, 8.0);
                cooldown = rollDouble(0.25, 0.75);
                mana = 100.0;
                stamina = 150;
                break;

            case "balanced":
                health = rollInt(75, 150);
                damage = rollInt(10, 15);
                speed = rollDouble(3.0, 5.0);
                cooldown = rollDouble(0.75, 1.5);
                mana = 100.0;
                stamina = 100;
                break;

            case "dummy":
                health = 90 * 90 * 90 * 90;
                damage = 0;
                speed = 0.0;
                cooldown = Math.pow(90.0, 12);
                mana = 0.0;
                stamina = 0;
                break;

            default:
                throw new IllegalStateException(
                    "Unknown enemy type: " + type
                );
        }

        helper.SOPL(
            "Enemy type: " + type +
            " | HP: " + health +
            " | Damage: " + damage
        );
    }

    public int typeAttackStats() {
        return damage;
    }

    public int enemy_takeDamage(int incomingDamage) {
        health = Math.max(
            0, health - Math.max(0, incomingDamage)
        );

        helper.SOPL("Enemy HP: " + health);
        return incomingDamage;
    }

    public int enemyAttacks(player target) {
        int attackDamage = typeAttackStats();

        target.TakesDamage(attackDamage);

        helper.SOPL(
            "Enemy dealt " + attackDamage +
            " damage. Player HP: " + target.health
        );

        return attackDamage;
    }

    public void newEnemy() {
        type = enemyTypes[random.nextInt(enemyTypes.length)];
        enemy_stats();
    }

    // Optional test dummy, not randomly spawned.
    public void newDummy() {
        type = "dummy";
        enemy_stats();
    }

    public String getType() {
        return type;
    }
}
