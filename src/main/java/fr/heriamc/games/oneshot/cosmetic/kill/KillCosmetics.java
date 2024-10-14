package fr.heriamc.games.oneshot.cosmetic.kill;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum KillCosmetics implements KillCosmetic {

    NONE ("none", "Aucun", Material.BARRIER, 0, HeriaRank.PLAYER, false),
    FIRE_WORK ("firework", "§6Feu d'artifice", Material.FIREWORK, 1000, HeriaRank.PLAYER, true);

    private final String id, name;

    private final Material icon;
    private double price;
    private HeriaRank requiredRank;

    private boolean purchasable;

    public String getId() {
        return "oneshot.effects." + id;
    }

    @Override
    public void play(OneShotPlayer gamePlayer) {
        // TODO: run task
        // task.run();
    }

    @Override
    public void select(OneShotPlayer gamePlayer) {
        gamePlayer.getSelectedCosmetics().put(CosmeticType.KILL_EFFECT, this);
    }

    @Override
    public void deselect(OneShotPlayer gamePlayer) {
        gamePlayer.getSelectedCosmetics().put(CosmeticType.KILL_EFFECT, NONE);
    }

    @Override
    public void buy(OneShotPlayer gamePlayer) {
        gamePlayer.getUnlockedCosmetics().unlock(id);
        gamePlayer.getPoints().remove(price);
    }

    @Override
    public boolean has(OneShotPlayer gamePlayer) {
        return gamePlayer.getUnlockedCosmetics().isUnlocked(id);
    }

    @Override
    public boolean canBuy(OneShotPlayer gamePlayer) {
        return gamePlayer.getPoints().getWallet() >= price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setRequiredRank(HeriaRank requiredRank) {
        this.requiredRank = requiredRank;
    }

    @Override
    public void setPurchasable(boolean purchasable) {
        this.purchasable = purchasable;
    }

}