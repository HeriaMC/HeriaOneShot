package fr.heriamc.games.oneshot.lobby;

import fr.heriamc.games.engine.event.GameListener;
import fr.heriamc.games.engine.utils.concurrent.VirtualThreading;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import fr.heriamc.games.oneshot.player.OneShotPlayerState;
import fr.heriamc.games.oneshot.setting.message.OneShotMessages;
import org.bukkit.entity.Player;

public class OneShotLobby extends GameListener<OneShotGame> {

    /*
        REMADE THIS !!!
     */
    public OneShotLobby(OneShotGame game) {
        super(game);
    }

    public void setupPlayer(OneShotPlayer gamePlayer) {
        Player player = gamePlayer.getPlayer();

        gamePlayer.setPlayerState(OneShotPlayerState.IN_LOBBY);
        gamePlayer.cleanUp();

        OneShotLobbyItems.giveItems(player);
        getGame().getSettings().getGameMapManager().getArenaMap().getSpawn().teleport(player);
    }

    public void sendWelcomeTitle(OneShotPlayer gamePlayer) {
        VirtualThreading.execute(() ->
                OneShotMessages.WELCOME_TITLE.sendAsTitle(gamePlayer , 20, 30, 20));
    }

}