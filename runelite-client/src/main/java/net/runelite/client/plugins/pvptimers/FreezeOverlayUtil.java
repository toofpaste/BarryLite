package net.runelite.client.plugins.pvptimers;

import com.google.common.base.Strings;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;

public class FreezeOverlayUtil extends OverlayUtil
{
    public static void renderTextLocation(Graphics2D graphics, Point txtLoc, String text, Color color)
    {
        if (Strings.isNullOrEmpty(text))
            return;

        int x = txtLoc.getX();
        int y = txtLoc.getY();

        // Emphasize blue text shadow
        graphics.setColor(Color.BLUE);
        graphics.drawString(text, x + 1, y + 1);
        graphics.drawString(text, x + 1, y - 1);
        graphics.drawString(text, x - 1, y + 1);
        graphics.drawString(text, x - 1, y - 1);
        graphics.drawString(text, x + 2, y + 2);
        graphics.drawString(text, x + 2, y - 2);
        graphics.drawString(text, x - 2, y + 2);
        graphics.drawString(text, x - 2, y - 2);

        graphics.setColor(color);
        graphics.drawString(text, x, y);
    }
}
