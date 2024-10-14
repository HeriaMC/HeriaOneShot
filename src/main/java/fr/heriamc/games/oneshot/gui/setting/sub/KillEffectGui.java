package fr.heriamc.games.oneshot.gui.setting.sub;

import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.cosmetic.kill.KillCosmetics;
import fr.heriamc.games.oneshot.gui.setting.SubSettingGui;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class KillEffectGui extends SubSettingGui<KillCosmetics> {

    public KillEffectGui(OneShotGame game, OneShotPlayer gamePlayer, HeriaMenu beforeMenu) {
        super(game, gamePlayer, KillCosmetics.class, "Effet de kill", 54, false, beforeMenu);
    }

    @Override
    public void inventory(Inventory inventory) {
        setBorder(inventory, DyeColor.CYAN.getWoolData());
        insertCategoryIcon(inventory, Material.FIREWORK, "§7» §6Effet de kill");

        insertFilterButton(inventory, 48);
        insertCloseButton(inventory, 47);
    }

    @Override
    protected ItemBuilder item(KillCosmetics cosmetic, int i, int i1) {
        var icon = new ItemBuilder(cosmetic.getIcon());
        List<String> lore = new ArrayList<>();

        if (gamePlayer.hasSelected(CosmeticType.KILL_EFFECT, cosmetic))
            icon.addEnchant(Enchantment.LUCK, 1).flag(ItemFlag.HIDE_ENCHANTS);

        return icon.setName(cosmetic.getName())
                .setLoreWithList(lore)
                .onClick(event -> {
                    // TODO: open confirm buy gui
                });
    }

}