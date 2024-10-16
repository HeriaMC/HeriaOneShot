package fr.heriamc.games.oneshot.cosmetic;

import fr.heriamc.games.oneshot.cosmetic.block.BlockCosmetics;
import fr.heriamc.games.oneshot.cosmetic.kill.KillCosmetics;
import fr.heriamc.games.oneshot.cosmetic.sound.SoundCosmetics;
import fr.heriamc.games.oneshot.cosmetic.sword.SwordCosmetics;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CosmeticType {

    BLOCK (BlockCosmetics.class, "blocks"),
    KILL_EFFECT (KillCosmetics.class, "effects"),
    SOUND_EFFECT (SoundCosmetics.class, "sounds"),
    SWORD (SwordCosmetics.class, "swords");

    private final Class<? extends Enum<? extends Cosmetic>> cosmeticClass;
    private final String id;

    public static final CosmeticType[] types = values();
    private static final Stream<CosmeticType> typeStream = Stream.of(values());

    public static CosmeticType fromId(String id) {
        return typeStream.filter(type -> type.getId().equals(id)).findAny().orElse(null);
    }

}