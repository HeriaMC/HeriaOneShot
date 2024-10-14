package fr.heriamc.games.oneshot.listener;

import fr.heriamc.games.api.pool.GameManager;
import fr.heriamc.games.engine.utils.concurrent.VirtualThreading;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import fr.heriamc.games.oneshot.player.OneShotPlayerState;
import fr.heriamc.games.oneshot.setting.message.OneShotKillStreakMessage;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.EnumSet;

public record PlayerDamageListener(GameManager<OneShotGame> gameManager) implements Listener {

    // NEED TO BE MOVED AWAY
    private static final EnumSet<EntityDamageEvent.DamageCause> damageCauses = EnumSet.of(
            EntityDamageEvent.DamageCause.VOID,
            EntityDamageEvent.DamageCause.FALL,
            EntityDamageEvent.DamageCause.SUFFOCATION,
            EntityDamageEvent.DamageCause.FALLING_BLOCK,
            EntityDamageEvent.DamageCause.LAVA,
            EntityDamageEvent.DamageCause.FIRE,
            EntityDamageEvent.DamageCause.FIRE_TICK,
            EntityDamageEvent.DamageCause.DROWNING,
            EntityDamageEvent.DamageCause.BLOCK_EXPLOSION,
            EntityDamageEvent.DamageCause.ENTITY_EXPLOSION,
            EntityDamageEvent.DamageCause.LIGHTNING,
            EntityDamageEvent.DamageCause.MAGIC,
            EntityDamageEvent.DamageCause.POISON,
            EntityDamageEvent.DamageCause.WITHER
    );

    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player victim) {
            var game = gameManager.getNullableGame(victim);

            if (game == null) return;

            var gamePlayer = game.getNullablePlayer(victim);

            if (gamePlayer == null) return;

            if (event.getDamager() instanceof Player attacker) {
                var weapon = attacker.getItemInHand();

                if (weapon == null
                        || !game.containsPlayer(attacker)
                        || !weapon.getType().name().contains("_SWORD")) return;

                if (weapon.getType().name().contains("_PICKAXE")) event.setDamage(1.0);

               //if (!weapon.getType().name().contains("_SWORD")) return;

                event.setDamage(EntityDamageEvent.DamageModifier.BASE, (victim.getMaxHealth() / 3.0) + 0.1);

                if (victim.getHealth() - event.getDamage() <= 0) {
                    handleDeath(game, gamePlayer.getLastAttacker(), gamePlayer);
                    event.setCancelled(true);
                }

                return;
            }

            if (event.getDamager() instanceof Projectile
                    && event.getDamager() instanceof Arrow arrow
                    && arrow.getShooter() instanceof Player shooterPlayer) {

                event.setCancelled(true);

                var shooter = game.getNullablePlayer(shooterPlayer);

                if (shooter == null || shooter.equals(gamePlayer)) return;

                gamePlayer.setLastAttacker(shooter);

                event.setDamage(victim.getMaxHealth());
                shooter.getInventory().remove(Material.ARROW);

                handleDeathByArrow(game, shooter, gamePlayer);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            var game = gameManager.getNullableGame(player);

            if (game == null) return;

            var gamePlayer = game.getNullablePlayer(player);

            if (gamePlayer == null) return;

            if (gamePlayer.getPlayerState() == OneShotPlayerState.IN_LOBBY)
                event.setCancelled(true);

            if (damageCauses.contains(event.getCause())) {
                event.setCancelled(true);
                return;
            }

            if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                onEntityDamage(entityDamageByEntityEvent);
                return;
            }

            System.out.println("hm #3");

            if (!event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && player.getHealth() - event.getDamage() <= 0) {
                System.out.println("hm ! #1");
                event.setCancelled(true);
                handleDeath(game, gamePlayer.getLastAttacker(), gamePlayer);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getDrops().clear();
        event.setDroppedExp(0);
    }

    private void handleDeathByArrow(OneShotGame game, OneShotPlayer shooter, OneShotPlayer victim) {
        var distance = shooter.getLocation().distance(victim.getLocation());

        // USE AN ENUM FOR THAT
        sendActionBar(game, shooter, victim, " §7(Distance: §b" + String.format("%.2f", distance) + "§7)");

        shooter.onKill();
        victim.onDeath();

        OneShotKillStreakMessage.sendMessage(game, shooter.getKillStreak(), shooter);
        game.getMessage().sendDistanceMessage((int) distance, shooter);

        victim.getCraftPlayer().getHandle().getDataWatcher().watch(9, (byte) 0);
        game.getLobby().setupPlayer(victim);
    }

    private void handleDeath(OneShotGame game, OneShotPlayer attacker, OneShotPlayer victim) {
        if (attacker != null) {
            sendActionBar(game, attacker, victim, "");
            attacker.onKill();
            OneShotKillStreakMessage.sendMessage(game, attacker.getKillStreak(), attacker);
        }

        victim.onDeath();
        game.getLobby().setupPlayer(victim);
    }

    private void sendActionBar(OneShotGame game, OneShotPlayer attacker, OneShotPlayer victim, String distance) {
        VirtualThreading.runAsync(() -> {
            // USE AN ENUM FOR THAT
            attacker.sendActionBar("§7§l➼ §7Tu as tué §c" + victim.getName() + " §7!" + distance);
            victim.sendActionBar("§7§l➼ §7Vous avez été tué par §c" + attacker.getName() + " §7!" + distance);

            game.getPlayers().values().stream()
                    .filter(gamePlayer -> !gamePlayer.equals(victim) && !gamePlayer.equals(attacker))
                    .forEach(gamePlayer -> gamePlayer.sendActionBar("§7➼ §c" + attacker.getName() + " §7a tué §c" + victim.getName() + " §7!" + distance));
        });
    }

}