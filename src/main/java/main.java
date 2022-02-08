
import java.util.Scanner;

/**
 * @Author: py
 */
public class main {

    public static void main(String[] args) {
        Game game = new Game(5, 5);
        game.prepareAndPlayGames(new Scanner(System.in));
    }
}
