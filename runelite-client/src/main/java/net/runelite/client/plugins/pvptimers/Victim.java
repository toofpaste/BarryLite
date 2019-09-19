package net.runelite.client.plugins.pvptimers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import net.runelite.api.Actor;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.infobox.Timer;

class Victim
{
	private FreezeTimerPlugin plugin;
	@Getter private Actor actor;
	@Getter private WorldPoint lastPoint;
	@Getter private List<VictimPvpTimer> victimPvpTimers = new ArrayList<>();

	Victim(Actor actor, PvpTimer pvpTimer, FreezeTimerPlugin plugin)
	{
		this.actor = actor;
		this.plugin = plugin;
		this.lastPoint = actor.getWorldLocation();
		victimPvpTimers.add(new VictimPvpTimer(plugin, pvpTimer));
	}

	void addVictimPvpTimer(PvpTimer pvpTimer)
	{
		victimPvpTimers.add(new VictimPvpTimer(plugin, pvpTimer));
		sort();
	}

	/**
	 * Removes the timers if the are finished.
	 */
	void checkTimers()
	{
		Iterator<VictimPvpTimer> it = this.victimPvpTimers.iterator();

		// removeIf Java 8 better to do but i don't know lambdas very well
		while (it.hasNext())
		{
			VictimPvpTimer victimPvpTimer = it.next();
			Timer timer = victimPvpTimer.getTimer();

			if (Instant.now().compareTo(timer.getEndTime().plusMillis(victimPvpTimer.getPvpTimer().getImmunity())) >= 0)
			{
				it.remove();
			}
		}

		sort();
	}

	void removeFreezeTimers()
	{
		this.victimPvpTimers.removeIf(victimPvpTimer -> victimPvpTimer.getPvpTimer().getType() == TimerType.FREEZE);
		sort();
	}

	void removeVengeActive()
	{
		this.victimPvpTimers.removeIf(victimPvpTimer -> victimPvpTimer.getPvpTimer().getType() == TimerType.VENGEANCE_ACTIVE);
		sort();
	}

	void removeAllTimers()
	{
		this.victimPvpTimers.clear();
	}

	private void sort()
	{
		this.victimPvpTimers.sort(new Comparator<VictimPvpTimer>()
		{
			@Override
			public int compare(VictimPvpTimer o1, VictimPvpTimer o2)
			{
				return o1.getPvpTimer().getType().ordinal() - o2.getPvpTimer().getType().ordinal();
			}
		});
	}
}
