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
import java.util.function.Predicate;

public abstract class SubSettingGui<C extends Enum<C> & Cosmetic> extends BaseGamePageableGui<OneShotGame, OneShotPlayer, C> {

    private static final List<Integer> slots = Arrays.asList(20, 21, 22, 23, 24, 29, 30, 31, 32, 33);

    private final HeriaMenu beforeMenu;
    private final Class<C> enumClass;

    protected Filter currentFilter;

    public SubSettingGui(OneShotGame game, OneShotPlayer gamePlayer, Class<C> enumClass, String name, int size, boolean update, HeriaMenu beforeMenu) {
        super(game, gamePlayer, name, size, update, slots, () -> List.of(enumClass.getEnumConstants()));
        this.beforeMenu = beforeMenu;
        this.enumClass = enumClass;
        this.currentFilter = Filter.ALL;
    }

    public abstract void setup(Inventory inventory);

    @Override
    public void inventory(Inventory inventory) {
        switch (currentFilter) {
            case ALL -> updatePagination();
            case OWNED -> updatePagination(cosmetic -> gamePlayer.getUnlockedCosmetics().isUnlocked(cosmetic.getId()));
            case NOT_OWNED -> updatePagination(cosmetic -> !gamePlayer.getUnlockedCosmetics().isUnlocked(cosmetic.getId()));
        }

        setup(inventory);
    }

    private void updatePagination() {
        getPagination().clear();
        getPagination().addAll(Arrays.asList(enumClass.getEnumConstants()));
    }

    private void updatePagination(Predicate<C> predicate) {
        getPagination().clear();
        getPagination().addAll(Arrays.stream(enumClass.getEnumConstants()).filter(predicate).toList());
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

        for (Filter filter : Filter.filters)
            lore.add((filter == currentFilter ? "§e" : "§7") + "■ Statut: " + filter.getDisplayName());

        insertInteractItem(inventory, slot, new ItemBuilder(Material.HOPPER).setName("§7» §6Filtrer")
                .setLoreWithList(lore)
                .onClick(event -> {
                    this.currentFilter = event.isLeftClick() ? nextFilter : previousFilter;
                    updateMenu();
                }));
    }

    protected void openGui(HeriaMenu heriaMenu) {
        HeriaBukkit.get().getMenuManager().open(heriaMenu);
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

        private static final Filter[] filters = values();

        public Filter getPrevious() {
            return filters[(ordinal() - 1  + filters.length) % filters.length];
        }

        public Filter getNext() {
            return filters[(ordinal() + 1) % filters.length];
        }

    }

}