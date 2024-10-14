package fr.heriamc.games.oneshot.player.data;

import com.google.gson.annotations.SerializedName;
import fr.heriamc.api.data.SerializableData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OneShotPlayerData implements SerializableData<UUID> {

    @SerializedName("id")
    private UUID identifier;

    private int kills, deaths, killStreak, bestKillStreak;
    private double points;

    public OneShotPlayerData updateKills(int kills) {
        this.kills += kills;
        return this;
    }

    public OneShotPlayerData updateDeaths(int deaths) {
        this.deaths += deaths;
        return this;
    }

    public OneShotPlayerData updateKillStreak(int killStreak) {
        this.killStreak += killStreak;
        return this;
    }

    public OneShotPlayerData updateBestKillStreak(int bestKillStreak) {
        this.bestKillStreak += bestKillStreak;
        return this;
    }

    public OneShotPlayerData updatePoints(double points) {
        this.points += points;
        return this;
    }

}