package fr.heriamc.games.oneshot;

import fr.heriamc.games.api.addon.GameAddon;
import fr.heriamc.games.oneshot.command.DebugCommand;
import fr.heriamc.games.oneshot.data.OneShotDataManager;
import fr.heriamc.games.oneshot.listener.CancelListener;
import fr.heriamc.games.oneshot.listener.PlayerBlockListener;
import fr.heriamc.games.oneshot.listener.PlayerConnectionListener;
import fr.heriamc.games.oneshot.listener.PlayerDamageListener;
import fr.heriamc.games.oneshot.pool.OneShotPool;
import lombok.Getter;

@Getter
public class OneShotAddon extends GameAddon<OneShotPool> {

    private OneShotDataManager dataManager;

    public OneShotAddon() {
        super(new OneShotPool());
    }

    @Override
    public void enable() {
        this.dataManager = new OneShotDataManager(heriaApi);

        pool.loadDefaultGames();

        registerListener(
                new CancelListener(this, pool.getGamesManager()),
                new PlayerConnectionListener(dataManager),
                new PlayerBlockListener(pool.getGamesManager()),
                new PlayerDamageListener(pool.getGamesManager())
        );

        registerCommand(new DebugCommand(pool.getGamesManager()));
    }

    @Override
    public void disable() {

    }

}