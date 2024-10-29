package fr.heriamc.games.oneshot.gui.setting;

import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.engine.utils.gui.BaseGameGui;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import fr.heriamc.games.oneshot.setting.message.OneShotMessages;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class SettingGui extends BaseGameGui<OneShotGame, OneShotPlayer> {

    public SettingGui(OneShotGame game, OneShotPlayer gamePlayer) {
        super(game, gamePlayer, "Paramètres", 54, false);
    }

    @Override
    public void contents(Inventory inventory) {
        setBorder(inventory, DyeColor.YELLOW.getWoolData());

        inventory.setItem(4, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§cParamètres").build());

        insertResetStatsButton(inventory, 100, 21);
        insertTimeChangerButton(inventory, 22);
        insertEditKitButton(inventory, 23);
    }

    private void insertEditKitButton(Inventory inventory, int slot) {
        insertInteractItem(inventory, slot, new ItemBuilder(Material.DIAMOND_SWORD).setName("§7» §6Éditer votre kit")
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .setLoreWithList(
                        " ",
                        "§7Vous pouvez choisir l'emplacement",
                        "§7de vos items lors de votre",
                        "§7prochaine apparition",
                        "",
                        "§6§l❱ §eClique pour éditer")
                .onClick(event -> event.getWhoClicked().sendMessage(OneShotMessages.UNAVAILABLE_FUNCTIONALITY.getMessage())));
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