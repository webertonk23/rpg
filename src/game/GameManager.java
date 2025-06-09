package game;

import java.util.Scanner;

import characters.Archer;
import characters.Character;
import characters.Mage;
import characters.Warrior;
import enums.CharacterClass;
import items.Equipment.Slot;
import items.Potion.PotionType;
import items.Armor;
import items.Item;
import items.Potion;
import items.Weapon;

public class GameManager {
    private Scanner scanner;
    private Character player;
    private CombatManager combatManager;

    public GameManager(Scanner scanner) {
        this.scanner = scanner;
        this.combatManager = new CombatManager(scanner);
    }

    /**
     * Inicia o loop principal do jogo (exploração, combate, etc.).
     * 
     * @param player O personagem principal do jogo.
     */
    public void startGame(Character player) throws Exception {
        this.player = player;
        System.out.println("\n--- Jogo Iniciado com " + player.getName() + " ---");
        player.introduce();
        player.getInventory().displayInventory();
        player.getInventory().displayEquippedGear();

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
                0, 0, 0, 1, 0, 0, 0, 3, 0
            );
            player.getInventory().addItem(basicArmor);
            player.getInventory().equipItem(basicArmor);
        }

        if (player.getInventory().getItems().stream().noneMatch(item -> item instanceof Potion && ((Potion)item).getType() == PotionType.HEALING)) {
             player.getInventory().addItem(new Potion("Poção de Cura Pequena", "Restaura um pouco de vida.", 15, 1, PotionType.HEALING, 20));
        }

        player.getInventory().displayInventory();
        player.getInventory().displayEquippedGear();
        System.out.println("Stats atuais: Força: " + player.getStrength() + ", Vitalidade: " + player.getVitality() + ", Defesa Total: " + player.getTotalDamageReduction());

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
                case "1":
                    Character enemy = new Warrior("Orc Brute");
                    boolean playerWon = combatManager.startCombat(player, enemy);
                    if (!playerWon) {
                        playing = false;
                    }
                    break;

                case "2":
                    player.getInventory().displayInventory();
                    break;

                case "3":
                    player.getInventory().displayEquippedGear();
                    break;

                case "4":
                    System.out.println("\n--- Poções no Inventário ---");
                    player.getInventory().getItems().stream()
                        .filter(item -> item instanceof Potion && ((Potion)item).getType() == PotionType.HEALING)
                        .map(item -> (Potion)item)
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

                case "5":
                    try {
                        SaveLoadManager.saveGame(player);
                        System.out.println("Jogo salvo! Você pode continuar de onde parou na próxima vez.");
                    } catch (Exception e) {
                        System.err.println("Falha ao salvar o jogo: " + e.getMessage());
                    }
                    break;

                case "6":
                    System.out.println("Retornando ao menu principal...");
                    playing = false;
                    break;

                default:
                    System.out.println("Opção invalida");
                    break;
            }


            if (!player.isAlive() && playing) { 
                System.out.println("Seu personagem foi derrotado! Fim de jogo para este personagem.");
                playing = false; // Sai do loop de jogo
            }
        }
    }

    /**
     * Cria um novo personagem baseado na entrada do usuário.
     */
    public Character createNewCharacter() {
        System.out.println("Entre com o nome de seu personagem: ");
        String name = scanner.nextLine();

        System.out.println("Escolha sua classe: ");
        CharacterClass[] classes = CharacterClass.values();
        for (int i = 0; i < classes.length; i++) {
            System.out.println((i + 1) + " => " + classes[i]);
        }

        CharacterClass chosenClass = null;
        while (chosenClass == null) {
            System.out.print("Entre com o número da classe");
            String input = scanner.nextLine();
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < classes.length) {
                    chosenClass = classes[index];
                } else {
                    System.out.println("Classe inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Classe inválida");
            }
        }

        Character newPlayer = switch (chosenClass) {
            case WARRIOR -> new Warrior(name);
            case MAGE -> new Mage(name);
            case ARCHER -> new Archer(name);
        };
        newPlayer.introduce();
        return newPlayer;
    }

    /**
     * Carrega um personagem de um arquivo de save existente.
     */
    public Character loadExistingGame() {
        SaveLoadManager.listSaveFiles();
        System.out.print("Entre com o nome do perssonagem para carregar.");
        String nameToLoad = scanner.nextLine();
        return SaveLoadManager.loadGame(nameToLoad);
    }
}
