public class game {
    public boolean isGameActive = true;
    private boolean isPlayerAlivequestionmark = true;
    public boolean gamestatus(){
            if (isGameActive == true && isPlayerAlivequestionmark == true) {
                isGameActive = true;
            }
            return isGameActive;
        }
    }
