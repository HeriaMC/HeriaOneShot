package fr.heriamc.games.oneshot.gui.cosmetic;

import fr.heriamc.bukkit.menu.HeriaMenuManager;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.engine.utils.gui.BaseGameGui;
import fr.heriamc.games.oneshot.OneShotAddon;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.gui.cosmetic.sub.BlockGui;
import fr.heriamc.games.oneshot.gui.cosmetic.sub.KillEffectGui;
import fr.heriamc.games.oneshot.gui.cosmetic.sub.KillSoundGui;
import fr.heriamc.games.oneshot.gui.cosmetic.sub.SwordGui;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class CosmeticGui extends BaseGameGui<OneShotGame, OneShotPlayer> {

    private final HeriaMenuManager menuManager;

    public CosmeticGui(OneShotAddon addon, OneShotGame game, OneShotPlayer gamePlayer) {
        super(game, gamePlayer, "Cosmétiques", 54, false);
        this.menuManager = addon.getHeriaBukkit().getMenuManager();
    }

    @Override
    public void contents(Inventory inventory) {
        setBorder(inventory, DyeColor.YELLOW.getWoolData());

        inventory.setItem(4, new ItemBuilder(Material.CHEST).setName("§eCosmétiques").build());

        insertInteractItem(inventory, 22, new ItemBuilder(Material.FIREWORK).setName("§7» §6Effet de kill")
                .onClick(event -> menuManager.open(new KillEffectGui(game, gamePlayer, this))));

        insertInteractItem(inventory, 30, new ItemBuilder(Material.NOTE_BLOCK).setName("§7» §6Son de kill")
                .onClick(event -> menuManager.open(new KillSoundGui(game, gamePlayer, this))));

        insertInteractItem(inventory, 31, new ItemBuilder(Material.STAINED_CLAY).setName("§7» §6Blocs")
                .onClick(event -> menuManager.open(new BlockGui(game, gamePlayer, this))));

        insertInteractItem(inventory, 32, new ItemBuilder(Material.DIAMOND_SWORD).setName("§7» §6Épée").flag(ItemFlag.HIDE_ATTRIBUTES)
                .onClick(event -> menuManager.open(new SwordGui(game, gamePlayer, this))));

        insertInteractItem(inventory, 49, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§c→ Fermer le menu")
                .onClick(event -> gamePlayer.getPlayer().closeInventory()));
    }

}