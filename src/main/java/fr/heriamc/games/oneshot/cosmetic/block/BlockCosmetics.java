package fr.heriamc.games.oneshot.cosmetic.block;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum BlockCosmetics implements BlockCosmetic {

    CLAY ("clay", "§7Bloc d'argile", Material.STAINED_CLAY, 0, HeriaRank.PLAYER, false),

    BRICK ("brick", "§cBloc de brique", Material.BRICK, 1000, HeriaRank.PLAYER, true),

    GOLD ("gold", "§eBloc d'or", Material.GOLD_BLOCK, 1000, HeriaRank.PLAYER, true),
    EMERALD ("emerald", "§aBloc d'émeraude", Material.EMERALD_BLOCK, 1000, HeriaRank.PLAYER, true),
    DIAMOND ("diamond", "§bBloc de diamant", Material.DIAMOND_BLOCK, 1000, HeriaRank.PLAYER, true);

    private final String id, name;
    private final Material material;

    private double price;
    private HeriaRank requiredRank;
    private boolean purchasable;

    public String getId() {
        return "oneshot.blocks." + id;
    }

    @Override
    public Material getIcon() {
        return material;
    }

    @Override
    public void select(OneShotPlayer gamePlayer) {
        gamePlayer.getSelectedCosmetics().put(CosmeticType.BLOCK, this);
        gamePlayer.getKit().changeBlock(material);
    }

    @Override
    public void deselect(OneShotPlayer gamePlayer) {
        gamePlayer.getSelectedCosmetics().put(CosmeticType.BLOCK, CLAY);
        gamePlayer.getKit().changeBlock(CLAY.material);
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