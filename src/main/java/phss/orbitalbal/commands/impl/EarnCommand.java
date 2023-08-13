package phss.orbitalbal.commands.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.Command;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.validators.IsPlayerValidator;
import phss.orbitalbal.transaction.BalanceTransaction;
import phss.orbitalbal.transaction.type.TransactionType;
import phss.orbitalbal.utils.TimeUtils;
import phss.orbitalbal.utils.expirable.ExpirableList;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EarnCommand extends Command {

    ExpirableList<UUID> cooldowns;

    public EarnCommand(OrbitalBal plugin) {
        super(plugin, "earn");
        this.cooldowns = new ExpirableList<>(TimeUnit.SECONDS.toMillis(plugin.getSettings().get().getInt("Config.earnCooldown", 60)));
    }

    @Override
    public List<RequirementValidator<?>> getRequirements() {
        return Collections.singletonList(new IsPlayerValidator());
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (!checkRequirements(sender, "earn", 0, args)) return false;

        Player player = (Player) sender;
        if (cooldowns.contains(player.getUniqueId())) {
            player.sendMessage(getMessagesConfig().getMessage("earnCooldown").replace("{time}", TimeUtils.getTimeString(cooldowns.getRemainingTime(player.getUniqueId()))));
            return false;
        }

        FileConfiguration settings = getPlugin().getSettings().get();
        int minAmount = settings.getInt("Config.minEarnAmount", 1);
        int maxAmount = settings.getInt("Config.maxEarnAmount", 5);

        int depositAmount = getRandomAmount(minAmount, maxAmount);

        BalanceTransaction transaction = new BalanceTransaction.Builder(player.getUniqueId(), getPlugin())
                .setTransactionType(TransactionType.DEPOSIT)
                .setAmount(depositAmount)
                .build();
        transaction.makeTransaction();

        sender.sendMessage(getMessagesConfig().getMessage("earnSuccess").replace("{amount}", "" + depositAmount));
        cooldowns.add(player.getUniqueId());
        return true;
    }

    private int getRandomAmount(int min, int max) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(max - min + 1) + min;

        while (random < min) {
            random = randomGenerator.nextInt(max - min + 1) + min;
        }

        return random;
    }

}
