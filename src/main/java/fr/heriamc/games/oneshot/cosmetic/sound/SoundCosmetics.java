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
    EXPLOSION ("oneshot.sounds.explosion", "§4Son explosif", Sound.EXPLODE, Material.TNT, 1000, HeriaRank.PLAYER, true),
    WATER ("oneshot.sounds.water", "§9Son d'eau", Sound.WATER, Material.WATER_BUCKET, 100, HeriaRank.PLAYER, true),
    SKELETON ( "oneshot.sounds.skeleton", "§fSon squelettique", Sound.SKELETON_HURT, Material.BONE, 100, HeriaRank.PLAYER, true),
    ANVIL ("oneshot.sounds.anvil", "§8Son d'enclume", Sound.ANVIL_USE, Material.ANVIL, 100, HeriaRank.PLAYER, true);

    private final String id, name;
    private final Sound sound;

    private final Material icon;
    private int price;
    private HeriaRank requiredRank;

    private boolean purchasable;

    public static final List<SoundCosmetics> sounds = Arrays.asList(values());

    public static SoundCosmetic getFromId(String id) {
        return sounds.stream().filter(sound -> sound.getId().equals(id)).findFirst().orElse(NONE);
    }

    public static SoundCosmetic getFromIdNullable(String id) {
        return sounds.stream().filter(sound -> sound.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void play(Collection<OneShotPlayer> collection) {

    }

    @Override
    public CosmeticType getType() {
        return CosmeticType.SOUND_EFFECT;
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
    public boolean hasRequiredRank(OneShotPlayer gamePlayer) {
        return gamePlayer.getHeriaPlayer().getRank().getPower() >= requiredRank.getPower();
    }

    @Override
    public boolean isSelected(OneShotPlayer gamePlayer) {
        return gamePlayer.hasSelected(CosmeticType.SOUND_EFFECT, this);
    }

    @Override
    public boolean canSelect(OneShotPlayer gamePlayer) {
        return has(gamePlayer) && !isSelected(gamePlayer);
    }

    @Override
    public boolean canBuy(OneShotPlayer gamePlayer) {
        return purchasable && gamePlayer.getPoints().getWallet() >= price;
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