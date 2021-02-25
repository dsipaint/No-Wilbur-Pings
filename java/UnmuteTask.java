
public class UnmuteTask implements Runnable
{
	private String userid;
	
	public UnmuteTask(String userid)
	{
		this.userid = userid;
	}
	
	public void run()
	{
		Main.jda.getGuildById("565623426501443584").removeRoleFromMember(userid, PingListener.muted).queue();
	}
	
	public String getUserId()
	{
		return userid;
	}
}
