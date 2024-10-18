package fr.heriamc.games.oneshot.listener;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.games.engine.event.player.GamePlayerLeaveEvent;
import fr.heriamc.games.engine.utils.concurrent.VirtualThreading;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.data.OneShotDataManager;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public record PlayerConnectionListener(HeriaAPI heriaAPI, OneShotDataManager dataManager) implements Listener {

    @EventHandler
    public void onGamePlayerLeave(GamePlayerLeaveEvent<OneShotGame, OneShotPlayer> event) {
        var gamePlayer = event.getGamePlayer();

        savePlayerData(gamePlayer);
    }

    private void savePlayerData(OneShotPlayer gamePlayer) {
        VirtualThreading.execute(() -> {
            System.out.println("hello from" + Thread.currentThread().getName());
            var gamePlayerData = dataManager.createOrLoad(gamePlayer.getUuid());

            gamePlayerData.updateStats(gamePlayer);
            heriaAPI.getUnlockableManager().save(gamePlayer.getUnlockedCosmetics());
            dataManager.save(gamePlayerData);
            //dataManager.saveInPersistant(gamePlayerData);
        });
    }

}