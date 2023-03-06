package mz.backwards;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import mz.lib.minecraft.bukkit.MzPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MzBackwards extends MzPlugin
{
	public static MzBackwards instance;
	{
		instance=this;
	}
	
	public static float getPlayerVersion(Player player)
	{
		try
		{
			return Float.parseFloat(ProtocolVersion.getProtocol(Via.getAPI().getPlayerVersion(player)).getName().split("/")[0].substring(2));
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
	}
	
	@Override
	public void onEnable()
	{
		MzBackwardsModule.instance.load();
	}
}
