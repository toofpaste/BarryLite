package net.runelite.client.plugins.pvpsupplytracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("pvpsupplytracker")
public interface PvpSupplyTrackerConfig extends Config
{
    @ConfigItem(
    	keyName = "ignoredItems",
		name = "Ignored items",
		description = "Configures which items should be ignored when calculating cost of supplies."
    )
    default String getIgnoredItems()
    {
        return "";
    }

	@ConfigItem(
		keyName = "ignoredItems",
		name = "",
		description = ""
	)
	void setIgnoredItems(String key);
}
