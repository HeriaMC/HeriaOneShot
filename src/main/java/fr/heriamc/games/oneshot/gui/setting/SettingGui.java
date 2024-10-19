package fr.heriamc.games.oneshot.gui.setting;

import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.engine.utils.gui.BaseGameGui;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class SettingGui extends BaseGameGui<OneShotGame, OneShotPlayer> {

    public SettingGui(OneShotGame game, OneShotPlayer gamePlayer) {
        super(game, gamePlayer, "Paramètres", 54, false);
    }

    @Override
    public void contents(Inventory inventory) {
        setBorder(inventory, DyeColor.YELLOW.getWoolData());

        inventory.setItem(4, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§cParamètres").build());

        inventory.setItem(20, new ItemBuilder(Material.PAPER).setName("reset stat").build());
        inventory.setItem(21, new ItemBuilder(Material.DIAMOND_SWORD).setName("edit kit").build());
        inventory.setItem(22, new ItemBuilder(Material.WATCH).setName("choix du temps").build());
        inventory.setItem(23, new ItemBuilder(Material.BOOK_AND_QUILL).setName("afficher messages de kill").build());
    }

}