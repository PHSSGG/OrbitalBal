package phss.orbitalbal.commands.requirements.validators;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.RequirementValidatorResponse;

public class IsPlayerValidator extends RequirementValidator<Player> {

    @Override
    public RequirementValidatorResponse<Player> validate(OrbitalBal plugin, CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            getResponse().setResult((Player) sender);
        } else {
            getResponse().setErrorMessage("onlyInGame");
        }

        return getResponse();
    }

}
