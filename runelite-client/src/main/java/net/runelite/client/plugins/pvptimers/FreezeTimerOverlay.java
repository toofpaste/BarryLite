package net.runelite.client.plugins.pvptimers;

import com.google.inject.Inject;
import net.runelite.api.Point;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import net.runelite.client.ui.overlay.infobox.Timer;

public class FreezeTimerOverlay extends Overlay
{
    @Inject
    private SpriteManager spriteManager;
    private FreezeTimerPlugin plugin;

    @Inject
    private FreezeTimerOverlay(FreezeTimerPlugin plugin)
    {
        super(plugin);
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
    	// Iterate following over all victims.
		for (Victim victim : plugin.getVictimHashMap().values())
		{
			if (victim != null)
			{
				for (VictimPvpTimer victimPvpTimer : victim.getVictimPvpTimers())
				{
					PvpTimer pvpTimer = victimPvpTimer.getPvpTimer();
					Timer timer = victimPvpTimer.getTimer();

					String timerText = timer.getText();
					String formattedTimer = pvpTimer.getType() == TimerType.TELEBLOCK ? timerText : timerText.substring(timerText.indexOf(":") + 1);

					int ordinal = pvpTimer.getType().ordinal();
					int zOffset = getOffSet(victim, ordinal);

					BufferedImage sprite = spriteManager.getSprite(pvpTimer.getSpriteId(), 0);
					Point textLocation = victim.getActor().getCanvasTextLocation(graphics, formattedTimer, zOffset);

					// textLocation is null often, need to understand why as it despawns ongoing timers randomly
					if (sprite == null || textLocation == null)
						return null;

					final int imageWidth = sprite.getWidth();
					final int imageMargin = imageWidth / 2;

					Point imageLocation;

					if (ordinal == 3)
					{
						this.setPreferredPosition(null);
						imageLocation = new Point(textLocation.getX() - imageMargin, textLocation.getY() - sprite.getHeight() / 2);
					}
					else
					{
						final int textHeight = graphics.getFontMetrics().getHeight() - graphics.getFontMetrics().getMaxDescent();
						imageLocation = new Point(textLocation.getX() - imageMargin - 3, textLocation.getY() - textHeight / 2 - sprite.getHeight() / 2);
						textLocation = new Point(textLocation.getX() + imageMargin + 2, textLocation.getY());

						this.setPreferredPosition(null);
						FreezeOverlayUtil.renderTextLocation(graphics, textLocation, formattedTimer, Color.WHITE);
					}
					OverlayUtil.renderImageLocation(graphics, imageLocation, sprite);
				}
			}
		}

        return null;
    }

    private int getOffSet(Victim victim, int ordinal)
	{
		boolean isVictimFrozen = false;
		boolean isVictimTbd = false;
		boolean isVictimVenged = false;

		if (ordinal == 0)
		{
			return 0;
		}
		else if (ordinal == 1)
		{
			for (VictimPvpTimer victimPvpTimer : victim.getVictimPvpTimers())
			{
				if (victimPvpTimer.getPvpTimer().getType() == TimerType.FREEZE)
				{
					isVictimFrozen = true;
				}
			}

			if (isVictimFrozen)
			{
				return victim.getActor().getLogicalHeight() + 50; // want above head
			}
			else
			{
				return 0;
			}
		}
		else if (ordinal == 2)
		{
			for (VictimPvpTimer victimPvpTimer : victim.getVictimPvpTimers())
			{
				if (victimPvpTimer.getPvpTimer().getType() == TimerType.FREEZE)
				{
					isVictimFrozen = true;
				}
				else if (victimPvpTimer.getPvpTimer().getType() == TimerType.TELEBLOCK)
				{
					isVictimTbd = true;
				}
			}

			if (isVictimFrozen && isVictimTbd)
			{
				return victim.getActor().getLogicalHeight() / 2;
			}
			else if (isVictimFrozen || isVictimTbd)
			{
				return victim.getActor().getLogicalHeight() + 50;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			for (VictimPvpTimer victimPvpTimer : victim.getVictimPvpTimers())
			{
				if (victimPvpTimer.getPvpTimer().getType() == TimerType.FREEZE)
				{
					isVictimFrozen = true;
				}
				else if (victimPvpTimer.getPvpTimer().getType() == TimerType.TELEBLOCK)
				{
					isVictimTbd = true;
				}
				else if (victimPvpTimer.getPvpTimer().getType() == TimerType.VENGEANCE)
				{
					isVictimVenged = true;
				}
			}

			if ((isVictimFrozen && isVictimTbd) 	||
				(isVictimFrozen && isVictimVenged)	||
				(isVictimTbd && isVictimVenged))
			{
				return victim.getActor().getLogicalHeight() / 2;
			}
			else
			{
				return victim.getActor().getLogicalHeight();
			}
		}
	}
}