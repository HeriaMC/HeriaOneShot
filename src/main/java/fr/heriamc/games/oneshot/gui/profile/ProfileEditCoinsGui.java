package fr.heriamc.games.oneshot.gui.profile;

import fr.heriamc.api.utils.HeriaSkull;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.engine.utils.gui.BaseGameGui;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class ProfileEditCoinsGui extends BaseGameGui<OneShotGame, OneShotPlayer> {

    private final OneShotPlayer target;

    public ProfileEditCoinsGui(OneShotGame game, OneShotPlayer gamePlayer, OneShotPlayer target, HeriaMenu beforeMenu) {
        super(game, gamePlayer, beforeMenu, "name", 54, false);
        this.target = target;
    }

    public ProfileEditCoinsGui(OneShotGame game, OneShotPlayer gamePlayer, HeriaMenu beforeMenu) {
        this(game, gamePlayer, null, beforeMenu);
    }

    @Override
    public void contents(Inventory inventory) {
        setBorder(inventory, DyeColor.ORANGE.getWoolData());

        insertIncreaseButton(inventory, 21, 10);

    }

    private void insertIncreaseButton(Inventory inventory, int slot, int value) {
        insertInteractItem(inventory, slot, new ItemBuilder(Material.SKULL_ITEM)
                .setSkullURL(HeriaSkull.GREEN_PLUS.getURL())
                .onClick(event -> {}));
    }

    private void insertDecreaseButton(int slot, int value) {
    }

}