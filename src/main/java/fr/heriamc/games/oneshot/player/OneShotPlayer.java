package fr.heriamc.games.oneshot.player;

import fr.heriamc.api.user.unlock.HeriaUnlockable;
import fr.heriamc.games.engine.ffa.player.FFAGamePlayer;
import fr.heriamc.games.oneshot.cosmetic.Cosmetic;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.player.kit.OneShotGameKit;
import fr.heriamc.games.oneshot.player.wallet.OneShotCoins;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class OneShotPlayer extends FFAGamePlayer {

    private final OneShotCoins points;
    private final OneShotGameKit kit;

    private final HeriaUnlockable unlockedCosmetics;
    private final Map<CosmeticType, Cosmetic> selectedCosmetics;

    private OneShotPlayer lastAttacker;

    public OneShotPlayer(UUID uuid, int kills, int deaths, int killStreak, int bestKillStreak, double wallet, HeriaUnlockable unlockedCosmetics, Map<CosmeticType, Cosmetic> selectedCosmetics, boolean spectator) {
        super(uuid, kills, deaths, killStreak, bestKillStreak, spectator);
        this.points = new OneShotCoins(wallet);
        this.unlockedCosmetics = unlockedCosmetics;
        this.selectedCosmetics = selectedCosmetics;
        this.kit = new OneShotGameKit(this);
        this.lastAttacker = null;
    }

    public <C extends Cosmetic> boolean hasSelected(CosmeticType type, C cosmetic) {
        return selectedCosmetics.get(type) == cosmetic;
    }

    public void onKill() {
        player.setHealth(20);
        kit.addArrow();

        addKill();
        addKillStreak();
    }

    public void onDeath() {
        player.setHealth(20);

        addDeath();
        resetKillStreak();
    }

    public void cleanUp() {
        var inventory = player.getInventory();

        inventory.clear();
        inventory.setArmorContents(null);

        player.setHealth(20);
        player.getActivePotionEffects().clear();
    }

}