package fr.heriamc.games.oneshot.command;

import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.games.api.pool.GameManager;
import fr.heriamc.games.oneshot.OneShotGame;

public record DebugCommand(GameManager<OneShotGame> gameManager) {

    /*
        COINS
     */

    @HeriaCommand(name = "setCoins")
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

    @HeriaCommand(name = "addCoins")
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

    @HeriaCommand(name = "removeCoins")
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

    @HeriaCommand(name = "showCosmetics", inGameOnly = true)
    public void showCosmetics(CommandArgs commandArgs) {
        var player = commandArgs.getPlayer();
        var game = gameManager.getNullableGame(player);

        if (game == null) return;

        var gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;
    }

}