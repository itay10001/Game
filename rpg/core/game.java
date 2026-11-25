package rpg.core;

public class game {
    public boolean isGameActive = true;
    private boolean isPlayerAlivequestionmark = true;

    public boolean gamestatus() {
        isGameActive = isGameActive && isPlayerAlivequestionmark;
        return isGameActive;
    }
}