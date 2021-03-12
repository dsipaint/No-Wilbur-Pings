import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingListener extends ListenerAdapter
{
	static User wilbur = Main.jda.retrieveUserById("183722545181229057").complete();
	static Role muted = Main.jda.getRoleById("760950749177577524");
	static Role friends = Main.jda.getRoleById("565626487114301440");
	static TextChannel logs = Main.jda.getTextChannelById("771463923449987104");
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(!e.getGuild().getId().equals("565623426501443584"))
			return;
		
		//if wilbur is pinged
		if(e.getMessage().getMentionedUsers().contains(wilbur))
		{
			//if not staff
			if(!StopListener.isStaff(e.getMember()))
			{
				e.getMessage().delete().queue(); //delete message
				e.getGuild().addRoleToMember(e.getMember().getId(), muted).queue(); //give them the muted role
				Main.unmutes.schedule(new UnmuteTask(e.getMember().getId()), 2, TimeUnit.HOURS); //schedule an unmute in 2 hours
				
				//send messages
				e.getChannel().sendMessage("Please do not ping Wilbur- you have been tempmuted for 2 hours").queue();
				logs.sendMessage(":mute: " + e.getAuthor().getAsTag() + " (" + e.getMember().getId() + ") muted by " + 
						e.getJDA().getSelfUser().getAsTag() + " for 2h, for reason \"Pinging Wilbur\"").queue();
			}
		}
	}
}
