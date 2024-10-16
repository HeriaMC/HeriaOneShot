package fr.heriamc.games.oneshot.player.data;

import com.google.gson.annotations.SerializedName;
import fr.heriamc.api.data.SerializableData;
import fr.heriamc.games.oneshot.cosmetic.Cosmetic;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class OneShotPlayerData implements SerializableData<UUID> {

    @SerializedName("id")
    private UUID identifier;

    private int kills, deaths, killStreak, bestKillStreak;
    private double points;
    private Map<CosmeticType, String> selectedCosmetics;

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

    public OneShotPlayerData updateSelectedCosmetics(Map<CosmeticType, Cosmetic> selectedCosmetics) {
        this.selectedCosmetics = selectedCosmetics.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getId()));
        return this;
    }

}