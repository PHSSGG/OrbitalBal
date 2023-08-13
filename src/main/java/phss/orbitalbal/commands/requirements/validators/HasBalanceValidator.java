package phss.orbitalbal.commands.requirements.validators;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import phss.orbitalbal.OrbitalBal;
import phss.orbitalbal.commands.requirements.RequirementValidator;
import phss.orbitalbal.commands.requirements.RequirementValidatorResponse;
import phss.orbitalbal.data.domain.PlayerBalAccount;

public class HasBalanceValidator extends RequirementValidator<Boolean> {

    int BALANCE_ARG_POSITION = 1;

    @Override
    public RequirementValidatorResponse<Boolean> validate(OrbitalBal plugin, CommandSender sender, String[] args) {
        Player player = (Player) sender;
        PlayerBalAccount account = plugin.getBalRepository().createAccount(player.getUniqueId());
        double amount = Double.parseDouble(args[BALANCE_ARG_POSITION]);

        if (account.getBalance().doubleValue() < amount) {
            getResponse().setResult(false);
            getResponse().setErrorMessage("insufficientBalance");
        } else {
            getResponse().setResult(true);
        }

        return getResponse();
    }

}
