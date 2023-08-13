package phss.orbitalbal.commands.requirements.validators;

import org.bukkit.command.CommandSender;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.RequirementValidatorResponse;

public class IsOperatorValidator extends RequirementValidator<Boolean> {

    @Override
    public RequirementValidatorResponse<Boolean> validate(OrbitalBal plugin, CommandSender sender, String[] args) {
        if (!sender.hasPermission("orbitalbal.admin")) {
            getResponse().setResult(false);
            getResponse().setErrorMessage("notHasPermission");
        } else {
            getResponse().setResult(true);
        }

        return getResponse();
    }

}
