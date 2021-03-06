package io.github.famous1622.NatsukiBot.listeners;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.commands.ComedyDarkCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

public class PrivateMessageListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromType(ChannelType.PRIVATE)) {
			if (event.getMessage().getContentDisplay().equalsIgnoreCase("I understand the rules of the comedy-dark channel")) {
				if (ComedyDarkCommand.cdusers.contains(event.getAuthor())) {
					event.getMessage().addReaction("👌").queue();
					Member member = Main.guild.getMember(event.getAuthor());
					Role cdrole = Main.guild.getRolesByName("comedy-dark", true).get(0);
					new GuildController(Main.guild).addSingleRoleToMember(member, cdrole).queue();
				}
			}
		}
	}
	
}
