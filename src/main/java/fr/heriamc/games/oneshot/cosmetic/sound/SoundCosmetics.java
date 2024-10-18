package fr.heriamc.games.oneshot.cosmetic.sound;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public enum SoundCosmetics implements SoundCosmetic {

    NONE ("oneshot.sounds.none", "Aucun", null, Material.BARRIER, 0, HeriaRank.PLAYER, false),
    EXPLOSION ("oneshot.sounds.explosion", "§4Son explosif", Sound.EXPLODE, Material.TNT, 1000, HeriaRank.PLAYER, true);

    private final String id, name;
    private final Sound sound;

    private final Material icon;
    private int price;
    private HeriaRank requiredRank;

    private boolean purchasable;

    public static final List<SoundCosmetics> sounds = Arrays.asList(values());

    public static SoundCosmetic getFromId(String id) {
        return sounds.stream().filter(block -> block.getId().equals(id)).findFirst().orElse(NONE);
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
        gamePlayer.getPoints().remove((double) price);
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
    public void setPrice(int price) {
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