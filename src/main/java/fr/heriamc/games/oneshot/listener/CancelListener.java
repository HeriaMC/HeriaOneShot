package fr.heriamc.games.oneshot.listener;

import fr.heriamc.games.api.pool.GameManager;
import fr.heriamc.games.engine.utils.MaterialUtils;
import fr.heriamc.games.oneshot.OneShotAddon;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.lobby.OneShotLobbyItems;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public record CancelListener(OneShotAddon addon, GameManager<OneShotGame> gameManager) implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var action = event.getAction();
        var game = gameManager.getNullableGame(player);

        if (game == null || !game.containsPlayer(player)) return;

        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK) {
            var block = event.getClickedBlock();

            if (block != null && MaterialUtils.isInteractable(block.getType())) {
                event.setCancelled(true);
                return;
            }
        }

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        switch (gamePlayer.getState()) {
            case IN_LOBBY -> {
                var itemStack = event.getItem();

                if (itemStack != null && itemStack.hasItemMeta())
                    OneShotLobbyItems.getLobbyItem(itemStack)
                            .ifPresent(item -> item.getConsumer().accept(addon, game, gamePlayer));

                event.setCancelled(true);
            }
            case IN_GAME -> event.setCancelled(false);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        var player = (Player) event.getWhoClicked();
        var game = gameManager.getNullableGame(player);

        if (game == null || !game.containsPlayer(player)) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        switch (gamePlayer.getState()) {
            case IN_LOBBY -> event.setCancelled(true);
            case IN_GAME -> event.setCancelled(false);
        }
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow)
            arrow.remove();
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void unHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}