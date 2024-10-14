package fr.heriamc.games.oneshot.cosmetic.sound;

import fr.heriamc.games.oneshot.cosmetic.Cosmetic;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.Sound;

import java.util.Collection;

public interface SoundCosmetic extends Cosmetic {

    Sound getSound();

    void play(Collection<OneShotPlayer> collection);

}