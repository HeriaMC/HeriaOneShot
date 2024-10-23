package fr.heriamc.games.oneshot.cosmetic.kill;

import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.Location;

public interface KillEffectTask {

    void run(OneShotPlayer attacker, Location location);

}