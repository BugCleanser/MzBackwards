package mz.backwards;

import mz.lib.minecraft.bukkit.LangUtil;
import mz.lib.minecraft.bukkit.event.SetItemEvent;
import mz.lib.minecraft.bukkit.event.ShowItemEvent;
import mz.lib.minecraft.bukkit.itemstack.ItemStackBuilder;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.nothing.*;
import mz.lib.minecraft.bukkit.wrappednms.NmsNBTTagByte;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MzBackwardsModule extends AbsModule
{
	public static MzBackwardsModule instance=new MzBackwardsModule();
	public MzBackwardsModule()
	{
		super(MzBackwards.instance);
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onShowItem(ShowItemEvent event)
	{
		if(event.player!=null&&MzBackwards.getPlayerVersion(event.player)<BukkitWrapper.version)
		{
			if(!event.item.hasName())
			{
				event.item.setName("§f"+LangUtil.getTranslated(event.player,ItemStackBuilder.getTranslateKey(event.item.get())));
				event.item.tag().set("MzBackwards",NmsNBTTagByte.newInstance((byte)1));
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onSetItem(SetItemEvent event)
	{
		if(event.item.hasTag()&&event.item.tag().containsKey("MzBackwards"))
		{
			event.item.display().remove("Name");
			event.item.tag().remove("MzBackwards");
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		if(BukkitWrapper.version>=13.1&&MzBackwards.getPlayerVersion(event.getPlayer())<13.1)
		{
			Bukkit.getScheduler().runTask(getPlugin(),()->
			{
				event.getPlayer().updateInventory();
			});
		}
	}
}
