package net.runelite.client.plugins.pvptimers;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import lombok.Getter;
import net.runelite.client.ui.overlay.infobox.Timer;
import org.apache.commons.lang3.ArrayUtils;

final class VictimPvpTimer
{
	private FreezeTimerPlugin plugin;
	@Getter private PvpTimer pvpTimer;
	@Getter private Timer timer;

	VictimPvpTimer(FreezeTimerPlugin plugin, PvpTimer pvpTimer)
	{
		this.plugin = plugin;
		this.pvpTimer = pvpTimer;
		this.timer = createTimer(pvpTimer);
	}

	private Timer createTimer(PvpTimer pvpTimer)
	{
		long duration = pvpTimer.getDuration();
		return new Timer(duration, ChronoUnit.MILLIS, null, plugin);
	}
}
