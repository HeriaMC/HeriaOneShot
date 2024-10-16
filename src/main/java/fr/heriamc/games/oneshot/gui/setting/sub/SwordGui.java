package fr.heriamc.games.oneshot.gui.setting.sub;

import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.cosmetic.sword.SwordCosmetics;
import fr.heriamc.games.oneshot.gui.setting.SubSettingGui;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class SwordGui extends SubSettingGui<SwordCosmetics> {

    public SwordGui(OneShotGame game, OneShotPlayer gamePlayer, HeriaMenu beforeMenu) {
        super(game, gamePlayer, SwordCosmetics.class, "Épée", 54, false, beforeMenu);
    }

    @Override
    public void inventory(Inventory inventory) {
        setBorder(inventory, DyeColor.SILVER.getWoolData());
        insertCategoryIcon(inventory, Material.DIAMOND_SWORD, "§7» §6Épée");

        insertFilterButton(inventory, 48);
        insertCloseButton(inventory, 47);
    }

    @Override
    protected ItemBuilder item(SwordCosmetics cosmetic, int i, int i1) {
        var icon = new ItemBuilder(cosmetic.getIcon());
        List<String> lore = new ArrayList<>();

        if (gamePlayer.hasSelected(CosmeticType.SWORD, cosmetic))
            icon.addEnchant(Enchantment.DAMAGE_ALL, 1).flag(ItemFlag.HIDE_ENCHANTS);

        return icon.setName(cosmetic.getName())
                .setLoreWithList(
                        " ",
                        gamePlayer.getUnlockedCosmetics().isUnlocked(cosmetic.getId()) ?
                                "§8» §7Prix: §aPosséder" : "§8» §7Prix: §6" + cosmetic.getPrice(),
                        " ",
                        gamePlayer.getUnlockedCosmetics().isUnlocked(cosmetic.getId())
                                ? "§6§l❱ §eClique pour équiper"
                                : "§6§l❱ §eClique pour acheter"
                )
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .onClick(event -> {
                    // TODO: open confirm buy gui
                });
    }

}