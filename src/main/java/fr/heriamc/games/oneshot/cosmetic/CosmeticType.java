package fr.heriamc.games.oneshot.cosmetic;

import fr.heriamc.games.oneshot.cosmetic.block.BlockCosmetics;
import fr.heriamc.games.oneshot.cosmetic.kill.KillCosmetics;
import fr.heriamc.games.oneshot.cosmetic.sound.SoundCosmetics;
import fr.heriamc.games.oneshot.cosmetic.sword.SwordCosmetics;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CosmeticType {

    BLOCK (BlockCosmetics.class),
    KILL_EFFECT (KillCosmetics.class),
    SOUND_EFFECT (SoundCosmetics.class),
    SWORD (SwordCosmetics.class);

    private final Class<? extends Enum<? extends Cosmetic>> cosmeticClass;

}