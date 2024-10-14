package fr.heriamc.games.oneshot.cosmetic.kill;

import fr.heriamc.games.oneshot.cosmetic.Cosmetic;
import fr.heriamc.games.oneshot.player.OneShotPlayer;

public interface KillCosmetic extends Cosmetic {

    void play(OneShotPlayer gamePlayer);

}