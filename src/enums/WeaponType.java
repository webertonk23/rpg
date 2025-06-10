package enums;

public enum WeaponType {
    SWORD("Espada"),
    DAGGER("Adaga"),
    SPEAR("Lança"),
    AXE("Machado"),
    STAFF("Cajado"),
    BOOK("Livro"),
    ORB("Orb"),
    BOW("Arco");

    private final String typeName;

    WeaponType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
