package fr.heriamc.games.oneshot.cosmetic.sword;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum SwordCosmetics implements SwordCosmetic {

    WOOD ("wood", "§6Épée en bois", Material.WOOD_SWORD, 0, HeriaRank.PLAYER, false),
    STONE ("stone", "§8Épée en pierre", Material.STONE_SWORD, 100, HeriaRank.PLAYER, true),
    IRON ("iron", "§7Épée en fer", Material.IRON_SWORD, 100, HeriaRank.PLAYER, true),
    GOLD ("gold", "§eÉpée en or", Material.GOLD_SWORD, 100, HeriaRank.PLAYER, true),
    DIAMOND ("diamond", "§bÉpée en diamant", Material.DIAMOND_SWORD, 100, HeriaRank.PLAYER, true),

    // ADD DESCRIPTION
    WAR_AXE ("waraxe", "§cHache de guerre", Material.DIAMOND_AXE, 999, HeriaRank.PLAYER, true),
    BONK ("bonk", "§dBONK", Material.STICK, 9999, HeriaRank.PLAYER, true),
    BONE ("bone", "§fOs", Material.BONE, 9999, HeriaRank.PLAYER, true);

    private final String id, name;
    private final Material sword;

    private double price;
    private HeriaRank requiredRank;
    private boolean purchasable;

    public String getId() {
        return "oneshot.swords." + id;
    }

    @Override
    public Material getIcon() {
        return sword;
    }

    @Override
    public void select(OneShotPlayer gamePlayer) {
        gamePlayer.getSelectedCosmetics().put(CosmeticType.SWORD, this);
        gamePlayer.getKit().changeSword(sword);
    }

    @Override
    public void deselect(OneShotPlayer gamePlayer) {
        gamePlayer.getSelectedCosmetics().put(CosmeticType.SWORD, WOOD);
        gamePlayer.getKit().changeSword(WOOD.sword);
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