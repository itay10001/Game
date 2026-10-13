import java.util.Random;
public class enemy {
    private int damage = new Random().nextInt(11);
    private helperFuncs helper = new helperFuncs();
    private inventory inv = new inventory();
    private player player = new player();
    public int health;
    public double mana;
    public int stamina;
    public int cooldown = 0;
    String[] enemyTypes = {"balanced", "tank", "mage", "assassin"};
    String type = enemyTypes[new Random().nextInt(enemyTypes.length)];

    private String enemy_stats() {
    if(type == "balanced"){
        health = 100;
        mana = 100.0;
        stamina = 100;
    }
    if(type == "tank"){
        health = 150;
        mana = 50.0;
        stamina = 100;
    }
    if(type == "mage"){
        health = 50;
        mana = 150.0;
        stamina = 100;
    }
    if(type == "assassin"){
        health = 75;
        mana = 100.0;
        stamina = 150;
    }
    helper.SOPL("Enemy type: " + type);
    return type;
    }

    public int typeAttackStats(){
        if(enemy_stats() == "balanced"){
            damage = new Random().nextInt(11);
            cooldown = 3;
        }
        if(enemy_stats() == "tank"){
            damage = new Random().nextInt(6);
            cooldown = 5;
        }
        if(enemy_stats() == "mage"){
            damage = new Random().nextInt(16);
            cooldown = 4;
        }
        if(enemy_stats() == "assassin"){
            damage = new Random().nextInt(9);
            cooldown = 1;
        }

        return damage;
    }





    public int enemy_takeDamage(){
        final int damage = player.player_doDamage();
        if (player.isAttackSuccessful()) {
            health -= damage;
        }
        return damage;
    }


}

