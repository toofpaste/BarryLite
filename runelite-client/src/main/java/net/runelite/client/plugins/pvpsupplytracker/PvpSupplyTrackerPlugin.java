package net.runelite.client.plugins.pvpsupplytracker;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemVariationMapping;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.runepouch.Runes;
import net.runelite.client.plugins.pvpsupplytracker.data.MultiPhaseSupply;
import net.runelite.client.plugins.pvpsupplytracker.data.Untradables;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;
import net.runelite.http.api.loottracker.GameItem;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

@PluginDescriptor(
                    name = "(G) PvP Supply Tracker",
                    description = "Tracks consumption of supplies.",
                    tags = {"supply, pvp, tracker, consumed, items, death"},
                    enabledByDefault = true
                 )
public class PvpSupplyTrackerPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
	private PvpSupplyTrackerConfig config;

    @Inject
    private ClientToolbar clientToolbar;

	@Getter	private List<Supply> suppliesConsumed = new ArrayList<Supply>();
	@Getter	private GameItem[] previousInventoryItems;
	@Getter	private GameItem[] previousEquipmentItems;
	private GameItem[] previousInventoryItemsEquipmentEvent; // Previous equipment stored when both inventory and equipment container change at the same time
	private boolean justDied = false;
	private int ignoreCaseCounter;

	// Config stuff
	private List<String> previousIgnoredItems = new ArrayList<>();
	private List<String> currentIgnoredItems = new ArrayList<>();
	private boolean isToggled = false;

    // Panel stuff
    private PvpSupplyTrackerPanel panel;
    private NavigationButton navButton;

	@Provides
	PvpSupplyTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PvpSupplyTrackerConfig.class);
	}

    @Override
    protected void startUp() throws Exception
    {
        panel = new PvpSupplyTrackerPanel(this, itemManager);
        this.previousIgnoredItems = Text.fromCSV(config.getIgnoredItems());

        // Get a relevant icon, like a brew
        final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "coins.png");

        navButton = NavigationButton.builder()
                                    .tooltip("Supply Tracker")
                                    .icon(icon)
                                    .priority(5)
                                    .panel(panel)
                                    .build();

        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown()
    {
        clientToolbar.removeNavigation(navButton);
    }

	void toggleItem(String name, boolean ignore)
	{
		final Set<String> ignoredItemSet = new HashSet<>(previousIgnoredItems);

		if (ignore)
		{
			ignoredItemSet.add(name);
		}
		else
		{
			ignoredItemSet.remove(name);
		}

		this.isToggled = true;
		config.setIgnoredItems(Text.toCSV(ignoredItemSet));
	}

	private List<String> getAddedItems()
	{
		List<String> addedIgnoredItems = new ArrayList<>();

		for (String currentIgnored : this.currentIgnoredItems)
		{
			if (!this.previousIgnoredItems.contains(currentIgnored))
			{
				addedIgnoredItems.add(currentIgnored);
			}
		}

		return addedIgnoredItems;
	}

	private List<String> getRemovedItems()
	{
		List<String> removedIgnoredItems = new ArrayList<>();

		for (String currentIgnored : this.previousIgnoredItems)
		{
			if (!this.currentIgnoredItems.contains(currentIgnored))
			{
				removedIgnoredItems.add(currentIgnored);
			}
		}

		return removedIgnoredItems;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("pvpsupplytracker"))
		{
			currentIgnoredItems = Text.fromCSV(config.getIgnoredItems());

			if (this.isToggled)
			{
				// Toggler sets ignore property on its own.
				this.isToggled = false;
			}
			else
			{
				// Added names
				List<String> addedIgnoredItems = getAddedItems();

				// If added, set the ignore property to true
				for (String ignoredSupply : addedIgnoredItems)
				{
					for (Supply supply : suppliesConsumed)
					{
						if (supply.getName().equals(ignoredSupply))
						{
							supply.setIgnored(true);
						}
					}
				}

				// Removed names
				List<String> removedIgnoredItems = getRemovedItems();

				// If removed, set the ignore property to false;
				for (String ignoredSupply : removedIgnoredItems)
				{
					for (Supply supply : suppliesConsumed)
					{
						if (supply.getName().equals(ignoredSupply))
						{
							supply.setIgnored(false);
						}
					}
				}
			}

			this.previousIgnoredItems = this.currentIgnoredItems;
			SwingUtilities.invokeLater(() -> panel.rebuild(this.suppliesConsumed));
		}
	}

	/**
	 * Hacky way to prevent double input of lost items on death
	 *
	 * @param event
	 */
	@Subscribe
	public void onLocalPlayerDeath(LocalPlayerDeath event)
	{
		justDied = true;
	}

	/**
	 * Used for tracking tsod zulrah scales and rune pouch supplies as runes in rune pouch are not tracked on inventory
	 * change.
	 *
	 * @param event
	 */
	@Subscribe
	public void onExperienceChanged(ExperienceChanged event)
	{
		if (event.getSkill() == Skill.MAGIC)
		{
				addScalesToConsumptionList();


			ItemContainer container = client.getItemContainer(InventoryID.INVENTORY);
			GameItem[] gameItems = container == null ? null : convertToGameItem(container.getItems());

			onInventoryChange(gameItems);
		}
	}

	/**
	 * Used to track scales used when staff bashing with the TSOD
	 *
	 * @param event
	 */
	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		final int staffPokeAnim = 440;

		if (event.getActor().getAnimation() == staffPokeAnim)
		{
			addScalesToConsumptionList();
		}
	}

	private void addScalesToConsumptionList()
	{
		ItemContainer container = client.getItemContainer(InventoryID.EQUIPMENT);
		Item[] items = container == null ? null : container.getItems();

		if (items != null && items.length > EquipmentInventorySlot.WEAPON.getSlotIdx())
		{
			final Item weapon = items[EquipmentInventorySlot.WEAPON.getSlotIdx()];

			if (weapon.getId() == ItemID.TOXIC_STAFF_OF_THE_DEAD)
			{
				// You staff bashed a dude with tsod. You lose one scale.
				List<Supply> scalesUsed = new ArrayList<>();
				scalesUsed.add(new Supply(ItemID.ZULRAHS_SCALES, 1, itemManager));
				buildConsumptionList(scalesUsed);
				SwingUtilities.invokeLater(() -> this.panel.rebuild(this.suppliesConsumed));
			}
		}
	}

	/**
	 * Hacky way to prevent items being consumed when deposited 1 tick after bank interface has closed (i.e. delayed deposit).
	 *
	 * A small issue is that supplies consumed a tick after an ignore case are not tracked. Much better drawback than the
	 * one listed above.
	 *
	 * @param event
	 */
	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (isIgnoreCase())
		{
			ignoreCaseCounter = 2;
		}
		else if (ignoreCaseCounter > 0)
		{
			ignoreCaseCounter--;
		}
	}

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event)
    {
		if (event.getItemContainer() == client.getItemContainer(InventoryID.EQUIPMENT))
		{
			onEquipmentChange(convertToGameItem(event.getItemContainer().getItems()));
		}
		else if (event.getItemContainer() == client.getItemContainer(InventoryID.INVENTORY))
        {
        	onInventoryChange(convertToGameItem(event.getItemContainer().getItems()));
        }
    }

	private boolean isIgnoreCase()
	{
		Widget bankWidget = client.getWidget(WidgetInfo.BANK_CONTAINER);
		Widget depoBoxWidget = client.getWidget(WidgetInfo.DEPOSIT_BOX_INVENTORY_ITEMS_CONTAINER);
		Widget grandExchangeWidget = client.getWidget(WidgetInfo.GRAND_EXCHANGE_WINDOW_CONTAINER);

		return 	bankWidget != null && !bankWidget.isHidden() ||
				depoBoxWidget != null && !depoBoxWidget.isHidden() ||
				grandExchangeWidget != null && !grandExchangeWidget.isHidden();
	}

	private void onEquipmentChange(GameItem[] gameItems)
	{
		if (isIgnoreCase() || this.previousEquipmentItems == null || justDied || ignoreCaseCounter > 0)
		{
			this.previousEquipmentItems = gameItems;
			this.previousInventoryItemsEquipmentEvent = null;
			justDied = false;
			return;
		}

		// If previous inventory quantity is not the same as before (i,e, decrease) then post event
		if (!Arrays.equals(this.previousEquipmentItems, gameItems))
		{
			ItemContainer currentContainer = client.getItemContainer(InventoryID.INVENTORY);
			GameItem[] currentInventory = (currentContainer == null ? null : convertToGameItem(currentContainer.getItems()));
			GameItem[] current = (currentInventory == null ? gameItems : ArrayUtils.addAll(currentInventory, gameItems));
			GameItem[] previous = ArrayUtils.addAll(previousInventoryItemsEquipmentEvent, previousEquipmentItems);

			invokePanelBuilder(current, previous);
		}

		this.previousInventoryItemsEquipmentEvent = null;
		this.previousEquipmentItems = gameItems;
	}

	private void onInventoryChange(GameItem[] gameItems)
	{
		if (isIgnoreCase()|| this.previousInventoryItems == null || ignoreCaseCounter > 0)
		{
			this.previousInventoryItems = gameItems;
			return;
		}

		// Needed for when duel event occurs (i.e. Inventory change and Equipment chagne at the same time).
		List<GameItem> previousInventoryList = new ArrayList<>();

		// If previous inventory quantity is not the same as before (i,e, decrease) then post event
		if (!Arrays.equals(this.previousInventoryItems, gameItems))
		{
			ItemContainer currentContainer = client.getItemContainer(InventoryID.EQUIPMENT);
			GameItem[] currentEquipment = (currentContainer == null ? null : convertToGameItem(currentContainer.getItems()));
			GameItem[] current = (currentEquipment == null ? gameItems : ArrayUtils.addAll(currentEquipment, gameItems));
			GameItem[] previous = ArrayUtils.addAll(previousEquipmentItems, this.previousInventoryItems);
			List<Supply> consumedSupplies = suppliesUsed(buildSupplies(current), buildSupplies(previous));

			// whatever supplies were consumed, remove them from the equipment inventory event.
			for (Supply supply : consumedSupplies)
			{
				int supplyId = supply.getId();
				int supplyQuantity = supply.getQuantity();

				for (GameItem item : this.previousInventoryItems)
				{
					if (item.getId() == supplyId && supplyQuantity > 0)
					{
						supplyQuantity--;
						continue;
					}

					previousInventoryList.add(item);
				}
			}

			invokePanelBuilder(current, previous);
		}

		this.previousInventoryItemsEquipmentEvent = previousInventoryList.toArray(new GameItem[0]);
		this.previousInventoryItems = gameItems;
	}

	private HashMap<Integer, Supply> suppliesHashMap = new HashMap<>();

    /**
     * Returns the given inventory into a list of Supply, removing all duplicates and showing them via
     * a quantity field.
     *
     * Elaborating, on any given supply, consumption of it will add it to the list. In terms of multiple phase supplies
     * like pizzas and potions (where consumption doesn't remove the item entirely), the list will populate the simplest
     * form of said supply and add the quantity accordingly.
     *
     * @param inventory The array of Items in the given ItemContainerChanged event
     * @return A List of Supply removing duplicates from the parameter and showcasing them as object properties
     */
    private List<Supply> buildSupplies(GameItem[] inventory)
    {
    	if (inventory == null)
		{
			return Collections.emptyList();
		}

        for (GameItem item : inventory)
        {
            int itemId = item.getId();

            if (itemId == -1)
            {
                continue;
            }

            if (itemId == ItemID.RUNE_POUCH)
			{
				System.out.println("FUCKED UP THIS SHOULDNT BE POSSIBLE");
			}

            ItemComposition itemComposition = itemManager.getItemComposition(itemId);

            // If item is noted, remove note format.
            if (itemComposition.getNote() == 799)
            {
                itemId = itemComposition.getLinkedNoteId();
            }

			int originalItemId = itemId;
			itemId = ItemVariationMapping.map(itemId);
			int itemQuantity = item.getQty();
			int[] phaseItem = MultiPhaseSupply.getAllPhasesOfItem(originalItemId);

			// Determine if item is special case (MultiPhaseSupply or Untradable)
			if (ArrayUtils.contains(phaseItem, originalItemId))
			{
				// Determine if supply has multiple stages (i.e. potions, pizzas, etc)
				int [] phasesOfSupply = MultiPhaseSupply.getAllPhasesOfItem(originalItemId);
				int multiplier = 1;

				if (phasesOfSupply != null)
				{
					int phases = phasesOfSupply.length;

					multiplier = phases - ArrayUtils.indexOf(phasesOfSupply, originalItemId);
					itemId = phasesOfSupply[phases - 1];
				}

				itemQuantity *= multiplier;
			}
			else if (Untradables.BROKEN_UNTRADABLES.containsKey(originalItemId))
			{
				itemId = originalItemId;
			}

			addToHashMap(itemId, itemQuantity);
        }

        List<Supply> supplies =  new ArrayList<>(this.suppliesHashMap.values());
        this.suppliesHashMap.clear();
        return supplies;
    }

	private void addToHashMap(int itemId, int itemQuantity)
	{
		if (this.suppliesHashMap.containsKey(itemId))
		{
			this.suppliesHashMap.get(itemId).increaseQuantity(itemQuantity);
		}
		else
		{
			Supply supply = new Supply(itemId, itemQuantity, itemManager);
			this.suppliesHashMap.put(itemId, supply);
		}
	}

	private int getSupplyIndex(List<Supply> inventory, Supply supply)
	{
		int index = 0;

		for (Supply inventSupp : inventory)
		{
			if (inventSupp.getId() == supply.getId())
			{
				return index;
			}
			index++;
		}
		return -1;
	}

    /**
     * Returns a list of only the consumed supplies from the event by comparing the previous inventory with current.
     *
     * @param currentInventory
	 * @param previous
     * @return
     */
    private List<Supply> suppliesUsed(List<Supply> currentInventory, List<Supply> previous)
    {
        List<Supply> suppliesUsed = new ArrayList<>();

        // CI = currentInventory
        for (Supply initialSupply : previous)
        {
            int ciSupplyIndex = getSupplyIndex(currentInventory, initialSupply);

            if (ciSupplyIndex >= 0)
            {
                Supply ciSupply = currentInventory.get(ciSupplyIndex);

                if (initialSupply.getQuantity() > ciSupply.getQuantity())
                {
                    int quantity = initialSupply.getQuantity() - ciSupply.getQuantity();
                    suppliesUsed.add(new Supply(ciSupply.getId(), quantity, itemManager));
                }
            }
            else
            {
                suppliesUsed.add(initialSupply);
            }
        }
        return suppliesUsed;
    }

    /**
     * Adds the event's consumedSupplies to the main list of supplies consumed
     *
     * @param consumedSupplies The event's consumed suppleis
     */
    private void buildConsumptionList(List<Supply> consumedSupplies)
    {
        for (Supply supply : consumedSupplies)
        {
            int index = getSupplyIndex(this.suppliesConsumed, supply);

            if (index == -1)
            {
                // DNE in the list, simply add supply
                this.suppliesConsumed.add(supply);
            }
            else
            {
                this.suppliesConsumed.get(index).increaseQuantity(supply.getQuantity());
            }
        }
        Collections.sort(this.suppliesConsumed);
    }

	private void invokePanelBuilder(GameItem[] current, GameItem[] previous)
	{
		List<Supply> currentSupplies = buildSupplies(current);
		List<Supply> previousSupplies = buildSupplies(previous);

		// Append consumed supplies from event with current list of consumed supplies
		buildConsumptionList(suppliesUsed(currentSupplies, previousSupplies));
		SwingUtilities.invokeLater(() -> this.panel.rebuild(this.suppliesConsumed));
	}

    void reset()
	{
		this.suppliesConsumed.clear();

		ItemContainer containerInventory = client.getItemContainer(InventoryID.INVENTORY);
		ItemContainer containerEquipment = client.getItemContainer(InventoryID.EQUIPMENT);
		this.previousInventoryItems = containerInventory == null ? null : convertToGameItem(containerInventory.getItems());
		this.previousEquipmentItems = containerEquipment == null ? null : convertToGameItem(containerEquipment.getItems());
	}

	private GameItem[] convertToGameItem (Item[] items)
	{
		List<GameItem> gameItems = new ArrayList<>();

		// Check if there's a rune pouch in items
		for (Item item : items)
		{
			if (item.getId() == ItemID.RUNE_POUCH)
			{
				// Check how many runes there are in the pouch
				Varbits[] amountVarbits = {Varbits.RUNE_POUCH_AMOUNT1, Varbits.RUNE_POUCH_AMOUNT2, Varbits.RUNE_POUCH_AMOUNT3};
				Varbits[] runeVarbits = {Varbits.RUNE_POUCH_RUNE1, Varbits.RUNE_POUCH_RUNE2, Varbits.RUNE_POUCH_RUNE3};

				for (int i = 0; i < amountVarbits.length; i++)
				{
					Varbits amountVarbit = amountVarbits[i];
					int itemQuantity = client.getVar(amountVarbit);

					if (itemQuantity <= 0)
					{
						continue;
					}

					Varbits runeVarbit = runeVarbits[i];
					int runeId = client.getVar(runeVarbit);
					Runes rune = Runes.getRune(runeId);

					if (rune == null)
					{
						continue;
					}

					int itemId = rune.getItemId();
					gameItems.add(new GameItem(itemId, itemQuantity));
				}
				break;
			}
		}

		// GameItem[] gameItems = new GameItem[items.length + runesInPouch];
		for (Item item : items)
		{
			if (item.getId() != -1)
			{
				gameItems.add(new GameItem(item.getId(), item.getQuantity()));
			}
		}

		return gameItems.toArray(new GameItem[0]);
	}
}
