package org.inesgar.MobBountyReloaded.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.inesgar.MobBountyReloaded.MobBountyAPI;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.event.MobBountyReloadedPaymentEvent;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;
import org.inesgar.MobBountyReloaded.utils.MobBountyPlayerKillData;
import org.inesgar.MobBountyReloaded.utils.MobBountyUtils;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MobBountyReloadedEntityListener implements Listener
{

	private MobBountyReloaded mbr;

	public MobBountyReloadedEntityListener(MobBountyReloaded mbr)
	{
		setMBR(mbr);
		getMBR().getServer().getPluginManager().registerEvents(this, getMBR());
	}

	public MobBountyReloaded getMBR()
	{
		return mbr;
	}

	private void setMBR(MobBountyReloaded mbr)
	{
		this.mbr = mbr;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		if (!(event.getEntity().getKiller() instanceof Player))
		{
			return;
		}
		Player player = event.getEntity().getKiller();
		MobBountyCreature creature = MobBountyCreature.valueOf(
				event.getEntity(), "");
		if (!getMBR().getPermissionManager().hasPermission(player,
				"mbr.user.collect.normal"))
		{
			return;
		}
		if (!getMBR().getPermissionManager().hasPermission(player,
				"mbr.user.collect." + creature.getName().toLowerCase()))
		{
			return;
		}
		double amount = getMBR().getAPI().getEntityValue(player.getName(),
				creature);
		MobBountyReloadedPaymentEvent mbrpe = new MobBountyReloadedPaymentEvent(
				player.getName(), creature, amount);
		getMBR().getServer().getPluginManager().callEvent(mbrpe);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPaymentEvent(MobBountyReloadedPaymentEvent mbrpe)
	{
		if (mbrpe.isCancelled())
		{
			return;
		}
		Player player = Bukkit.getServer().getPlayer(mbrpe.getPlayerName());
		MobBountyPlayerKillData playerKillData = MobBountyAPI.playerData
				.get(player.getName());
		if (playerKillData == null)
		{
			playerKillData = new MobBountyPlayerKillData();
			MobBountyAPI.playerData.put(player.getName(), playerKillData);
		}
		double amount = mbrpe.getAmount();
		if (amount == 0.0)
		{
			return;
		}
		double mult = getMBR().getAPI().getMult(player);
		double amt = mult * amount;
		getMBR().getAPI().makeTransaction(mbrpe.getPlayerName(), mult * amount);
		String blnTest = getMBR().getConfigManager().getProperty(
				MobBountyReloadedConfFile.GENERAL, "killCache.use");
		boolean bln = Boolean.parseBoolean(blnTest);
		if (bln)
		{
			handleKillCache(player, playerKillData, amt);
		}
		else
		{
			sendNonKillCache(mbrpe, player, amount, amt);
		}
	}

	private void sendNonKillCache(MobBountyReloadedPaymentEvent mbrpe,
			Player player, double amount, double amt)
	{
		if (amount > 0.0)
		{
			sendPayMessage(mbrpe, player, amt);
		}
		else if (amount < 0.0)
		{
			sendFineMessage(mbrpe, player, amt);
		}
	}

	private void sendPayMessage(MobBountyReloadedPaymentEvent mbrpe,
			Player player, double amt)
	{
		String string = getMBR().getLocaleManager().getString("Awarded");
		if (string == null)
		{
			return;
		}
		player.sendMessage(getMBR().getAPI().formatString(
				string,
				player.getName(),
				mbrpe.getCreature().getName(),
				player.getWorld().getName(),
				amt,
				amt,
				amt,
				"",
				"",
				"mbr.user.collect."
						+ mbrpe.getCreature().getName().toLowerCase(), "", "",
				0, "", ""));
	}

	private void sendFineMessage(MobBountyReloadedPaymentEvent mbrpe,
			Player player, double amt)
	{
		String string = getMBR().getLocaleManager().getString("Fined");
		if (string == null)
		{
			return;
		}
		player.sendMessage(getMBR().getAPI().formatString(
				string,
				player.getName(),
				mbrpe.getCreature().getName(),
				player.getWorld().getName(),
				amt,
				amt,
				amt,
				"",
				"",
				"mbr.user.collect."
						+ mbrpe.getCreature().getName().toLowerCase(), "", "",
				0, "", ""));
	}

	private void handleKillCache(Player killer,
			MobBountyPlayerKillData playerData, double amount)
	{
		playerData.cacheSize++;
		if (amount >= 0.0)
			playerData.cacheEarned += amount;
		else
			playerData.cacheEarned -= amount;
		int timeLimit = MobBountyUtils.getInt(
				getMBR().getConfigManager().getProperty(
						MobBountyReloadedConfFile.GENERAL,
						"killCache.timeLimit"), 30000);
		if (System.currentTimeMillis() - playerData.cacheTime >= timeLimit)
		{
			if (playerData.cacheEarned > 0.0)
			{
				String message = getMBR().getLocaleManager().getString(
						"CacheAwarded");
				if (message != null)
				{
					message = getMBR()
							.getAPI()
							.formatString(
									message,
									killer.getName(),
									"",
									killer.getWorld().getName(),
									playerData.cacheEarned,
									playerData.cacheEarned,
									playerData.cacheEarned,
									"",
									"",
									"",
									"",
									String.valueOf(String.valueOf(Math
											.round((System.currentTimeMillis() - playerData.cacheTime) / 1000))),
									playerData.cacheSize, "", "");
					killer.sendMessage(message);
				}
			}
			else if (playerData.cacheEarned < 0.0)
			{
				String message = getMBR().getLocaleManager().getString(
						"CacheFined");
				if (message != null)
				{
					message = getMBR()
							.getAPI()
							.formatString(
									message,
									killer.getName(),
									"",
									killer.getWorld().getName(),
									playerData.cacheEarned,
									playerData.cacheEarned,
									playerData.cacheEarned,
									"",
									"",
									"",
									"",
									String.valueOf(String.valueOf(Math
											.round((System.currentTimeMillis() - playerData.cacheTime) / 1000))),
									playerData.cacheSize, "", "");
					killer.sendMessage(message);
				}
			}
		}
		MobBountyAPI.playerData.put(killer.getName(), playerData);
	}
}
