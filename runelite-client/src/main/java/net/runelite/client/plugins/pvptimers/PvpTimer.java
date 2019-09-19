package net.runelite.client.plugins.pvptimers;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.GraphicID;
import net.runelite.api.SpriteID;
import net.runelite.client.ui.overlay.infobox.Timer;
import org.apache.commons.lang3.ArrayUtils;

// All PvpTimer are perfectly designed for off-pid. When you don't have pid, you must wait another tick to get the
// refreeze (i.e. For snare, you have to wait four ticks after freeze has finished to refreeze, but only three with pid).
// They are desinged for off-pid so that if you dont try to freeze them when they are immune. If you have PID, adjust your
// refreeze time slight before timer runs out.

enum PvpTimer
{
    // Time, sprite, animation, graphic
    BARRAGE(20000, GraphicID.ICE_BARRAGE, SpriteID.SPELL_ICE_BARRAGE, TimerType.FREEZE,3000),
    BLITZ(15000, GraphicID.ICE_BLITZ, SpriteID.SPELL_ICE_BLITZ, TimerType.FREEZE,3000),
    BURST(10000,  GraphicID.ICE_BURST, SpriteID.SPELL_ICE_BURST, TimerType.FREEZE,3000),
    RUSH(5000, GraphicID.ICE_RUSH, SpriteID.SPELL_ICE_RUSH, TimerType.FREEZE,3000),
    BIND(5000,  GraphicID.BIND, SpriteID.SPELL_BIND, TimerType.FREEZE,3000),
    HALFBIND(2500, GraphicID.BIND, SpriteID.SPELL_BIND, TimerType.FREEZE,3000, true),
    SNARE(10000, GraphicID.SNARE, SpriteID.SPELL_SNARE, TimerType.FREEZE,3000),
    HALFSNARE(5000, GraphicID.SNARE, SpriteID.SPELL_SNARE, TimerType.FREEZE,3000, true),
    ENTANGLE(15000, GraphicID.ENTANGLE, SpriteID.SPELL_ENTANGLE, TimerType.FREEZE,3000),
    HALFENTANGLE(7500, GraphicID.ENTANGLE, SpriteID.SPELL_ENTANGLE, TimerType.FREEZE,3000, true),
    //TELEBLOCK(300000, GraphicID.TELEBLOCK, SpriteID.SPELL_TELE_BLOCK, TimerType.TELEBLOCK,0),
	//HALFTELEBLOCK(150000, GraphicID.TELEBLOCK, SpriteID.SPELL_TELE_BLOCK, TimerType.TELEBLOCK,0, true),
    VENGEANCE_TIMER(30000, GraphicID.VENGEANCE, SpriteID.SPELL_VENGEANCE, TimerType.VENGEANCE,0),
	VENGEANCE_ACTIVE(500000, GraphicID.VENGEANCE, SpriteID.SPELL_VENGEANCE_OTHER, TimerType.VENGEANCE_ACTIVE,0);	// Duration is invalid

	private final boolean isHalved;
    @Getter private final long duration;
    @Getter private final int spriteId;
    @Getter private final long immunity;
	@Getter private final int graphicId;
	@Getter private TimerType type;
	private static final PvpTimer[] NOT_NORMALS = {BARRAGE, BLITZ, BURST, RUSH, VENGEANCE_TIMER};

    PvpTimer(long duration, int graphicId, int spriteId, TimerType type, long immunity, boolean isHalved)
    {
        this.duration = duration;
        this.graphicId = graphicId;
        this.spriteId = spriteId;
        this.type = type;
        this.immunity = immunity;
        this.isHalved = isHalved;
    }

    PvpTimer(long duration, int graphicId, int spriteId, TimerType type, long immunity)
    {
        this(duration, graphicId, spriteId, type, immunity, false);
    }

	/**
	 * Checks to see if an actor's graphic was recently a frozen graphic. Returns a freezetimer if so, null otherwise.
	 *
	 * @param graphicId
	 * @param isProtMageOn
	 * @return
	 */
	public static PvpTimer getPvpTimer(int graphicId, boolean isProtMageOn)
	{
		for (PvpTimer freezeTimer : PvpTimer.values())
		{
			if (freezeTimer.getGraphicId() == graphicId)
			{
				if (ArrayUtils.contains(NOT_NORMALS, freezeTimer))
				{
					return freezeTimer;
				}

				// Need to have a setting for on normals magic while pro mage is off
				if (isProtMageOn && freezeTimer.isHalved)
				{
					return freezeTimer;
				}
				else if (!isProtMageOn && !freezeTimer.isHalved)
				{
					return freezeTimer;
				}
			}
		}

		return null;
	}
}
