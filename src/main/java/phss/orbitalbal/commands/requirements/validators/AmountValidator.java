package phss.orbitalbal.commands.requirements.validators;

import org.bukkit.command.CommandSender;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.RequirementValidatorResponse;

public class AmountValidator extends RequirementValidator<Double> {

    int BALANCE_ARG_POSITION = 1;

    @Override
    public RequirementValidatorResponse<Double> validate(OrbitalBal plugin, CommandSender sender, String[] args) {
        try {
            double amount = Double.parseDouble(args[BALANCE_ARG_POSITION]);
            getResponse().setResult(amount);
        } catch (NumberFormatException ignored) {
            getResponse().setErrorMessage("onlyNumbers");
        }

        return getResponse();
    }

}
