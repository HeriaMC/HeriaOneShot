package fr.heriamc.games.oneshot.gui.setting;

import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.engine.utils.gui.BaseGameGui;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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

        insertTimeChangerButton(inventory, 22);
        insertResetStatsButton(inventory, 100, 20);
    }

    private void insertResetStatsButton(Inventory inventory, int price, int slot) {

        insertInteractItem(inventory, slot, new ItemBuilder(Material.PAPER).setName("§7» §6Réinitialisation des statistiques")
                .setLoreWithList(
                        " ",
                        "§7Vous perdrais toutes vos §emorts§7, vos",
                        "§ckills §7et votre record de §bkill streak",
                        "§7mais vous garderez vos §6points",
                        " ",
                        "§8» §7Prix: §6" + price + " ⛃",
                        " ",
                        "§6§l❱ §eClique pour réinitialiser")
                .onClick(event -> {}));
    }

    private void insertTimeChangerButton(Inventory inventory, int slot) {
        var currentTime = gamePlayer.getTime();
        var nextTime = currentTime.getNext();
        var previousTime = currentTime.getPrevious();

        insertInteractItem(inventory, slot, new ItemBuilder(Material.WATCH).setName("§7» §6Temps")
                .setLoreWithList(
                        " ",
                        "§7Option inutile si vous avez un",
                        "§7mod pour changer le temps",
                        " ",
                        "§8▲ Statut: " + nextTime.getDisplayName(),
                        "§a■ Statut: " + currentTime.getDisplayName(),
                        "§8▼ Statut: " + previousTime.getDisplayName(),
                        " ",
                        "§6§l❱ §eClique pour changer le temps"
                )
                .onClick(event -> {
                    gamePlayer.setTime(event.isLeftClick() ? nextTime : previousTime);
                    gamePlayer.playSound(Sound.NOTE_PLING, 10f, 10f);
                    updateMenu();
                }));
    }

}