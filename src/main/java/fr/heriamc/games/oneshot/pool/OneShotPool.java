package fr.heriamc.games.oneshot.pool;

import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.games.api.DirectConnectStrategy;
import fr.heriamc.games.api.pool.GamePool;
import fr.heriamc.games.oneshot.OneShotGame;

import java.util.function.Supplier;

public class OneShotPool extends GamePool<OneShotGame> {

    public OneShotPool() {
        super(OneShotGame.class, "OneShot Pool", HeriaServerType.ONESHOT, 1, 5, DirectConnectStrategy.FILL_GAME);
    }

    @Override
    public Supplier<OneShotGame> newGame() {
        return OneShotGame::new;
    }

}