package net.runelite.client.plugins.pvpcalculators.data;

import java.util.HashMap;
import net.runelite.api.ItemID;
import net.runelite.client.game.ItemVariationMapping;

public final class PvpMeleeEquipment
{
	public static final HashMap<Integer, Integer> weaponMap = new HashMap<>();
	public static final HashMap<Integer, Integer> amuletMap = new HashMap<>();
	public static final HashMap<Integer, Integer> headMap = new HashMap<>();
	public static final HashMap<Integer, Integer> torsoMap = new HashMap<>();
	public static final HashMap<Integer, Integer> legMap = new HashMap<>();
	public static final HashMap<Integer, Integer> shieldMap = new HashMap<>();
	public static final HashMap<Integer, Integer> capeMap = new HashMap<>();
	public static final HashMap<Integer, Integer> handMap = new HashMap<>();
	public static final HashMap<Integer, Integer> bootMap = new HashMap<>();

	static
	{
		// ONLY SHOW RELEVANT MELEE WEAPONS
		weaponMap.put(ItemVariationMapping.map(ItemID.GRANITE_MAUL), 79);
		weaponMap.put(ItemVariationMapping.map(ItemID.ARMADYL_GODSWORD), 132);
		weaponMap.put(ItemVariationMapping.map(ItemID.DRAGON_CLAWS), 56);
		weaponMap.put(ItemVariationMapping.map(ItemID.DHAROKS_GREATAXE), 105);

		amuletMap.put(ItemVariationMapping.map(ItemID.AMULET_OF_TORTURE), 10);
		amuletMap.put(ItemVariationMapping.map(ItemID.AMULET_OF_STRENGTH), 10);
		amuletMap.put(ItemVariationMapping.map(ItemID.AMULET_OF_FURY), 8);
		amuletMap.put(ItemID.BERSERKER_NECKLACE, 7);
		amuletMap.put(ItemVariationMapping.map(ItemID.AMULET_OF_GLORY), 6);
		amuletMap.put(ItemVariationMapping.map(ItemID.AMULET_OF_POWER), 6);
		amuletMap.put(ItemID.BEADS_OF_THE_DEAD, 1);

		headMap.put(ItemVariationMapping.map(ItemID.SERPENTINE_HELM), 5);
		headMap.put(ItemID.HELM_OF_NEITIZNOT, 3);
		headMap.put(ItemID.BERSERKER_HELM, 3);
		headMap.put(ItemID.OBSIDIAN_HELMET, 3);

		torsoMap.put(ItemID.BANDOS_CHESTPLATE, 4);
		torsoMap.put(ItemID.FIGHTER_TORSO, 4);
		torsoMap.put(ItemID.OBSIDIAN_PLATEBODY, 3);

		legMap.put(ItemID.BANDOS_TASSETS, 2);
		legMap.put(ItemID.OBSIDIAN_PLATELEGS, 1);
		legMap.put(ItemID.FREMENNIK_KILT, 1);

		shieldMap.put(ItemID.AVERNIC_DEFENDER, 8);
		shieldMap.put(ItemID.DRAGONFIRE_SHIELD, 7);
		shieldMap.put(ItemVariationMapping.map(ItemID.DRAGON_DEFENDER), 6);
		shieldMap.put(ItemID.TOKTZKETXIL, 5);
		shieldMap.put(ItemVariationMapping.map(ItemID.RUNE_DEFENDER), 5);
		shieldMap.put(ItemID.ADAMANT_DEFENDER, 4);
		shieldMap.put(ItemID.MITHRIL_DEFENDER, 3);
		shieldMap.put(ItemID.BOOK_OF_WAR, 2);
		shieldMap.put(ItemID.BLACK_DEFENDER, 2);
		shieldMap.put(ItemID.STEEL_DEFENDER, 1);

		capeMap.put(ItemVariationMapping.map(ItemID.INFERNAL_CAPE), 8);
		capeMap.put(ItemVariationMapping.map(ItemID.FIRE_CAPE), 4);
		capeMap.put(ItemID.MYTHICAL_CAPE, 1);

		handMap.put(ItemID.FEROCIOUS_GLOVES, 14);
		handMap.put(ItemID.BARROWS_GLOVES, 12);
		handMap.put(ItemID.DRAGON_GLOVES, 9);
		handMap.put(ItemID.RUNE_GLOVES, 8);
		handMap.put(ItemID.GRANITE_GLOVES, 7);
		handMap.put(ItemID.REGEN_BRACELET, 7);
		handMap.put(ItemID.ADAMANT_GLOVES, 7);
		handMap.put(ItemID.MITHRIL_GLOVES, 5);
		handMap.put(ItemID.COMBAT_BRACELET, 5);
		handMap.put(ItemID.BLACK_GLOVES, 5);
		handMap.put(ItemID.STEEL_GLOVES, 4);
		handMap.put(ItemID.IRON_GLOVES, 3);
		handMap.put(ItemID.SPIKY_VAMBRACES, 2);

		bootMap.put(ItemID.PRIMORDIAL_BOOTS, 5);
		bootMap.put(ItemVariationMapping.map(ItemID.DRAGON_BOOTS), 4);
		bootMap.put(ItemID.SPIKED_MANACLES, 4);
		bootMap.put(ItemID.GUARDIAN_BOOTS, 3);
		bootMap.put(ItemID.RUNE_BOOTS, 2);
		bootMap.put(ItemID.GILDED_BOOTS, 2);
		bootMap.put(ItemVariationMapping.map(ItemID.CLIMBING_BOOTS), 2);
		bootMap.put(ItemID.FREMENNIK_KILT, 1);
	}

	private PvpMeleeEquipment(){};
}
