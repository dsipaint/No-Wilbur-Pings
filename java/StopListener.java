import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StopListener extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(e.getMember() != null && isStaff(e.getMember()))
		{
			String msg = e.getMessage().getContentRaw();
			String[] args = msg.split(" ");
			
			if(args.length > 1 && args[0].equalsIgnoreCase(Main.PREFIX + "disable") && args[1].equalsIgnoreCase("nowilburpings"))
			{
				e.getChannel().sendMessage("*disabling al's pinging wilbur code...*").complete();
				e.getJDA().shutdown();
				System.exit(0);
			}
			
			if(args[0].equalsIgnoreCase(Main.PREFIX + "shutdown"))
			{
				e.getJDA().shutdownNow();
				System.exit(0);
			}
		}
	}
	
	//return true if a member has discord mod, admin or is owner
		public static boolean isStaff(Member m)
		{
			//if owner
			if(m.isOwner())
				return true;
			
			//if admin
			if(m.hasPermission(Permission.ADMINISTRATOR))
				return true;
			
			for(Role r : m.getRoles())
			{
				if(r.getId().equals("565626094917648386") || r.getId().equals("751506678683533333")) //wilbur discord mod
					return true;
			}
			
			return false;
		}
}
