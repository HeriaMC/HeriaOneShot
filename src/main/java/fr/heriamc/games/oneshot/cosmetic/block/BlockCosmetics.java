package fr.heriamc.games.oneshot.cosmetic.block;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum BlockCosmetics implements BlockCosmetic {

    CLAY ("oneshot.blocks.clay", "§7Bloc d'argile", Material.STAINED_CLAY, 0, HeriaRank.PLAYER, false),

    BRICK ("oneshot.blocks.brick", "§cBloc de brique", Material.BRICK, 1000, HeriaRank.PLAYER, true),

    GOLD ("oneshot.blocks.gold", "§eBloc d'or", Material.GOLD_BLOCK, 1000, HeriaRank.PLAYER, true),
    EMERALD ("oneshot.blocks.emerald", "§aBloc d'émeraude", Material.EMERALD_BLOCK, 1000, HeriaRank.PLAYER, true),
    DIAMOND ("oneshot.blocks.diamond", "§bBloc de diamant", Material.DIAMOND_BLOCK, 1000, HeriaRank.PLAYER, true);

    private final String id, name;
    private final Material material;

    private int price;
    private HeriaRank requiredRank;
    private boolean purchasable;

    public static final List<BlockCosmetics> blocks = Arrays.asList(values());

    public static BlockCosmetic getFromId(String id) {
        return blocks.stream().filter(block -> block.getId().equals(id)).findFirst().orElse(CLAY);
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
        gamePlayer.getPoints().remove((double) price);
    }

    @Override
    public boolean has(OneShotPlayer gamePlayer) {
        return gamePlayer.getUnlockedCosmetics().getUnlockableData().getOrDefault(id, false);
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