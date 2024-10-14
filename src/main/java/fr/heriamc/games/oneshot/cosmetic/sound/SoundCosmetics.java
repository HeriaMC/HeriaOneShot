package fr.heriamc.games.oneshot.cosmetic.sound;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.Collection;

@Getter
@AllArgsConstructor
public enum SoundCosmetics implements SoundCosmetic {

    NONE ("none", "Aucun", null, Material.BARRIER, 0, HeriaRank.PLAYER, false),
    EXPLOSION ("", "§4Son explosif", Sound.EXPLODE, Material.TNT, 1000, HeriaRank.PLAYER, true);

    private final String id, name;
    private final Sound sound;

    private final Material icon;
    private double price;
    private HeriaRank requiredRank;

    private boolean purchasable;

    public String getId() {
        return "oneshot.sounds." + id;
    }

    @Override
    public void play(Collection<OneShotPlayer> collection) {

    }

    @Override
    public void select(OneShotPlayer gamePlayer) {
        gamePlayer.getSelectedCosmetics().put(CosmeticType.SOUND_EFFECT, this);
    }

    @Override
    public void deselect(OneShotPlayer gamePlayer) {
        gamePlayer.getSelectedCosmetics().put(CosmeticType.SOUND_EFFECT, NONE);
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