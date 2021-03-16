import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingListener extends ListenerAdapter
{
	static Role muted = Main.jda.getRoleById("760950749177577524");
	static Role friends = Main.jda.getRoleById("565626487114301440");
	static TextChannel logs = Main.jda.getTextChannelById("771463923449987104");
	
	static User[] creators = {
			Main.jda.retrieveUserById("183722545181229057").complete(), //wilbur
			Main.jda.retrieveUserById("232086421794455553").complete(), //tommy
			Main.jda.retrieveUserById("287265260824559617").complete(), //tubbo
			Main.jda.retrieveUserById("141577172987936768").complete(), //kai
			Main.jda.retrieveUserById("172008491228200960").complete(), //seapeekay
			Main.jda.retrieveUserById("251865753244925952").complete(), //niki
			Main.jda.retrieveUserById("318833694385111041").complete(), //jack manifold
	};
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(!e.getGuild().getId().equals("565623426501443584"))
			return;
		
		for(User creator : creators)
		{
			//if creator is pinged
			if(e.getMessage().getMentionedUsers().contains(creator))
			{
				//if not staff
				if(!StopListener.isStaff(e.getMember()))
				{
					e.getMessage().delete().queue(); //delete message
					e.getGuild().addRoleToMember(e.getMember().getId(), muted).queue(); //give them the muted role
					Main.unmutes.schedule(new UnmuteTask(e.getMember().getId()), 2, TimeUnit.HOURS); //schedule an unmute in 2 hours
					
					//send messages
					e.getChannel().sendMessage("Please do not ping " + creator.getName() + "- you have been tempmuted for 2 hours").queue();
					logs.sendMessage(":mute: " + e.getAuthor().getAsTag() + " (" + e.getMember().getId() + ") muted by " + 
							e.getJDA().getSelfUser().getAsTag() + " for 2h, for reason \"Pinging" +  creator.getName() + "\"").queue();
				}
			}
		}
	}
}
