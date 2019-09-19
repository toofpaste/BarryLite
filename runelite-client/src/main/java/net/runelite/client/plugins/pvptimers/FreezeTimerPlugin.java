package net.runelite.client.plugins.pvptimers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import org.apache.commons.lang3.ArrayUtils;

@PluginDescriptor(
                    name = "(G) Pvp Timers",
                    description = "Shows pvp timers of nearby players",
                    tags = {"Freeze", "Timer", "Opponent", "PvP", "Gengar",
                            "Barrage", "Blitz", "Rush", "Entangle", "Snare", "Bind",
							"tb", "Teleblock", "Vengeance"}
                 )
public class FreezeTimerPlugin extends Plugin
{
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private FreezeTimerOverlay overlay;

	@Inject
	private Client client;

    @Getter
	private HashMap<String, Victim> victimHashMap = new HashMap<>();
	private static final int[] GRAPHICS = {GraphicID.ICE_BARRAGE, GraphicID.ICE_BLITZ, GraphicID.ICE_BURST, GraphicID.ICE_RUSH,
											GraphicID.ENTANGLE, GraphicID.SNARE, GraphicID.BIND,  GraphicID.VENGEANCE};

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
    }

	@Subscribe
	public void onGameTick(GameTick event)
	{
		// shouldRemoveVictim
		Iterator<Victim> it = victimHashMap.values().iterator();

		while(it.hasNext())
		{
			Victim victim = it.next();
			victim.checkTimers();

			if (isVictimUnfrozen(victim))
			{
				victim.removeFreezeTimers();
			}

			if (victim.getVictimPvpTimers().isEmpty() || isVictimDead(victim))
			{
				it.remove();
			}
		}

		// I think if too many players, sprites starts to fuck up. Maybe lower list
		List<Player> players = client.getPlayers();

		for (Player player : players)
		{
			if (client.getLocalPlayer().equals(player))
				continue;

			// The victim got hit with spell that adds a timer
			if (ArrayUtils.contains(GRAPHICS, player.getGraphic()))
			{
				// Send to method to determine if it should be added to the map
				checkExistanceInMap(player);
			}
		}
	}

	@Subscribe
	public void onPlayerDespawned(PlayerDespawned event)
	{
		final String name = event.getActor().getName();

		if (victimHashMap.containsKey(name))
		{
			victimHashMap.get(name).removeAllTimers();
		}
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied event)
	{
		Actor victimActor = event.getActor();
		String victimName = victimActor.getName();

		if (victimHashMap.containsKey(victimName))
		{
			Hitsplat hitsplat = event.getHitsplat();
			Actor opponent = victimActor.getInteracting();
			String overheadText = opponent == null ? null : opponent.getOverheadText();

			if (hitsplat.getHitsplatType() == Hitsplat.HitsplatType.DAMAGE  &&
				(overheadText == null || !overheadText.equals("Taste vengeance!")))
			{
				Victim victim = victimHashMap.get(victimName);
				// Removes if any exist. I think checkiung if it has one is just as CPU heavy so best to use this.
				victim.removeVengeActive();
			}
		}
	}

	/**
	 * Movement determines if player is unfrozen. Mithril seeds does not trigger this.
	 *
	 * @param victim
	 * @return
	 */
	private boolean isVictimUnfrozen(Victim victim)
	{
		WorldPoint currentWorldPoint = victim.getActor().getWorldLocation();
		return !currentWorldPoint.equals(victim.getLastPoint());
	}

	private boolean isVictimDead(Victim victim)
	{
		return victim != null && (victim.getActor().getAnimation() == 836 || victim.getActor().getHealthRatio() == 0);
	}

	/**
	 * Adds {@code Victim's} to the hashmap if they are already not on it.
	 *
	 * If already in map, checks the current {@code Victim's} pvpTimers and if the given parameter's type does not exist
	 * in the list of pvpTimers, it will be added. In other words, all elements in the list have unique {@code TimerType}.
	 *
	 * For instance, if only a FREEZE type exists in the list and a TELEBLOCK type is given, it will be added.
	 *
	 * @param victimActor
	 */
	private void checkExistanceInMap(Actor victimActor)
	{
		PvpTimer pvpTimer = PvpTimer.getPvpTimer(victimActor.getGraphic(),
			((Player) victimActor).getOverheadIcon() == HeadIcon.MAGIC);

		if (pvpTimer != null)
		{
			Victim victim = victimHashMap.get(victimActor.getName());

			if (victim == null)
			{
				Victim newVictim = new Victim(victimActor, pvpTimer, this);

				// Add Vengeance active sprite
				if (pvpTimer.getType() == TimerType.VENGEANCE)
				{
					newVictim.addVictimPvpTimer(PvpTimer.VENGEANCE_ACTIVE);
				}

				victimHashMap.put(victimActor.getName(), newVictim);
			}
			else
			{
				boolean shouldAdd = shouldAddPvpTimer(victim, pvpTimer);

				if (shouldAdd)
				{
					victim.addVictimPvpTimer(pvpTimer);

					// Check to see if venge active sprite needs to be added
					if (pvpTimer.getType() == TimerType.VENGEANCE && shouldAddPvpTimer(victim, PvpTimer.VENGEANCE_ACTIVE))
					{
						victim.addVictimPvpTimer(PvpTimer.VENGEANCE_ACTIVE);
					}
				}
			}
		}
	}

	private boolean shouldAddPvpTimer(Victim victim, PvpTimer pvpTimer)
	{
		boolean shouldAdd = true;

		for (VictimPvpTimer victimPvpTimer : victim.getVictimPvpTimers())
		{
			if (pvpTimer.getType() == victimPvpTimer.getPvpTimer().getType())
			{
				shouldAdd = false;
			}
		}

		return shouldAdd;
	}
}


