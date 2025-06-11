package items;

import enums.WeaponType;
import items.Equipment.Slot;

public enum WeaponList {
    // Espadas
    BASIC_SWORD(
        "Espada Básica",
        "Uma espada de treinamento.",
        10,
        2,
        2,
        0,
        0,
        0,
        0,
        0,
        0,
        7,
        WeaponType.SWORD
    ),
    LONG_SWORD(
        "Espada Longa",
        "Uma espada equilibrada para combate.",
        18,
        3,
        4,
        0,
        0,
        0,
        0,
        0,
        0,
        12,
        WeaponType.SWORD
    ),
    // Adagas
    SIMPLE_DAGGER(
        "Adaga Simples",
        "Uma lâmina pequena e furtiva.",
        8,
        1,
        1,
        2,
        0,
        0,
        0,
        0,
        0,
        5,
        WeaponType.DAGGER
    ),
    VENOMOUS_FANG(
        "Presa Venenosa",
        "Uma adaga exótica com uma lâmina coberta por veneno. Chance de envenenar o alvo.",
        30,
        2,
        2,
        5,
        0,
        0,
        0,
        0,
        0,
        8,
        WeaponType.DAGGER
    ),
    // Lanças
    IRON_SPEAR(
        "Lança de Ferro",
        "Uma lança resistente para alcance.",
        15,
        4,
        3,
        0,
        0,
        0,
        0,
        0,
        0,
        10,
        WeaponType.SPEAR
    ),
    // Machados
    BATTLE_AXE(
        "Machado de Batalha",
        "Um machado pesado para golpes poderosos.",
        22,
        5,
        5,
        0,
        0,
        0,
        0,
        0,
        0,
        15,
        WeaponType.AXE
    ),
    // Cajados
    WOODEN_STAFF(
        "Cajado de Madeira",
        "Um cajado simples para magos iniciantes.",
        5,
        2,
        0,
        0,
        3,
        0,
        0,
        0,
        0,
        3,
        WeaponType.STAFF
    ),
    FIRE_STAFF(
        "Cajado de Fogo",
        "Um cajado que amplifica magias de fogo.",
        12,
        2,
        0,
        0,
        7,
        0,
        0,
        0,
        0,
        8,
        WeaponType.STAFF
    ),
    // Livros
    BASIC_SPELL_BOOK(
        "Livro de Magias Básicas",
        "Um livro com feitiços iniciais.",
        7,
        1,
        0,
        0,
        4,
        0,
        0,
        0,
        0,
        4,
        WeaponType.BOOK
    ),
    // Orbes
    CRYSTAL_ORB(
        "Orbe de Cristal",
        "Um orbe que concentra energia mágica.",
        10,
        1,
        0,
        0,
        6,
        0,
        0,
        0,
        0,
        6,
        WeaponType.ORB
    ),
    // Arcos
    SIMPLE_BOW(
        "Arco Simples",
        "Um arco para arqueiros iniciantes.",
        12,
        2,
        2,
        3,
        0,
        0,
        0,
        0,
        0,
        8,
        WeaponType.BOW
    );

    private final Weapon weapon;

    WeaponList(
        String name,
        String description,
        int value,
        int weight,
        int strengthBonus,
        int dexterityBonus,
        int intelligenceBonus,
        int vitalityBonus,
        int charismaBonus,
        int luckBonus,
        int resistanceBonus,
        int damage,
        WeaponType weaponType
    ) {
        this.weapon = new Weapon(
            name,
            description,
            value,
            weight,
            Equipment.Slot.WEAPON,
            strengthBonus,
            dexterityBonus,
            intelligenceBonus,
            vitalityBonus,
            charismaBonus,
            luckBonus,
            resistanceBonus,
            0,
            damage,
            weaponType
        );
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
