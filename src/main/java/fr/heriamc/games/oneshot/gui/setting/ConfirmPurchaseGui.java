package fr.heriamc.games.oneshot.gui.setting;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.menu.confirm.ConfirmMenu;
import fr.heriamc.games.oneshot.cosmetic.Cosmetic;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import fr.heriamc.games.oneshot.setting.message.OneShotMessages;
import org.bukkit.DyeColor;
import org.bukkit.inventory.Inventory;

public class ConfirmPurchaseGui extends ConfirmMenu {

    public ConfirmPurchaseGui(OneShotPlayer gamePlayer, HeriaMenu before, Cosmetic cosmetic) {
        super(gamePlayer.getPlayer(), "Confirmer l'achat", HeriaBukkit.get(), before, player -> {
            player.closeInventory();
            cosmetic.buy(gamePlayer);
            player.sendMessage(OneShotMessages.SHOP_SUCCESSFUL_PURCHASE.getMessage(cosmetic.getName()));
        });
    }

    @Override
    public void inventory(Inventory inventory) {
        setBorder(inventory, DyeColor.ORANGE.getWoolData());
    }

}