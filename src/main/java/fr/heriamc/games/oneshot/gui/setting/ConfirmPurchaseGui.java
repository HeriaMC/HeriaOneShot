package fr.heriamc.games.oneshot.gui.setting;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.menu.confirm.ConfirmMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.oneshot.cosmetic.Cosmetic;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import fr.heriamc.games.oneshot.setting.message.OneShotMessages;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class ConfirmPurchaseGui extends ConfirmMenu {

    private final Cosmetic cosmetic;
    private final OneShotPlayer gamePlayer;

    public ConfirmPurchaseGui(OneShotPlayer gamePlayer, HeriaMenu before, Cosmetic cosmetic) {
        super(gamePlayer.getPlayer(), "Confirmer l'achat", HeriaBukkit.get(), before, player -> {
            player.closeInventory();
            cosmetic.buy(gamePlayer);
            player.sendMessage(OneShotMessages.SHOP_SUCCESSFUL_PURCHASE.getMessage(cosmetic.getName()));
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 10f, 10f);
        });
        this.cosmetic = cosmetic;
        this.gamePlayer = gamePlayer;
    }

    @Override
    public void inventory(Inventory inventory) {
        setBorder(inventory, DyeColor.ORANGE.getWoolData());

        inventory.setItem(4, new ItemBuilder(cosmetic.getIcon())
                .setName(cosmetic.getName())
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .setLoreWithList(
                        " ",
                        "§8» §7Prix: §6" + cosmetic.getPrice() + " ⛃",
                        " ",
                        "§8» §7Solde actuel: §6 " + gamePlayer.getPoints().getWalletFormated(),
                        "§8» §7Nouveau Solde: §6" + (int) (gamePlayer.getPoints().getWallet() - cosmetic.getPrice()) + " ⛃",
                        " ",
                        "§6§l❱ §eConfirmer votre achat")
                .build());
    }

}