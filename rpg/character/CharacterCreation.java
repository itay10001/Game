
package rpg.character;

import static rpg.util.HelperFuncs.*;
import rpg.character.classes.classes;

public class CharacterCreation {

    private String characterName;
    private String characterClass;

    private static CharacterCreation currentCharacter;

    private String selectName() {
        characterName = ask("Enter your character's name: ");
        return characterName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public static CharacterCreation getCurrentCharacter() {
        return currentCharacter;
    }

    public static CharacterCreation createCharacter() {

        currentCharacter = new CharacterCreation();

        currentCharacter.selectName();
        currentCharacter.characterClass = classes.selectClass();

        SOPL("Character creation complete!");
        SOPL("Name: " + currentCharacter.characterName);
        SOPL("Class: " + currentCharacter.characterClass);

        return currentCharacter;
    }

    public static void main(String[] args) {
        createCharacter();
    }
}
