package fr.heriamc.games.oneshot;

import fr.heriamc.games.engine.SimpleGame;
import fr.heriamc.games.engine.utils.GameSizeTemplate;
import fr.heriamc.games.oneshot.lobby.OneShotLobby;
import fr.heriamc.games.oneshot.setting.OneShotMapManager;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import fr.heriamc.games.oneshot.player.OneShotPlayerState;
import fr.heriamc.games.oneshot.setting.OneShotGameSettings;
import fr.heriamc.games.oneshot.setting.message.OneShotMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OneShotGame extends SimpleGame<OneShotPlayer, OneShotGameSettings> {

    // TO BE CHANGED
    private final OneShotLobby lobby;

    private final OneShotMessage message;

    public OneShotGame() {
        super("oneshot", new OneShotGameSettings(GameSizeTemplate.FFA.toGameSize()));
        this.settings.setGameMapManager(new OneShotMapManager(this));
        this.lobby = new OneShotLobby(this);
        this.message = new OneShotMessage(this);
    }

    @Override
    public void load() {
        settings.getGameMapManager().setup();
        message.setup();
    }

    public void play(OneShotPlayer gamePlayer) {
        gamePlayer.setPlayerState(OneShotPlayerState.IN_GAME);
        gamePlayer.cleanUp();
        gamePlayer.getKit().giveKit();

        getSettings().getGameMapManager().getSpawnPoints().randomTeleport(gamePlayer);
    }

    @Override
    public OneShotPlayer defaultGamePlayer(UUID uuid, boolean spectator) {
        return new OneShotPlayer(uuid, 0, 0, 0, 0, 0, spectator);
    }

}