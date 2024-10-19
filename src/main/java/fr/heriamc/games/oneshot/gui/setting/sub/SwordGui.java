package fr.heriamc.games.oneshot.gui.setting.sub;

import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.cosmetic.sword.SwordCosmetics;
import fr.heriamc.games.oneshot.gui.setting.SubSettingGui;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class SwordGui extends SubSettingGui<SwordCosmetics> {

    public SwordGui(OneShotGame game, OneShotPlayer gamePlayer, HeriaMenu beforeMenu) {
        super(game, gamePlayer, SwordCosmetics.class,"Épée", 54, false, beforeMenu);
    }

    @Override
    public void setup(Inventory inventory) {
        setBorder(inventory, DyeColor.SILVER.getWoolData());
        insertCategoryIcon(inventory, Material.DIAMOND_SWORD, "§7» §6Épée");

        insertFilterButton(inventory, 48);
        insertCloseButton(inventory, 47);
    }

}