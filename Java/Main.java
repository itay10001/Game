public class Main {
    public static void main(String[] args) {
        System.out.println("Starting RPG test...");

        game gameInstance = new game();
        System.out.println("Game active: " + gameInstance.gamestatus());

        player p = new player();
        System.out.println("Player HP: " + p.health);

        enemy e = new enemy();
        System.out.println("Enemy HP: " + e.health);
    }
}
