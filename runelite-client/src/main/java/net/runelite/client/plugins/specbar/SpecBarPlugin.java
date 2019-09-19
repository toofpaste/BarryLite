package net.runelite.client.plugins.specbar;

import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.ClientTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "(G) Spec Bar",
	description = "Shows the spec bar on weapons that do not have one",
	tags = {"special", "spec-bar", "special attack"},
	enabledByDefault = true
)
public class SpecBarPlugin extends Plugin
{
	@Inject
	private Client client;

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		// s 593.34
		final int specBarWidgetId = 34;
		Widget specbarWidget = client.getWidget(WidgetID.COMBAT_GROUP_ID, specBarWidgetId);
		specbarWidget.setHidden(false);
	}
}
