package fr.heriamc.games.oneshot.data;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.games.oneshot.player.data.OneShotPlayerData;

import java.util.UUID;

public class OneShotDataManager extends PersistentDataManager<UUID, OneShotPlayerData> {

    public OneShotDataManager(HeriaAPI heriaAPI) {
        super(heriaAPI.getRedisConnection(), heriaAPI.getMongoConnection(), "oneshot", "oneshot");
    }

    @Override
    public OneShotPlayerData getDefault() {
        return new OneShotPlayerData(null, 0, 0, 0, 0, 0);
    }
    
}