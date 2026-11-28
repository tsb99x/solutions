import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

/* FIXME : Wow, this code sucks EXTREMELY!
But it does give proper results, nontheless...

time java Part1.java
Optional[1269]

________________________________________________________
Executed in  369.78 secs    fish           external
   usr time  373.50 secs  281.00 micros  373.50 secs
   sys time    1.59 secs  384.00 micros    1.59 secs
*/

record Spell(
        String name,
        int cost,
        int timer,
        BiConsumer<Player, Boss> fn) {
}

final Spell[] INSTANTS = {
        new Spell("Magic Missile", 53, 0, this::applyMagicMissile),
        new Spell("Drain", 73, 0, this::applyDrain) };

final Spell[] ENCHANTMENTS = {
        new Spell("Shield", 113, 6, this::applyShield),
        new Spell("Poison", 173, 6, this::applyPoison),
        new Spell("Recharge", 229, 5, this::applyRecharge) };

void applyMagicMissile(Player player, Boss boss) {
    boss.health -= 4;
}

void applyDrain(Player player, Boss boss) {
    boss.health -= 2;
    player.health += 2;
}

void applyShield(Player player, Boss boss) {
    player.armor += 7;
}

void applyPoison(Player player, Boss boss) {
    boss.health -= 3;
}

void applyRecharge(Player player, Boss boss) {
    player.mana += 101;
}

class Player {

    int health;
    int mana;
    int armor;

    Player(int health, int mana, int armor) {
        this.health = health;
        this.mana = mana;
        this.armor = armor;
    }

}

class Boss {

    int health;
    final int damage;

    Boss(int health, int damage) {
        this.health = health;
        this.damage = damage;
    }

}

Optional<Integer> playerTurn(List<Spell> effects, Player oldPlayer, Boss oldBoss, int manaSpent) {
    var player = new Player(oldPlayer.health, oldPlayer.mana, 0);
    var boss = new Boss(oldBoss.health, oldBoss.damage);

    if (player.health <= 0) {
        return Optional.empty();
    }

    for (var effect : effects) {
        effect.fn.accept(player, boss);
    }
    effects = effects.stream()
            .map(x -> new Spell(x.name, x.cost, x.timer - 1, x.fn))
            .filter(x -> x.timer > 0)
            .toList();

    if (boss.health <= 0) {
        return Optional.of(manaSpent);
    }

    // --- //

    var lowestManaSpent = Optional.<Integer>empty();

    for (var instant : INSTANTS) {
        if (instant.cost > player.mana) {
            continue;
        }
        if (lowestManaSpent.isPresent() && manaSpent + instant.cost >= lowestManaSpent.get()) {
            return lowestManaSpent;
        }
        var newPlayer = new Player(player.health, player.mana, 0);
        var newBoss = new Boss(boss.health, boss.damage);
        newPlayer.mana -= instant.cost;
        instant.fn.accept(newPlayer, newBoss);
        var res = bossTurn(effects, newPlayer, newBoss, manaSpent + instant.cost);
        if (res.isPresent()) {
            lowestManaSpent = lowestManaSpent.map(x -> (int) Math.min(x, res.get())).or(() -> res);
        }
    }

    for (var enchantment : ENCHANTMENTS) {
        var sameEffectActive = effects.stream()
                .anyMatch(x -> x.name.equals(enchantment.name));
        if (enchantment.cost > player.mana || sameEffectActive) {
            continue;
        }
        if (lowestManaSpent.isPresent() && manaSpent + enchantment.cost >= lowestManaSpent.get()) {
            return lowestManaSpent;
        }
        var newPlayer = new Player(player.health, player.mana, 0);
        var newBoss = new Boss(boss.health, boss.damage);
        newPlayer.mana -= enchantment.cost;
        var newEffects = new ArrayList<>(effects);
        newEffects.add(enchantment);
        var res = bossTurn(newEffects, newPlayer, newBoss, manaSpent + enchantment.cost);
        if (res.isPresent()) {
            lowestManaSpent = lowestManaSpent.map(x -> (int) Math.min(x, res.get())).or(() -> res);
        }
    }

    return lowestManaSpent;
}

Optional<Integer> bossTurn(List<Spell> effects, Player oldPlayer, Boss oldBoss, int manaSpent) {
    var player = new Player(oldPlayer.health, oldPlayer.mana, 0);
    var boss = new Boss(oldBoss.health, oldBoss.damage);

    for (var effect : effects) {
        effect.fn.accept(player, boss);
    }
    effects = effects.stream()
            .map(x -> new Spell(x.name, x.cost, x.timer - 1, x.fn))
            .filter(x -> x.timer > 0)
            .toList();

    if (boss.health <= 0) {
        return Optional.of(manaSpent);
    }

    // --- //

    var bossAttack = Math.max(1, boss.damage - player.armor);
    player.health -= bossAttack;

    return playerTurn(effects, player, boss, manaSpent);
}

void main() {
    var player = new Player(50, 500, 0);
    var boss = new Boss(58, 9);
    var minMana = playerTurn(List.of(), player, boss, 0);
    IO.println(minMana);
}
