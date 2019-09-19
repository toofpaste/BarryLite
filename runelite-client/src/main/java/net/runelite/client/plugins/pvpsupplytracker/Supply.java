package net.runelite.client.plugins.pvpsupplytracker;

import lombok.Data;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.pvpsupplytracker.data.Untradables;

@Data
class Supply implements Comparable<Supply>
{
    private int id;
    private int quantity;
    private String name;
    private int price;
    private boolean isIgnored = false;

    private static final float HIGH_ALCHEMY_CONSTANT = 0.6f;

    Supply(int id, int quantity, ItemManager itemManager)
    {
        this.id = id;
        this.quantity = quantity;
        this.price = getItemValue(itemManager);
        this.name = itemManager.getItemComposition(id).getName();
    }

    Supply (Item item, ItemManager itemManager)
	{
		this(item.getId(), item.getQuantity(), itemManager);
	}

    private int getItemValue(ItemManager itemManager)
    {
        if (Untradables.BROKEN_UNTRADABLES.containsKey(this.id))
        {
            return Untradables.BROKEN_UNTRADABLES.get(this.id);
        }

        final int gePrice = itemManager.getItemPrice(id);

        if (gePrice > 0)
        {
            return gePrice;
        }

        // Return high-alch price
        ItemComposition itemComposition = itemManager.getItemComposition(id);
        return itemComposition.getPrice() <= 0 ? 0 : Math.round(itemComposition.getPrice() * HIGH_ALCHEMY_CONSTANT);
    }

    void increaseQuantity(int quantity)
    {
        if (quantity > 0)
        {
            this.quantity += quantity;
        }
    }

    int getTotalPrice()
	{
		return this.getPrice() * this.getQuantity();
	}

    @Override
    public int compareTo(Supply o)
    {
        return o.getTotalPrice() - this.getTotalPrice();
    }
}
