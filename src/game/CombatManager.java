package game;

import characters.Character;
import items.Potion;
import items.Potion.PotionType;

import java.util.Scanner;

public class CombatManager {
    private Scanner scanner;

    public CombatManager(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Gerencia um único combate entre o jogador e um inimigo.
     *
     * @param player O personagem do jogador.
     * @param enemy  O inimigo.
     * @return true se o jogador venceu, false se o jogador foi derrotado.
     */
    public boolean startCombat(Character player, Character enemy) {
        System.out.println("\n--- Batalha contra " + enemy.getName() + " começa! ---");
        int turn = 0;

        while (player.isAlive() && enemy.isAlive()) {
            turn++;

            System.out.println("\n --- Tuno: " + turn + " ---");

            System.out.println("Sua vida: " + player.getHealth() + "/" + player.getMaxHealth() + " | Inimigo: "
                    + enemy.getHealth() + "/" + enemy.getMaxHealth());

            System.out.println("1. Atacar | 2. Usar Poção");
            System.out.print("Sua escolha: ");
            String combatChoice = scanner.nextLine();

            if ("1".equals(combatChoice)) {
                player.attack(enemy);
            } else if ("2".equals(combatChoice)) {
                Potion healingPotion = player.getInventory().getItems().stream()
                        .filter(item -> item instanceof Potion && ((Potion) item).getType() == PotionType.HEALING)
                        .map(item -> (Potion) item)
                        .findFirst()
                        .orElse(null);

                if (healingPotion != null) {
                    player.getInventory().useItem(healingPotion);
                } else {
                    System.out.println("Nenhuma poção de cura disponível.");
                }
            } else {
                System.out.println("Ação inválida. Atacando por padrão.");
                player.attack(enemy);
            }

            if (enemy.isAlive()) {
                enemy.attack(player);
            }
            // Thread.sleep(1000);
        }

        System.out.println("\n--- Batalha Encerrada ---");
        if (player.isAlive()) {
            System.out.println(player.getName() + " venceu!");
            player.gainExperience(150);
            return true;
        } else {
            System.out.println(enemy.getName() + " venceu! Você foi derrotado.");
            return false;
        }
    }

}
