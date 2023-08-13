package phss.orbitalbal.commands.requirements;

import org.bukkit.command.CommandSender;
import phss.orbitalbal.OrbitalBal;

public abstract class RequirementValidator <T> {

    RequirementValidatorResponse<T> response = new RequirementValidatorResponse<>();

    public abstract RequirementValidatorResponse<T> validate(OrbitalBal plugin, CommandSender sender, String[] args);

    public RequirementValidatorResponse<T> getResponse() {
        return response;
    }

}