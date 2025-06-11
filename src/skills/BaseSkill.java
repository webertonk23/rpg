package skills;

import characters.Character;
import interfaces.Skill;

public abstract class BaseSkill implements Skill {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected String description;
    protected int manaCost;
    protected int cooldown;
    protected int currentCooldown;

    public BaseSkill(String name, String description, int manaCost, int cooldown, int currentCooldown) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.currentCooldown = currentCooldown;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    @Override
    public int getCooldown() {
        return cooldown;
    }

    @Override
    public int getCurrentCooldown() {
        return currentCooldown;
    }

    @Override
    public void setCurrentCooldown(int cooldown) {
        this.currentCooldown = cooldown;
    }

    @Override
    public boolean canUse(Character user) {
        if (user.getMana() < manaCost) {
            System.out.println(user.getName() + " não tem mana suficiente para usar " + name + " (Necessita: "
                    + manaCost + ", Possui: " + user.getMana() + ").");
            return false;
        }
        if (currentCooldown > 0) {
            System.out.println(name + " está em cooldown. Faltam " + currentCooldown + " turnos.");
            return false;
        }
        return true;
    }

    @Override
    public void reduceCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    @Override
    public abstract void execute(Character user, Character target);
}
