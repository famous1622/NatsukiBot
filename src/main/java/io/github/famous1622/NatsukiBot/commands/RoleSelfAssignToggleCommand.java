package io.github.famous1622.NatsukiBot.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.data.SelfAssignableRolesData;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RoleSelfAssignToggleCommand implements Command {

	@Override
	public String getCommand() {
		return "caniam";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		String arg = String.join(" ", arguments);
		Guild guild = Main.guild;
		SelfAssignableRolesData roleConfig = SelfAssignableRolesData.getConfig(guild.getJDA());
		if (roleConfig.containsKey(arg.toLowerCase())) {
			roleConfig.remove(arg.toLowerCase());
			event.getChannel().sendMessage("Removed role: "+ arg).queue((message) -> {
				message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
			});
		} else {
			List<Role> roles = guild.getRolesByName(arg, true);
			if (!roles.isEmpty()) {
				Role role = roles.get(0);			
				roleConfig.put(arg.toLowerCase(), role);
				roleConfig.saveToDisk();
				event.getChannel().sendMessage("Added role: "+role.getName()).queue((message) -> {
					message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
				});
			} else {
				event.getChannel().sendMessage("No role named " + arg).queue((message) -> {
					message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
				});
			}
		}

	}
	
	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.ADMIN;
	}

	@Override
	public String getHelpMessage() {
		return "adds or removes a role from the list allowed to be assigned with $iam";
	}

	@Override
	public boolean mustBePublic() {
		return false;
	}

}
