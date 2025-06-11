package game;

import characters.Character;

import java.io.*;

public class SaveLoadManager {
    private static String SAVE_DIR = "saves";

    private static void ensureSaveDirectoryExists() {
        File directory = new File(SAVE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Salva um objeto Character em um arquivo.
     * O nome do arquivo será baseado no nome do personagem.
     *
     * @param player O objeto Character a ser salvo.
     * @return true se o salvamento foi bem-sucedido, false caso contrário.
     */
    public static boolean saveGame(Character player) {
        ensureSaveDirectoryExists();
        String fileName = fileName(player.getName());

        try (
                FileOutputStream fileOut = new FileOutputStream(fileName);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(player);
            return true;

        } catch (IOException e) {
            System.err.println("Erro ao salvar o jogo " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carrega um objeto Character de um arquivo.
     *
     * @param playerName O nome do personagem a ser carregado (usado para encontrar
     *                   o arquivo).
     * @return O objeto Character carregado, ou null se o carregamento falhar.
     */
    public static Character loadGame(String playerName) {
        String fileName = fileName(playerName);

        File savedFile = new File(fileName);

        if (!savedFile.exists()) {
            System.out.println("Nenhum save encontrado para '" + playerName + "'.");
            return null;
        }

        try (
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            Character loadedPlayer = (Character) objIn.readObject();

            return loadedPlayer;

        } catch (IOException e) {
            System.err.println("Erro de I/O ao carregar o jogo: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Classe não encontrada ao carregar o jogo: " + e.getMessage());
            System.err.println("Isso pode acontecer se a estrutura das classes mudou desde o último save.");
            e.printStackTrace();
            return null;
        }
    }

    public static void listSaveFiles() {
        File directory = new File(SAVE_DIR);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Nenhum diretório de saves encontrado.");
            return;
        }

        System.out.println("\n--- Arquivos de Save Disponíveis ---");
        File[] saveFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".sav"));

        if (saveFiles == null || saveFiles.length == 0) {
            System.out.println("Nenhum arquivo de save encontrado.");
            return;
        }

        for (int i = 0; i < saveFiles.length; i++) {
            String fileName = saveFiles[i].getName();
            String playerName = fileName.substring(0, fileName.lastIndexOf(".sav")).replaceAll("_", " ");
            System.out.println((i + 1) + ". " + playerName);
        }
        System.out.println("------------------------------------");
    }

    protected static String fileName(String fileName) {
        return SAVE_DIR + File.separator + fileName.replaceAll("\\s+", "_") + ".sav";

    }
}
