import characters.Character;
import game.GameManager;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        GameManager gameManager = new GameManager(scanner);

        boolean running = true;

        while (running) {
            System.out.println("\n--- RPG Textual ---");
            System.out.println("1. Novo jogo");
            System.out.println("2. Carregar jogo");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção:");

            String choice = scanner.nextLine();
            Character player = null;

            switch (choice) {
                case "1":
                    player = gameManager.createNewCharacter();
                    if (player != null) {
                        gameManager.startGame(player);
                    }
                    break;
                case "2":
                    player = gameManager.loadExistingGame();
                    if (player != null) {
                        gameManager.startGame(player);
                    }
                    break;
                case "3":
                    running = false;
                    System.out.println("Adeus");
                    break;
                default:
                    System.out.println("Opção invalida");
            }
        }

        scanner.close();
    }
}
