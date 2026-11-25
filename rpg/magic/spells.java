package rpg.magic;
import java.util.Random;

public class spells {

    public String name;
    public String element;
    public int damage;
    public int manaCost;
    public int cooldown;
    public double accuracy;

    public static spells createSpell(
        String name,
        String element,
        int damage,
        int manaCost,
        int cooldown,
        double accuracy
    ) {
        spells spell = new spells();

        spell.name = name;
        spell.element = element;
        spell.damage = damage;
        spell.manaCost = manaCost;
        spell.cooldown = cooldown;
        spell.accuracy = accuracy;

        return spell;
    }


    
}