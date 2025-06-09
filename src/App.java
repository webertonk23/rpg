import java.util.Scanner;

import characters.*;
import characters.Character;
import enums.CharacterClass;
import game.SaveLoadManager;
import items.Armor;
import items.Weapon;
import items.Equipment.Slot;
import items.Item;
import items.Potion;
import items.Potion.PotionType;
import util.I18n;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        Character player = null;
        Boolean running = true;

        while (running) {
            System.out.println("\n--- RPG Textual ---");
            System.out.println("1. " + I18n.t("new_game")); // Novo jogo
            System.out.println("2. " + I18n.t("load_game")); // Carregar jogo
            System.out.println("3. " + I18n.t("exit_game")); // Sair do jogo
            System.out.print(I18n.t("choose_option"));

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    player = createNewCharacter(scanner);
                    if(player != null) {
                        startGame(scanner, player);
                        running = false;
                    }
                    break;
                case "2":
                    player = loadExistingGame(scanner);
                    if (player != null) {
                        startGame(scanner, player);
                        running = false;
                    }
                    break;
                case "3":
                    running = false;
                    System.out.println(I18n.t("goodbye"));
                    break;
                default:
                    System.out.println(I18n.t("invalid_option"));
            }
        }

        scanner.close();
    }

    private static Character createNewCharacter(Scanner scanner) {
        System.out.println(I18n.t("enter_name"));
        String name = scanner.nextLine();

        System.out.println(I18n.t("choose_class"));

        CharacterClass[] classes = CharacterClass.values();
        for (int i = 0; i < classes.length; i++) {
            System.out.println((i + 1) + " => " + classes[i]);
        }

        CharacterClass chosenClass = null;

        while (chosenClass == null)

        {
            System.out.println("Digite o número da classe escolhida: ");
            String input = scanner.nextLine();

            try {
                int index = Integer.parseInt(input) - 1;

                if (index >= 0 && index < classes.length) {
                    chosenClass = classes[index];
                } else {
                    System.out.println("Classe inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Classe inválida. Tente novamente.");
            }
        }

        Character player = switch (chosenClass) {
            case WARRIOR -> player = new Warrior(name);
            case MAGE -> player = new Mage(name);
            case ARCHER -> player = new Archer(name);
        };

        player.introduce();
        return player;
    }

    private static Character loadExistingGame(Scanner scanner) {
        SaveLoadManager.listSaveFiles();
        System.out.print(I18n.t("enter_character_name_to_load"));
        String nameToLoad = scanner.nextLine();
        return SaveLoadManager.loadGame(nameToLoad);
    }

    private static void startGame(Scanner scanner, Character player) throws Exception {
        System.out.println("\n--- Jogo Iniciado com " + player.getName() + " ---");
        player.introduce();
        player.getInventory().displayInventory();
        player.getInventory().displayEquippedGear();

        // Exemplo: Equipando itens básicos e adicionando poção para teste
        if (player.getInventory().getEquippedGear().get(Slot.WEAPON) == null) {
            System.out.println("\nEquipando itens iniciais para " + player.getName() + "...");
            Weapon basicSword = new Weapon(
                    "Espada Básica", "Uma espada de treinamento.", 10, 2, Slot.WEAPON,
                    2, 0, 0, 0, 0, 0, 0, 0, 7);
            player.getInventory().addItem(basicSword);
            player.getInventory().equipItem(basicSword);
        }

        if (player.getInventory().getEquippedGear().get(Slot.CHEST) == null) {
            Armor basicArmor = new Armor(
                    "Armadura Leve", "Oferece proteção básica.", 15, 3, Slot.CHEST,
                    0, 0, 0, 1, 0, 0, 0, 3, 0);
            player.getInventory().addItem(basicArmor);
            player.getInventory().equipItem(basicArmor);
        }

        // Adicionar uma poção se não tiver nenhuma
        if (player.getInventory().getItems().stream()
                .noneMatch(item -> item instanceof Potion && ((Potion) item).getType() == PotionType.HEALING)) {
            player.getInventory().addItem(
                    new Potion("Poção de Cura Pequena", "Restaura um pouco de vida.", 15, 1, PotionType.HEALING, 20));
        }

        player.getInventory().displayInventory();
        player.getInventory().displayEquippedGear();
        System.out.println("Stats atuais: Força: " + player.getStrength() + ", Vitalidade: " + player.getVitality()
                + ", Defesa Total: " + player.getTotalDamageReduction());

        // --- Loop Principal do Jogo ---
        boolean playing = true;
        while (playing) {
            System.out.println("\n--- Ações do Jogo ---");
            System.out.println("1. Encontrar um inimigo (Combate)");
            System.out.println("2. Ver Inventário");
            System.out.println("3. Ver Equipamentos");
            System.out.println("4. Usar Poção (se disponível)");
            System.out.println("5. Salvar Jogo");
            System.out.println("6. Sair para o Menu Principal");
            System.out.print("Escolha uma ação: ");
            String gameAction = scanner.nextLine();

            switch (gameAction) {
                case "1": // Combate
                    Character enemy = new Warrior("Orc Brute"); // Exemplo simples
                    System.out.println("\n--- Batalha contra " + enemy.getName() + " começa! ---");
                    int turn = 0;
                    while (player.isAlive() && enemy.isAlive()) {
                        turn++;
                        System.out.println("\n --- Turno: " + turn + " ---");

                        // Turno do jogador
                        System.out.println("Sua vida: " + player.getHealth() + "/" + player.getMaxHealth()
                                + " | Inimigo: " + enemy.getHealth() + "/" + enemy.getMaxHealth());
                        System.out.println("1. Atacar | 2. Usar Poção");
                        System.out.print("Sua escolha: ");
                        String combatChoice = scanner.nextLine();

                        if ("1".equals(combatChoice)) {
                            player.attack(enemy);
                        } else if ("2".equals(combatChoice)) {
                            // Tenta encontrar e usar a primeira poção de cura disponível
                            Potion healingPotion = player.getInventory().getItems().stream()
                                    .filter(item -> item instanceof Potion
                                            && ((Potion) item).getType() == PotionType.HEALING)
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
                        Thread.sleep(1000); // Pequena pausa para visualização
                    }
                    System.out.println("\n--- Batalha Encerrada ---");
                    if (player.isAlive()) {
                        System.out.println(player.getName() + " venceu!");
                        player.gainExperience(150); // Exemplo de XP
                    } else {
                        System.out.println(enemy.getName() + " venceu! Você foi derrotado.");
                        playing = false; // Fim do jogo se o jogador for derrotado
                    }
                    break;

                case "2": // Ver Inventário
                    player.getInventory().displayInventory();
                    break;

                case "3": // Ver Equipamentos
                    player.getInventory().displayEquippedGear();
                    break;

                case "4": // Usar Poção
                    // Lógica para listar poções e permitir escolha
                    System.out.println("\n--- Poções no Inventário ---");
                    // Filtrar e listar apenas poções de cura
                    player.getInventory().getItems().stream()
                            .filter(item -> item instanceof Potion && ((Potion) item).getType() == PotionType.HEALING)
                            .map(item -> (Potion) item)
                            .forEach(p -> System.out.println("- " + p.getName()));

                    System.out.print("Digite o nome da poção a usar (ou 'voltar'): ");
                    String potionName = scanner.nextLine();
                    if (!"voltar".equalsIgnoreCase(potionName)) {
                        Item itemToUse = player.getInventory().getItems().stream()
                                .filter(item -> item.getName().equalsIgnoreCase(potionName) && item instanceof Potion)
                                .findFirst()
                                .orElse(null);

                        if (itemToUse != null) {
                            player.getInventory().useItem(itemToUse);
                        } else {
                            System.out.println("Poção não encontrada ou não pode ser usada.");
                        }
                    }
                    break;

                case "5": // Salvar Jogo
                    try {
                        SaveLoadManager.saveGame(player);
                        System.out.println("Jogo salvo! Você pode continuar de onde parou na próxima vez.");
                    } catch (Exception e) {
                        System.err.println("Falha ao salvar o jogo: " + e.getMessage());
                    }
                    break;

                case "6": // Sair para o Menu Principal
                    System.out.println("Retornando ao menu principal...");
                    playing = false;
                    break;

                default:
                    System.out.println(I18n.t("invalid_option"));
                    break;
            }

            if (!player.isAlive() && playing) { // Se o jogador morrer durante o jogo
                System.out.println("Seu personagem foi derrotado! Fim de jogo.");
                playing = false;
            }
        }
    }
}
