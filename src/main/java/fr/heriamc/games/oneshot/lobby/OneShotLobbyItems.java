package fr.heriamc.games.oneshot.lobby;

import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.engine.utils.func.TriConsumer;
import fr.heriamc.games.oneshot.OneShotAddon;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.gui.setting.SettingGui;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import fr.heriamc.games.oneshot.setting.message.OneShotMessages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum OneShotLobbyItems {

    PLAY (0,
            new ItemBuilder(Material.IRON_AXE).setName("§6Jouer§8・§7Clic droit").setInfinityDurability().flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE).build(),
            (addon, game, gamePlayer) -> game.play(gamePlayer)),

    SPECTATE (1,
            new ItemBuilder(Material.FEATHER).setName("§fSpectateur§8・§7Clic droit").build(),
            (addon, game, gamePlayer) -> gamePlayer.sendMessage(OneShotMessages.UNAVAILABLE_FUNCTIONALITY.getMessage())),

    SETTINGS (7,
            new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§cParamètres§8・§7Clic droit").build(),
            (addon, game, gamePlayer) -> addon.openGui(new SettingGui(addon, game, gamePlayer))),

    LEAVE (8, new ItemBuilder(Material.BARRIER).setName("§cQuitter§8・§7Clic droit").build(),
            (addon, game, gamePlayer) -> addon.redirectToHub(gamePlayer));

    private final int slot;
    private final ItemStack itemStack;
    private final TriConsumer<OneShotAddon, OneShotGame, OneShotPlayer> consumer;

    public static List<OneShotLobbyItems> getItems() {
        return Arrays.asList(values());
    }

    public static Stream<OneShotLobbyItems> getItemsAsStream() {
        return Arrays.stream(values());
    }

    public static void giveItems(Player player) {
        getItems().forEach(lobbyItem -> lobbyItem.giveItem(player));
    }

    public static void giveItems(Collection<Player> collection) {
        collection.forEach(OneShotLobbyItems::giveItems);
    }

    public static Optional<OneShotLobbyItems> getLobbyItem(ItemStack itemStack){
        return getItemsAsStream().filter(lobbyItems -> lobbyItems.getItemStack().equals(itemStack)).findFirst();
    }

    public void giveItem(Player player) {
        player.getInventory().setItem(getSlot(), getItemStack());
    }

}