package fr.heriamc.games.oneshot.gui.setting.sub;

import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.games.oneshot.OneShotGame;
import fr.heriamc.games.oneshot.cosmetic.CosmeticType;
import fr.heriamc.games.oneshot.cosmetic.sound.SoundCosmetics;
import fr.heriamc.games.oneshot.gui.setting.SubSettingGui;
import fr.heriamc.games.oneshot.player.OneShotPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class KillSoundGui extends SubSettingGui<SoundCosmetics> {

    public KillSoundGui(OneShotGame game, OneShotPlayer gamePlayer, HeriaMenu beforeMenu) {
        super(game, gamePlayer, SoundCosmetics.class, "Son de kill", 54, false, beforeMenu);
    }

    @Override
    public void setup(Inventory inventory) {
        setBorder(inventory, DyeColor.MAGENTA.getWoolData());
        insertCategoryIcon(inventory, Material.NOTE_BLOCK, "§7» §6Son de kill");

        insertFilterButton(inventory, 48);
        insertCloseButton(inventory, 47);
    }

    @Override
    protected ItemBuilder item(SoundCosmetics cosmetic, int i, int i1) {
        var icon = new ItemBuilder(cosmetic.getIcon());

        if (gamePlayer.hasSelected(CosmeticType.SOUND_EFFECT, cosmetic))
            icon.addEnchant(Enchantment.DAMAGE_ALL, 1).flag(ItemFlag.HIDE_ENCHANTS);

        return icon.setName(cosmetic.getName())
                .setLoreWithList(
                        " ",
                        gamePlayer.getUnlockedCosmetics().isUnlocked(cosmetic.getId()) ?
                                "§8» §7Prix: §aPosséder" : "§8» §7Prix: §6" + cosmetic.getPrice(),
                        " ",
                        gamePlayer.getUnlockedCosmetics().isUnlocked(cosmetic.getId())
                                ? "§6§l❱ §eClique pour équiper"
                                : "§6§l❱ §eClique pour acheter"
                )
                .onClick(event -> {
                    // TODO: open confirm buy gui
                });
    }

}