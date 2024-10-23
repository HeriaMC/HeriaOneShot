package fr.heriamc.games.oneshot.command;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.games.api.pool.GameManager;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.setting.message.OneShotMessages;

import java.util.Arrays;
import java.util.stream.Collectors;

public record DebugCommand(GameManager<OneShotGame> gameManager) {

    /*
        COINS
     */

    @HeriaCommand(name = "setCoins", power = HeriaRank.ADMIN)
    public void setCoins(CommandArgs commandArgs) {
        var player = commandArgs.getPlayer();
        var game = gameManager.getNullableGame(player);

        if (game == null) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        var args = commandArgs.getArgs();
        var arg = args[0];

        if (args.length != 1) return;
        if (arg == null || arg.isEmpty()) return;

        gamePlayer.getPoints().setWallet(Double.parseDouble(arg));
        player.sendMessage("[OneShot] you set your coins to " + arg);
    }

    @HeriaCommand(name = "addCoins", power = HeriaRank.ADMIN)
    public void addCoins(CommandArgs commandArgs) {
        var player = commandArgs.getPlayer();
        var game = gameManager.getNullableGame(player);

        if (game == null) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        var args = commandArgs.getArgs();
        var arg = args[0];

        if (args.length != 1) return;
        if (arg == null || arg.isEmpty()) return;

        gamePlayer.getPoints().add(Double.valueOf(arg));
        player.sendMessage("[OneShot] you added " + arg + " coins");
        player.sendMessage("[OneShot] you now have " + gamePlayer.getPoints().getWalletFormated());
    }

    @HeriaCommand(name = "removeCoins", power = HeriaRank.ADMIN)
    public void removeCoins(CommandArgs commandArgs) {
        var player = commandArgs.getPlayer();
        var game = gameManager.getNullableGame(player);

        if (game == null) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        var args = commandArgs.getArgs();
        var arg = args[0];

        if (args.length != 1) return;
        if (arg == null || arg.isEmpty()) return;

        gamePlayer.getPoints().remove(Double.valueOf(arg));
        player.sendMessage("[OneShot] you removed " + arg + " coins");
        player.sendMessage("[OneShot] you now have " + gamePlayer.getPoints().getWalletFormated());
    }

    /*
        COSMETICS
     */

    @HeriaCommand(name = "select", power = HeriaRank.PLAYER)
    public void selectCosmetic(CommandArgs commandArgs) {
        var player = commandArgs.getPlayer();
        var game = gameManager.getNullableGame(player);

        if (game == null) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        var args = commandArgs.getArgs();

        if (args.length != 2) return;

        var typeArg = args[0];
        var idArg = args[1];

        if (typeArg == null || typeArg.isEmpty() || idArg == null || idArg.isEmpty()) return;

        var type = CosmeticType.fromName(typeArg);

        if (type == null) {
            player.sendMessage("Type incorrect (" + Arrays.stream(CosmeticType.types).map(Enum::name).collect(Collectors.joining(", ")));
            return;
        }

        var cosmetic = CosmeticType.getCosmetic(type, idArg);

        if (cosmetic == null) {
            player.sendMessage("Cosmetic incorrect");
            return;
        }

        if (cosmetic.canSelect(gamePlayer)) {
            cosmetic.select(gamePlayer);
            player.sendMessage(OneShotMessages.SHOP_SELECT_COSMETIC.getMessage(cosmetic.getName()));
        }
    }

}