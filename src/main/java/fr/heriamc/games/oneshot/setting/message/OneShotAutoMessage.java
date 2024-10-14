package fr.heriamc.games.oneshot.setting.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OneShotAutoMessage {

    NO_ALLIANCE ("§c§lRappel: Les alliances sont strictements interdites !"),
    NO_TOWER ("§c§lRappel: Les towers sont strictements interdites !"),
    CPS ("§c§lRappel: Vous n'avez pas le droit de dépassez les 20 CPS.");

    private final String message;

    public OneShotAutoMessage nextMessage() {
        return OneShotAutoMessage.values()[(this.ordinal() + 1) % OneShotAutoMessage.values().length];
    }

}