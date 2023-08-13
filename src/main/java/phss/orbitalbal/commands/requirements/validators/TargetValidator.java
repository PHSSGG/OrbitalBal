package phss.orbitalbal.commands.requirements.validators;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.RequirementValidatorResponse;

public class TargetValidator extends RequirementValidator<OfflinePlayer> {

    int TARGET_ARG_POSITION = 0;

    @Override
    public RequirementValidatorResponse<OfflinePlayer> validate(OrbitalBal plugin, CommandSender sender, String[] args) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[TARGET_ARG_POSITION]);

        if (target == null || !target.hasPlayedBefore()) {
            getResponse().setErrorMessage("targetNotFound");
        } else {
            getResponse().setResult(target);
        }

        return getResponse();
    }

}
