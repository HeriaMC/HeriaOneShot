package fr.heriamc.games.oneshot.lobby;

import fr.heriamc.games.engine.ffa.lobby.FFAGameLobby;
import fr.heriamc.games.engine.utils.concurrent.VirtualThreading;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import fr.heriamc.games.oneshot.setting.message.OneShotMessages;
import org.bukkit.GameMode;

public class OneShotLobby extends FFAGameLobby<OneShotGame, OneShotPlayer, OneShotLobbyItems> {

    public OneShotLobby() {
        super(OneShotLobbyItems.class);
    }

    @Override
    protected void processJoin(OneShotGame game, OneShotPlayer gamePlayer) {
        gamePlayer.setGameMode(GameMode.ADVENTURE);
        sendWelcomeTitle(gamePlayer);
    }

    @Override
    protected void processSetup(OneShotGame game, OneShotPlayer gamePlayer) {

    }

    @Override
    protected void processPlay(OneShotGame game, OneShotPlayer gamePlayer) {
        gamePlayer.setGameMode(GameMode.SURVIVAL);
        gamePlayer.cleanUp();
        gamePlayer.getKit().giveKit();

        game.getSettings().getGameMapManager().getSpawnPoints().randomTeleport(gamePlayer);
    }

    private void sendWelcomeTitle(OneShotPlayer gamePlayer) {
        VirtualThreading.execute(() ->
                OneShotMessages.WELCOME_TITLE.sendAsTitle(gamePlayer , 20, 30, 20));
    }

}