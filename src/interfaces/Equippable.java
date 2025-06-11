package interfaces;

import characters.Character;
import items.Equipment;

public interface Equippable {
    Equipment.Slot getSlot();

    void onEquip(Character character);

    void onUnequip(Character character);
}
