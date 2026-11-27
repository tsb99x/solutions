// FIXME : Quick implementation. Works, but looks ugly. Rewrite later!

record Item(
        String name,
        int cost,
        int damage,
        int armor) {
}

class Shop {

    static final Item[] weapons = {
            new Item("Dagger", 8, 4, 0),
            new Item("Shortsword", 10, 5, 0),
            new Item("Warhammer", 25, 6, 0),
            new Item("Longsword", 40, 7, 0),
            new Item("Greataxe", 74, 8, 0) };

    static final Item[] armor = {
            null,
            new Item("Leather", 13, 0, 1),
            new Item("Chainmail", 31, 0, 2),
            new Item("Splintmail", 53, 0, 3),
            new Item("Bandedmail", 75, 0, 4),
            new Item("Platemail", 102, 0, 5) };

    static final Item[] rings = {
            null,
            new Item("Damage +1", 25, 1, 0),
            new Item("Damage +2", 50, 2, 0),
            new Item("Damage +3", 100, 3, 0),
            new Item("Defense +1", 20, 0, 1),
            new Item("Defense +2", 40, 0, 2),
            new Item("Defense +3", 80, 0, 3) };

}

class Fighter {

    private int health;
    private final int damage;
    private final int armor;
    private final List<Item> equipment;

    Fighter(int health, int damage, int armor, List<Item> equipment) {
        this.health = health;
        this.damage = damage;
        this.armor = armor;
        this.equipment = new ArrayList<>(equipment);
    }

    boolean isAlive() {
        return health > 0;
    }

    int totalDamage() {
        return damage + equipment.stream()
                .mapToInt(Item::damage)
                .sum();
    }

    int totalArmor() {
        return armor + equipment.stream()
                .mapToInt(Item::armor)
                .sum();
    }

    void takeDamage(int damage) {
        health -= damage;
    }

    int itemsCost() {
        return equipment.stream()
                .mapToInt(Item::cost)
                .sum();
    }

}

class Battle {

    private final List<Fighter> fighters;

    Battle(Fighter a, Fighter b) {
        fighters = Arrays.asList(a, b);
    }

    void play() {
        while (fighters.stream().allMatch(Fighter::isAlive)) {
            var attacker = fighters.get(0);
            var defender = fighters.get(1);
            var dmg = attacker.totalDamage();
            var arm = defender.totalArmor();
            defender.takeDamage(Math.max(dmg - arm, 1));
            Collections.rotate(fighters, 1);
        }
    }

}

void main() {
    var highestCost = Integer.MIN_VALUE;
    for (var weapon : Shop.weapons) {
        for (var armor : Shop.armor) {
            for (var ringRight : Shop.rings) {
                for (var ringLeft : Shop.rings) {
                    if (ringLeft == ringRight) {
                        continue;
                    }
                    var equipment = Stream.of(weapon, armor, ringRight, ringLeft)
                            .filter(Objects::nonNull)
                            .toList();
                    var player = new Fighter(100, 0, 0, equipment);
                    var boss = new Fighter(100, 8, 2, List.of());
                    new Battle(player, boss).play();
                    if (boss.isAlive()) {
                        highestCost = Math.max(player.itemsCost(), highestCost);
                    }
                }
            }
        }
    }
    IO.println(highestCost);
}
