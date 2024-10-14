package fr.heriamc.games.oneshot.setting.message;

import fr.heriamc.games.oneshot.player.OneShotPlayer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum OneShotMessages {

    PREFIX ("§6§lONESHOT §8┃ "),

    WELCOME_TITLE ("§e» §6§lOneShot §e«", "§fBon jeu !"),

    MAX_BUILD_HEIGHT_REACHED ("§cLa hauteur maximale a été dépassée."),

    UNAVAILABLE_FUNCTIONALITY ("§cCette fonctionnalité n'est pas encore disponible.");

    private String message;
    private String[] messages;

    OneShotMessages(String message) {
        this.message = message;
    }

    OneShotMessages(String... messages) {
        this.messages = messages;
    }

    public String getMessage() {
        return PREFIX.message + message;
    }

    public String getMessage(Object... objects) {
        return PREFIX.message + message.formatted(objects);
    }

    public String[] getMessages(Object... objects) {
        List<String> formattedMessages = new ArrayList<>(messages.length);
        var index = 0;

        for (String message : messages) {
            if (message.contains("%")) {
                formattedMessages.add(message.formatted(objects[index]));
                index++;
            } else
                formattedMessages.add(message);
        }

        return formattedMessages.toArray(String[]::new);
    }

    public void sendAsTitle(OneShotPlayer gamePlayer, int fadeIn, int stay, int fadeOut) {
        var title = messages[0];
        var subTitle = messages[1];

        gamePlayer.sendTitle(fadeIn, stay, fadeOut,
                title == null ? "" : title,
                subTitle == null ? "" : subTitle);
    }

    public String getMessageWithoutPrefix() {
        return message;
    }

}