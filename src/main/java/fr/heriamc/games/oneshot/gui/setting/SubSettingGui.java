package fr.heriamc.games.oneshot.gui.setting;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.engine.utils.gui.BaseGamePageableGui;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.cosmetic.Cosmetic;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SubSettingGui<C extends Enum<C> & Cosmetic> extends BaseGamePageableGui<OneShotGame, OneShotPlayer, C> {

    private final HeriaMenu beforeMenu;
    protected Filter currentFilter;

    private static final List<Integer> slots = Arrays.asList(20, 21, 22, 23, 24, 29, 30, 31, 32, 33);

    public SubSettingGui(OneShotGame game, OneShotPlayer gamePlayer, Class<C> enumClass, String name, int size, boolean update, HeriaMenu beforeMenu) {
        super(game, gamePlayer, name, size, update, slots, () -> List.of(enumClass.getEnumConstants()));
        this.beforeMenu = beforeMenu;
        this.currentFilter = Filter.ALL;
        // () -> List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n")
    }

    protected void insertCategoryIcon(Inventory inventory, Material material, String displayName) {
        inventory.setItem(4, new ItemBuilder(material).setName(displayName).flag(ItemFlag.HIDE_ATTRIBUTES).build());
    }

    protected void insertCloseButton(Inventory inventory, int slot) {
        insertInteractItem(inventory, slot, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§c→ Fermer le menu")
                .onClick(this::closeOrOpenBefore));
    }

    protected void insertFilterButton(Inventory inventory, int slot) {
        var nextFilter = currentFilter.getNext();
        var previousFilter = currentFilter.getPrevious();

        List<String> lore = new ArrayList<>(4);

        lore.add(" ");

        for (Filter filter : Filter.values())
            lore.add((filter == currentFilter ? "§e" : "§7") + "■ Statut: " + filter.getDisplayName());

        insertInteractItem(inventory, slot, new ItemBuilder(Material.HOPPER).setName("§7» §6Filtrer")
                .setLoreWithList(lore)

                /*.setLoreWithList(
                        " ",
                        " §7▲ Statut: " + nextFilter.getDisplayName(),
                        " §e■ Statut: " + currentFilter.getDisplayName(),
                        " §7▼ Statut: " + previousFilter.getDisplayName())*/
                .onClick(event -> {
                    this.currentFilter = event.isLeftClick() ? nextFilter : previousFilter;
                    updateMenu();
                }));
    }

    protected void closeOrOpenBefore(InventoryClickEvent event) {
        if (beforeMenu == null) gamePlayer.getPlayer().closeInventory();
        else HeriaBukkit.get().getMenuManager().open(beforeMenu);
    }

    @Getter
    @AllArgsConstructor
    protected enum Filter {

        ALL ("Tout"),
        OWNED ("Posséder"),
        NOT_OWNED ("Non posséder");

        private final String displayName;

        public Filter getPrevious() {
            return values()[(ordinal() - 1  + values().length) % values().length];
        }

        public Filter getNext() {
            return values()[(ordinal() + 1) % values().length];
        }

    }

}