package fr.heriamc.games.oneshot.setting.message;

import fr.heriamc.games.engine.utils.CollectionUtils;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum OneShotKillStreakMessage {

    DOUBLE_KILL (2,
            "§7❱❱ §c%player_name% §7explose tout avec un §c§ldouble kill §7!",
            "§7❱❱ §c%player_name% §7laisse un sillage de destruction, §c§ldouble kill §7!"),

    TRIPLE_KILL (3,
            "§7❱❱ §c%player_name% §7est en feu avec un §c§ltriplé §7!",
            "§7❱❱ §c%player_name% §7enchaîne avec un §c§ltriplé §7!",
            "§7❱❱ §c%player_name% §7fait trembler l’arène, §c§ltriplé §7!",
            "§7❱❱ §c%player_name% §7déclenche une tempête de kills, §c§ltriplé §7!"),

    QUADRA_KILL (4, "§7❱❱ §c%player_name% §7fait un massacre, §c§lquadruplé §7!",
            "§7❱❱ §c%player_name% §7règne sur le champ de bataille, §c§lquadruplé §7!"),

    PENTA_KILL (5,
            "§7❱❱ §c%player_name% §7a réussi un §c§lquintuplé §7!",
            "§7❱❱ §c%player_name% §7fait un carnage ! §c§l5 ÉLIMINATIONS",
            "§7❱❱ §c%player_name% §7est imparable, §c§lquintuple élimination §7!",
            "§7❱❱ §c%player_name% §7est une machine à tuer, §c§lquintuplé §7!",
            "§7❱❱ §c%player_name% §7est une §6légende§7, §c§lquintuple élimination §7!",
            "§7❱❱ §c%player_name% §7ne laisse personne debout, §c§lquintuplé §7!");

    private final int kills;
    private final String[] messages;

    OneShotKillStreakMessage(int kills, String... messages) {
        this.kills = kills;
        this.messages = Arrays.copyOf(messages, messages.length);
    }

    public static Optional<String> getMessage(int kills, OneShotPlayer gamePlayer) {
        return getByKills(kills)
                .map(OneShotKillStreakMessage::getMessage)
                .map(string -> string.replace("%player_name%", gamePlayer.getName()));
    }

    public static void sendMessage(OneShotGame game, int kills, OneShotPlayer gamePlayer) {
        getMessage(kills, gamePlayer).ifPresent(game::broadcast);
    }

    public String getMessage() {
        return CollectionUtils.oldRandom(messages).orElseThrow();
    }

    public static Optional<OneShotKillStreakMessage> getByKills(int kills) {
        return Arrays.stream(values())
                .filter(killStreak -> killStreak.getKills() == kills).findFirst();
    }

}