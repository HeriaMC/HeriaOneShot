package fr.heriamc.games.oneshot.player;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.user.unlock.HeriaUnlockable;
import fr.heriamc.games.engine.player.SimpleGamePlayer;
import fr.heriamc.games.oneshot.cosmetic.Cosmetic;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.player.kit.OneShotGameKit;
import fr.heriamc.games.oneshot.player.wallet.OneShotCoins;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class OneShotPlayer extends SimpleGamePlayer {

    private int killStreak, bestKillStreak;

    private final OneShotCoins points;
    private final OneShotGameKit kit;

    private final HeriaUnlockable unlockedCosmetics;
    private final Map<CosmeticType, Cosmetic> selectedCosmetics;

    private OneShotPlayer lastAttacker;
    private OneShotPlayerState playerState;

    public OneShotPlayer(UUID uuid, int kills, int deaths, int killStreak, int bestKillStreak, double coins, boolean spectator) {
        super(uuid, kills, deaths, spectator);
        this.killStreak = killStreak;
        this.bestKillStreak = bestKillStreak;
        this.points = new OneShotCoins(coins);
        this.unlockedCosmetics = HeriaAPI.get().getUnlockableManager().get(uuid);
        this.selectedCosmetics = new HashMap<>(4);
        this.kit = new OneShotGameKit(this);
        this.playerState = OneShotPlayerState.IN_LOBBY;
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

    public void addKillStreak() {
        this.killStreak++;
        this.bestKillStreak = Math.max(killStreak, bestKillStreak);
    }

    public void resetKillStreak() {
        this.killStreak = 0;
    }

}