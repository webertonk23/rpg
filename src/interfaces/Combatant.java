package interfaces;

public interface Combatant {
    void attack(Combatant target);

    void takeDamage(int amount);

    boolean isAlive();

    String getName();
}
