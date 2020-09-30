import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingListener extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(!e.getGuild().getId().equals("565623426501443584"))
			return;
		
		//if wilbur is pinged
		if(e.getMessage().getMentionedMembers().contains(e.getGuild().retrieveMemberById("183722545181229057").complete()))
		{
			//if not staff
			if(!StopListener.isStaff(e.getMember()))
				e.getChannel().sendMessage("Hi :) Please do not ping Wilbur, we try to keep that to a minimum in this server and would "
						+ "appreciate it if you did not do this :)").queue();
		}
	}
}
