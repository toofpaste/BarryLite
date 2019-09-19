package net.runelite.client.plugins.pvpcalculators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class PvpCalculatorOverlay extends Overlay
{
	private PvpCalculatorPlugin plugin;
	private PanelComponent panelComponent = new PanelComponent();

	@Inject
	private PvpCalculatorOverlay(PvpCalculatorPlugin plugin)
	{
		this.plugin = plugin;
		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.getChildren().clear();
		panelComponent.setPreferredSize(new Dimension(graphics.getFontMetrics().stringWidth("QQQQQQQQQQQQ"),0));

		PvpPlayer pvpPlayer = plugin.getPvpPlayer();

		if (pvpPlayer == null)
			return null;

		String playerName = pvpPlayer.getPlayer().getName();

		panelComponent.getChildren().add(TitleComponent.builder()
			.text(playerName)
			.color(Color.GREEN)
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("AXE:")
			.right("" + pvpPlayer.getMaxDharokAxe())
			.build());

		panelComponent.getChildren().add(LineComponent.builder()
			.left("AGS:")
			.right("" + pvpPlayer.getMaxAgsSpec())
			.build());

		return panelComponent.render(graphics);
	}
}
