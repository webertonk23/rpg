package inventory;

import characters.Character;
import interfaces.Equippable;
import interfaces.Usable;
import items.Equipment;
import items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Character owner;
    private List<Item> items;
    private Map<Equipment.Slot, Equipment> equippedGear;
    private int currentWeight;
    private int maxWeightCapacity;

    public Inventory(Character owner) {
        this.owner = owner;
        this.items = new ArrayList<>();
        this.equippedGear = new HashMap<>();
        this.currentWeight = 0;

        updateMaxWeightCapacity();

        for (Equipment.Slot slot : Equipment.Slot.values()) {
            equippedGear.put(slot, null);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public Map<Equipment.Slot, Equipment> getEquippedGear() {
        return equippedGear;
    }

    public void updateMaxWeightCapacity() {
        int baseWeightPerPoint = 5;
        this.maxWeightCapacity = this.owner.getStrength() * baseWeightPerPoint;
    }

    private boolean canAddItem(Item item) {
        return (this.currentWeight + item.getWeight()) <= this.maxWeightCapacity;
    }

    public boolean addItem(Item item) {
        if (!canAddItem(item)) {
            System.out.println(owner.getName() + " não tem espaço de peso suficiente para " + item.getName()
                    + ". Peso atual: " + currentWeight + "/" + maxWeightCapacity);
            return false;
        }

        items.add(item);
        currentWeight += item.getWeight();
        System.out.println(owner.getName() + " pegou: " + item.getName() + ". Peso atual: " + currentWeight + "/"
                + maxWeightCapacity);
        return true;
    }

    private boolean removeItem(Item item) {
        if (items.remove(item)) {
            currentWeight -= item.getWeight();
            System.out.println(owner.getName() + " removeu: " + item.getName() + ". Peso atual: " + currentWeight + "/"
                    + maxWeightCapacity);
            return true;
        }
        System.out.println(owner.getName() + " não possui " + item.getName() + " no inventário.");
        return false;
    }

    public void useItem(Item item) {
        if (!items.contains(item)) {
            System.out.println(owner.getName() + " não possui " + item.getName() + " no inventário para usar.");
            return;
        }

        if (item instanceof Usable) {
            ((Usable) item).use(owner);
            removeItem(item);
            return;
        }

        System.out.println(item.getName() + " não pode ser usado diretamente desta forma.");
    }

    public boolean equipItem(Equipment newGear) {
        if (!items.contains(newGear)) {
            System.out.println(owner.getName() + " não possui " + newGear.getName() + " no inventtário para equipar");
            return false;
        }

        Equipment currentGearInSlot = equippedGear.get(newGear.getSlot());
        if (currentGearInSlot != null) {
            unequipItem(currentGearInSlot);
        }

        equippedGear.put(newGear.getSlot(), newGear);
        items.remove(newGear);
        currentWeight -= newGear.getWeight();

        ((Equippable) newGear).onEquip(owner);

        System.out.println(owner.getName() + " equipou: " + newGear.getName());
        return true;
    }

    public boolean unequipItem(Equipment gearToUnequip) {
        if (!equippedGear.containsValue(gearToUnequip)) {
            System.out.println(owner.getName() + " não tem " + gearToUnequip + " Equipado.");
            return false;
        }

        if (!canAddItem(gearToUnequip)) {
            System.out.println(owner.getName() + " Não tem espaço para desequipar " + gearToUnequip.getName()
                    + ". Inventário cheio!");
            return false;
        }

        equippedGear.remove(gearToUnequip.getSlot());
        items.add(gearToUnequip);
        currentWeight += gearToUnequip.getWeight();

        ((Equipment) gearToUnequip).onUnequip(owner);

        System.out.println(owner.getName() + " desequipou " + gearToUnequip.getName());
        return true;
    }

    public void displayInventory() {
        System.out.println("\n--- Inventário de " + owner.getName() + " (Peso: " + currentWeight + "/"
                + maxWeightCapacity + ") ---");
        if (items.isEmpty()) {
            System.out.println("Vazio.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out
                        .println((i + 1) + ". " + items.get(i).getName() + " (Peso: " + items.get(i).getWeight() + ")");
            }
        }
        System.out.println("------------------------------------------");
    }

    public void displayEquippedGear() {
        System.out.println("\n--- Equipamento de " + owner.getName() + " ---");
        boolean anyEquipped = false;
        for (Map.Entry<Equipment.Slot, Equipment> entry : equippedGear.entrySet()) {
            if (entry.getValue() != null) {
                System.out.println(entry.getKey() + ": " + entry.getValue().getName());
                anyEquipped = true;
            } else {
                System.out.println(entry.getKey() + ": Vazio");
            }
        }
        if (!anyEquipped) {
            System.out.println("Nenhum equipamento.");
        }
        System.out.println("-------------------------");
    }
}
