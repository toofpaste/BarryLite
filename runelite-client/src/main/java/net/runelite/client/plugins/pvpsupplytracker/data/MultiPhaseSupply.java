package net.runelite.client.plugins.pvpsupplytracker.data;

import net.runelite.api.ItemID;
import org.apache.commons.lang3.ArrayUtils;

public final class MultiPhaseSupply
{
    // Potions
    private static final int[] ATTACK_POTION = {ItemID.ATTACK_POTION4, ItemID.ATTACK_POTION3, ItemID.ATTACK_POTION2, ItemID.ATTACK_POTION1};
    private static final int[] STRENGTH_POTION = {ItemID.STRENGTH_POTION4, ItemID.STRENGTH_POTION3, ItemID.STRENGTH_POTION2, ItemID.STRENGTH_POTION1};
    private static final int[] DEFENCE_POTION = {ItemID.DEFENCE_POTION4, ItemID.DEFENCE_POTION3, ItemID.DEFENCE_POTION2, ItemID.DEFENCE_POTION1};
    private static final int[] COMBAT_POTION = {ItemID.COMBAT_POTION4, ItemID.COMBAT_POTION3, ItemID.COMBAT_POTION2, ItemID.COMBAT_POTION1};
    private static final int[] SUPER_ATTACK_POTION = {ItemID.SUPER_ATTACK4, ItemID.SUPER_ATTACK3, ItemID.SUPER_ATTACK2, ItemID.SUPER_ATTACK1};
    private static final int[] SUPER_STRENGTH_POTION = {ItemID.SUPER_STRENGTH4, ItemID.SUPER_STRENGTH3, ItemID.SUPER_STRENGTH2, ItemID.SUPER_STRENGTH1};
    private static final int[] SUPER_DEFENCE_POTION = {ItemID.SUPER_DEFENCE4, ItemID.SUPER_DEFENCE3, ItemID.SUPER_DEFENCE2, ItemID.SUPER_DEFENCE1};
    private static final int[] SUPER_COMBAT_POTION = {ItemID.SUPER_COMBAT_POTION4, ItemID.SUPER_COMBAT_POTION3, ItemID.SUPER_COMBAT_POTION2, ItemID.SUPER_COMBAT_POTION1};
    private static final int[] RANGING_POTION = {ItemID.RANGING_POTION4, ItemID.RANGING_POTION3, ItemID.RANGING_POTION2, ItemID.RANGING_POTION1};
    private static final int[] BASTION_POTION = {ItemID.BASTION_POTION4, ItemID.BASTION_POTION3, ItemID.BASTION_POTION2, ItemID.BASTION_POTION1};
    private static final int[] MAGIC_ESSENCE_POTION = {ItemID.MAGIC_ESSENCE4, ItemID.MAGIC_ESSENCE3, ItemID.MAGIC_ESSENCE2, ItemID.MAGIC_ESSENCE1};
    private static final int[] MAGIC_POTION = {ItemID.MAGIC_POTION4, ItemID.MAGIC_POTION3, ItemID.MAGIC_POTION2, ItemID.MAGIC_POTION1};
    private static final int[] BATTLEMAGE_POTION = {ItemID.BATTLEMAGE_POTION4, ItemID.BATTLEMAGE_POTION3, ItemID.BATTLEMAGE_POTION2, ItemID.BATTLEMAGE_POTION1};
    private static final int[] ANTIPOISON = {ItemID.ANTIPOISON4, ItemID.ANTIPOISON3, ItemID.ANTIPOISON2, ItemID.ANTIPOISON1};
    private static final int[] SUPERANTIPOISON = {ItemID.SUPERANTIPOISON4, ItemID.SUPERANTIPOISON3, ItemID.SUPERANTIPOISON2, ItemID.SUPERANTIPOISON1};
    private static final int[] ANTIDOTE_P= {ItemID.ANTIDOTE4, ItemID.ANTIDOTE3, ItemID.ANTIDOTE2, ItemID.ANTIDOTE1};
    private static final int[] ANTIDOTE_PP = {ItemID.ANTIDOTE4_5952, ItemID.ANTIDOTE3_5954, ItemID.ANTIDOTE2_5956, ItemID.ANTIDOTE1_5958};
    private static final int[] ANTIVENOM = {ItemID.ANTIVENOM4, ItemID.ANTIVENOM3, ItemID.ANTIVENOM2, ItemID.ANTIVENOM1};
    private static final int[] ANTIVENOM_P = {ItemID.ANTIVENOM4_12913, ItemID.ANTIVENOM3_12915, ItemID.ANTIVENOM2_12917, ItemID.ANTIVENOM1_12919};
    private static final int[] ENERGY_POTION = {ItemID.ENERGY_POTION4, ItemID.ENERGY_POTION3, ItemID.ENERGY_POTION2, ItemID.ENERGY_POTION1};
    private static final int[] SUPER_ENERGY_POTION = {ItemID.SUPER_ENERGY4, ItemID.SUPER_ENERGY3, ItemID.SUPER_ENERGY2, ItemID.SUPER_ENERGY1};
    private static final int[] STAMINA_POTION = {ItemID.STAMINA_POTION4, ItemID.STAMINA_POTION3, ItemID.STAMINA_POTION2, ItemID.STAMINA_POTION1};
    private static final int[] PRAYER_POTION = {ItemID.PRAYER_POTION4, ItemID.PRAYER_POTION3, ItemID.PRAYER_POTION2, ItemID.PRAYER_POTION1};
    private static final int[] RESTORE_POTION = {ItemID.RESTORE_POTION4, ItemID.RESTORE_POTION3, ItemID.RESTORE_POTION2, ItemID.RESTORE_POTION1};
    private static final int[] SUPER_RESTORE_POTION = {ItemID.SUPER_RESTORE4, ItemID.SUPER_RESTORE3, ItemID.SUPER_RESTORE2, ItemID.SUPER_RESTORE1};
    private static final int[] SANFEW_SERUM = {ItemID.SANFEW_SERUM4, ItemID.SANFEW_SERUM3, ItemID.SANFEW_SERUM2, ItemID.SANFEW_SERUM1};
    private static final int[] AGILITY_POTION = {ItemID.AGILITY_POTION4, ItemID.AGILITY_POTION3, ItemID.AGILITY_POTION2, ItemID.AGILITY_POTION1};
    private static final int[] FISHING_POTION = {ItemID.FISHING_POTION4, ItemID.FISHING_POTION3, ItemID.FISHING_POTION2, ItemID.FISHING_POTION1};
    private static final int[] HUNTER_POTION = {ItemID.HUNTER_POTION4, ItemID.HUNTER_POTION3, ItemID.HUNTER_POTION2, ItemID.HUNTER_POTION1};
    private static final int[] ZAMORAK_BREW = {ItemID.ZAMORAK_BREW4, ItemID.ZAMORAK_BREW3, ItemID.ZAMORAK_BREW2, ItemID.ZAMORAK_BREW1};
    private static final int[] SARADOMIN_BREW = {ItemID.SARADOMIN_BREW4, ItemID.SARADOMIN_BREW3, ItemID.SARADOMIN_BREW2, ItemID.SARADOMIN_BREW1};
    private static final int[] ANTIFIRE_POTION = {ItemID.ANTIFIRE_POTION4, ItemID.ANTIFIRE_POTION3, ItemID.ANTIFIRE_POTION2, ItemID.ANTIFIRE_POTION1};
    private static final int[] EXTENDED_ANTIFIRE = {ItemID.EXTENDED_ANTIFIRE4, ItemID.EXTENDED_ANTIFIRE3, ItemID.EXTENDED_ANTIFIRE2, ItemID.EXTENDED_ANTIFIRE1};
    private static final int[] SUPER_ANTIFIRE_POTION = {ItemID.SUPER_ANTIFIRE_POTION4, ItemID.SUPER_ANTIFIRE_POTION3, ItemID.SUPER_ANTIFIRE_POTION2, ItemID.SUPER_ANTIFIRE_POTION1};
    private static final int[] EXTENDED_SUPER_ANTIFIRE = {ItemID.EXTENDED_SUPER_ANTIFIRE4, ItemID.EXTENDED_SUPER_ANTIFIRE3, ItemID.EXTENDED_SUPER_ANTIFIRE2, ItemID.EXTENDED_SUPER_ANTIFIRE1};
    private static final int[] GUTHIX_REST_TEA = {ItemID.GUTHIX_REST4, ItemID.GUTHIX_REST3, ItemID.GUTHIX_REST2, ItemID.GUTHIX_REST1};
    private static final int[] RELICYMS_BALM = {ItemID.RELICYMS_BALM4, ItemID.RELICYMS_BALM3, ItemID.RELICYMS_BALM2, ItemID.RELICYMS_BALM1};
    private static final int[] SERUM_207 = {ItemID.SERUM_207_4, ItemID.SERUM_207_3, ItemID.SERUM_207_2, ItemID.SERUM_207_1};
    private static final int[] COMPOST_POTION = {ItemID.COMPOST_POTION4, ItemID.COMPOST_POTION3, ItemID.COMPOST_POTION2, ItemID.COMPOST_POTION1};
    private static final int[] GUTHIX_BALANCE = {ItemID.GUTHIX_BALANCE4, ItemID.GUTHIX_BALANCE3, ItemID.GUTHIX_BALANCE2, ItemID.GUTHIX_BALANCE1};
	private static final int[] OLIVE_OILS = {ItemID.OLIVE_OIL4, ItemID.OLIVE_OIL3, ItemID.OLIVE_OIL2, ItemID.OLIVE_OIL1};
	private static final int[] SACRED_OILS = {ItemID.SACRED_OIL4, ItemID.SACRED_OIL3, ItemID.SACRED_OIL2, ItemID.SACRED_OIL1};

    // CAKES
    private static final int[] CAKE = {ItemID.CAKE, ItemID._23_CAKE, ItemID.SLICE_OF_CAKE};
    private static final int[] CHOCOLATE_CAKE = {ItemID.CHOCOLATE_CAKE, ItemID._23_CHOCOLATE_CAKE, ItemID.CHOCOLATE_SLICE};

    // PIZZAS
    private static final int[] PIZZA = {ItemID.PLAIN_PIZZA, ItemID._12_PLAIN_PIZZA};
    private static final int[] MEAT_PIZZA = {ItemID.MEAT_PIZZA, ItemID._12_MEAT_PIZZA};
    private static final int[] ANCHOVY_PIZZA = {ItemID.ANCHOVY_PIZZA, ItemID._12_ANCHOVY_PIZZA};
    private static final int[] PINEAPPLE_PIZZA = {ItemID.PINEAPPLE_PIZZA, ItemID._12_PINEAPPLE_PIZZA};

    // PIES
    private static final int[] REDBERRY_PIE = {ItemID.REDBERRY_PIE, ItemID.HALF_A_REDBERRY_PIE};
    private static final int[] MEAT_PIE = {ItemID.MEAT_PIE, ItemID.HALF_A_MEAT_PIE};
    private static final int[] APPLE_PIE = {ItemID.APPLE_PIE, ItemID.APPLE_PIE};
    private static final int[] GARDEN_PIE = {ItemID.GARDEN_PIE, ItemID.HALF_A_GARDEN_PIE};
    private static final int[] FISH_PIE = {ItemID.FISH_PIE, ItemID.HALF_A_FISH_PIE};
    private static final int[] BOTANICAL_PIE = {ItemID.BOTANICAL_PIE, ItemID.HALF_A_BOTANICAL_PIE};
    private static final int[] MUSHROOM_PIE = {ItemID.MUSHROOM_PIE, ItemID.HALF_A_MUSHROOM_PIE};
    private static final int[] ADMIRAL_PIE = {ItemID.ADMIRAL_PIE, ItemID.HALF_AN_ADMIRAL_PIE};
    private static final int[] DRAGONFRUIT_PIE = {ItemID.DRAGONFRUIT_PIE, ItemID.HALF_A_DRAGONFRUIT_PIE};
    private static final int[] WILD_PIE = {ItemID.WILD_PIE, ItemID.HALF_A_WILD_PIE};
    private static final int[] SUMMER_PIE = {ItemID.SUMMER_PIE, ItemID.HALF_A_SUMMER_PIE};

	// Others
	private static final int[] WATERSKINS = {ItemID.WATERSKIN4, ItemID.WATERSKIN3, ItemID.WATERSKIN2,
		ItemID.WATERSKIN1, ItemID.WATERSKIN0};

	// PvP
	private static final int[] FIRE_CAPE = {};
	private static final int[] FIRE_MAX_CAPE = {};
	private static final int[] INFERNAL_CAPE = {};
	private static final int[] INFERNAL_MAX_CAPE = {};
	private static final int[] AVAS_ASSEMBLER = {};
	private static final int[] ASSEMBLER_MAX_CAPE = {};
	private static final int[] BRONZE_DEFENDER = {ItemID.BRONZE_DEFENDER, ItemID.BRONZE_DEFENDER_BROKEN};
	private static final int[] IRON_DEFENDER = {ItemID.IRON_DEFENDER, ItemID.IRON_DEFENDER_BROKEN};
	private static final int[] STEEL_DEFENDER = {ItemID.STEEL_DEFENDER, ItemID.STEEL_DEFENDER_BROKEN};
	private static final int[] BLACK_DEFENDER = {ItemID.BLACK_DEFENDER, ItemID.BLACK_DEFENDER_BROKEN};
	private static final int[] MITHRIL_DEFENDER = {ItemID.MITHRIL_DEFENDER, ItemID.MITHRIL_DEFENDER_BROKEN};
	private static final int[] ADAMANT_DEFENDER = {ItemID.ADAMANT_DEFENDER, ItemID.ADAMANT_DEFENDER_BROKEN};
	private static final int[] RUNE_DEFENDER = {ItemID.RUNE_DEFENDER, ItemID.RUNE_DEFENDER_BROKEN};
	private static final int[] DRAGON_DEFENDER = {ItemID.DRAGON_DEFENDER, ItemID.DRAGON_DEFENDER_BROKEN};
	private static final int[] AVERNIC_DEFENDER = {ItemID.AVERNIC_DEFENDER, ItemID.AVERNIC_DEFENDER_BROKEN};

	private static final int[] VOID_HELM_MELEE = {ItemID.VOID_MELEE_HELM, ItemID.VOID_MELEE_HELM_BROKEN};
	private static final int[] VOID_HELM_RANGE = {ItemID.VOID_RANGER_HELM, ItemID.VOID_RANGER_HELM_BROKEN};
	private static final int[] VOID_HELM_MAGIC = {ItemID.VOID_MAGE_HELM, ItemID.VOID_MAGE_HELM_BROKEN};
	private static final int[] VOID_TOP = {ItemID.VOID_KNIGHT_TOP, ItemID.VOID_KNIGHT_TOP_BROKEN};
	private static final int[] VOID_ROBE = {ItemID.VOID_KNIGHT_ROBE, ItemID.VOID_KNIGHT_ROBE_BROKEN};
	private static final int[] VOID_ELITE_TOP = {ItemID.ELITE_VOID_TOP, ItemID.ELITE_VOID_TOP_BROKEN};
	private static final int[] VOID_ELITE_ROBE = {ItemID.ELITE_VOID_ROBE, ItemID.ELITE_VOID_ROBE_BROKEN};
	private static final int[] VOID_GLOVES = {ItemID.VOID_KNIGHT_GLOVES, ItemID.VOID_KNIGHT_GLOVES_BROKEN};

	private static final int[] DECORATIVE_ARMOUR_RANGE = {ItemID.DECORATIVE_ARMOUR_11899, ItemID.DECORATIVE_ARMOUR_BROKEN_20501};
	private static final int[] DECORATIVE_ARMOUR_MAGE_ROBE = {ItemID.DECORATIVE_ARMOUR_11896, ItemID.DECORATIVE_ARMOUR_BROKEN_20495};
	private static final int[] HALO = {ItemID.GUTHIX_HALO, ItemID.GUTHIX_HALO_BROKEN};



	static final int[][] POTIONS =
		{ATTACK_POTION, STRENGTH_POTION, DEFENCE_POTION, COMBAT_POTION, SUPER_ATTACK_POTION, SUPER_STRENGTH_POTION,
		SUPER_DEFENCE_POTION, SUPER_COMBAT_POTION, RANGING_POTION, BASTION_POTION, MAGIC_ESSENCE_POTION, MAGIC_POTION,
		BATTLEMAGE_POTION, ANTIPOISON, SUPERANTIPOISON, ANTIDOTE_P, ANTIDOTE_PP, ANTIVENOM, ANTIVENOM_P, ENERGY_POTION,
		SUPER_ENERGY_POTION, STAMINA_POTION, PRAYER_POTION, RESTORE_POTION, SUPER_RESTORE_POTION, SANFEW_SERUM, AGILITY_POTION,
		FISHING_POTION, HUNTER_POTION, ZAMORAK_BREW, SARADOMIN_BREW, ANTIFIRE_POTION, EXTENDED_ANTIFIRE, SUPER_ANTIFIRE_POTION,
		EXTENDED_SUPER_ANTIFIRE, GUTHIX_REST_TEA, RELICYMS_BALM, SERUM_207, COMPOST_POTION, GUTHIX_BALANCE, OLIVE_OILS,
		SACRED_OILS, WATERSKINS};

	static final int[][] FOOD =
		{CAKE, CHOCOLATE_CAKE, PIZZA, MEAT_PIZZA, ANCHOVY_PIZZA, PINEAPPLE_PIZZA, REDBERRY_PIE, MEAT_PIE, APPLE_PIE, GARDEN_PIE,
		FISH_PIE, BOTANICAL_PIE, MUSHROOM_PIE, ADMIRAL_PIE, DRAGONFRUIT_PIE, WILD_PIE, SUMMER_PIE};






    // DEGRADABLES
	private static final int[] DHAROK_HELM = {ItemID.DHAROKS_HELM, ItemID.DHAROKS_HELM_100, ItemID.DHAROKS_HELM_75,
												ItemID.DHAROKS_HELM_50, ItemID.DHAROKS_HELM_25, ItemID.DHAROKS_HELM_0};
	private static final int[] DHAROK_BODY = {ItemID.DHAROKS_PLATEBODY, ItemID.DHAROKS_PLATEBODY_100, ItemID.DHAROKS_PLATEBODY_75,
												ItemID.DHAROKS_PLATEBODY_50, ItemID.DHAROKS_PLATEBODY_25, ItemID.DHAROKS_PLATEBODY_0};
	private static final int[] DHAROK_LEG = {ItemID.DHAROKS_PLATELEGS, ItemID.DHAROKS_PLATELEGS_100, ItemID.DHAROKS_PLATELEGS_75,
												ItemID.DHAROKS_PLATELEGS_50, ItemID.DHAROKS_PLATELEGS_25, ItemID.DHAROKS_PLATELEGS_0};
	private static final int[] DHAROK_AXE = {ItemID.DHAROKS_GREATAXE, ItemID.DHAROKS_GREATAXE_100, ItemID.DHAROKS_GREATAXE_75,
												ItemID.DHAROKS_GREATAXE_50, ItemID.DHAROKS_GREATAXE_25, ItemID.DHAROKS_GREATAXE_0};

	private static final int[] TORAG_HELM = {ItemID.TORAGS_HELM, ItemID.TORAGS_HELM_100, ItemID.TORAGS_HELM_75,
												ItemID.TORAGS_HELM_50, ItemID.TORAGS_HELM_25, ItemID.TORAGS_HELM_0};
	private static final int[] TORAG_BODY = {ItemID.TORAGS_PLATEBODY, ItemID.TORAGS_PLATEBODY_100, ItemID.TORAGS_PLATEBODY_75,
												ItemID.TORAGS_PLATEBODY_50, ItemID.TORAGS_PLATEBODY_25, ItemID.TORAGS_PLATEBODY_0};
	private static final int[] TORAG_LEG = {ItemID.TORAGS_PLATELEGS, ItemID.TORAGS_PLATELEGS_100, ItemID.TORAGS_PLATELEGS_75,
												ItemID.TORAGS_PLATELEGS_50, ItemID.TORAGS_PLATELEGS_25, ItemID.TORAGS_PLATELEGS_0};
	private static final int[] TORAG_HAMMER = {ItemID.TORAGS_HAMMERS, ItemID.TORAGS_HAMMERS_100, ItemID.TORAGS_HAMMERS_75,
												ItemID.TORAGS_HAMMERS_50, ItemID.TORAGS_HAMMERS_25, ItemID.TORAGS_HAMMERS_0};

	private static final int[] VERAC_HELM = {ItemID.VERACS_HELM, ItemID.VERACS_HELM_100, ItemID.VERACS_HELM_75,
												ItemID.VERACS_HELM_50, ItemID.VERACS_HELM_25, ItemID.VERACS_HELM_0};
	private static final int[] VERAC_BODY = {ItemID.VERACS_BRASSARD, ItemID.VERACS_BRASSARD_100, ItemID.VERACS_BRASSARD_75,
												ItemID.VERACS_BRASSARD_50, ItemID.VERACS_BRASSARD_25, ItemID.VERACS_BRASSARD_0};
	private static final int[] VERAC_LEG = {ItemID.VERACS_PLATESKIRT, ItemID.VERACS_PLATESKIRT_100, ItemID.VERACS_PLATESKIRT_75,
												ItemID.VERACS_PLATESKIRT_50, ItemID.VERACS_PLATESKIRT_25, ItemID.VERACS_PLATESKIRT_0};
	private static final int[] VERAC_FLAIL = {ItemID.VERACS_FLAIL, ItemID.VERACS_FLAIL_100, ItemID.VERACS_FLAIL_75,
												ItemID.VERACS_FLAIL_50, ItemID.VERACS_FLAIL_25, ItemID.VERACS_FLAIL_0};

	private static final int[] GUTHAN_HELM = {ItemID.GUTHANS_HELM, ItemID.GUTHANS_HELM_100, ItemID.GUTHANS_HELM_75,
												ItemID.GUTHANS_HELM_50, ItemID.GUTHANS_HELM_25, ItemID.GUTHANS_HELM_0};
	private static final int[] GUTHAN_BODY = {ItemID.GUTHANS_PLATEBODY, ItemID.GUTHANS_PLATEBODY_100, ItemID.GUTHANS_PLATEBODY_75,
												ItemID.GUTHANS_PLATEBODY_50, ItemID.GUTHANS_PLATEBODY_25, ItemID.GUTHANS_PLATEBODY_0};
	private static final int[] GUTHAN_LEG = {ItemID.GUTHANS_CHAINSKIRT, ItemID.GUTHANS_CHAINSKIRT_100, ItemID.GUTHANS_CHAINSKIRT_75,
												ItemID.GUTHANS_CHAINSKIRT_50, ItemID.GUTHANS_CHAINSKIRT_25, ItemID.GUTHANS_CHAINSKIRT_0};
	private static final int[] GUTHAN_SPEAR = {ItemID.GUTHANS_WARSPEAR, ItemID.GUTHANS_WARSPEAR_100, ItemID.GUTHANS_WARSPEAR_75,
												ItemID.GUTHANS_WARSPEAR_50, ItemID.GUTHANS_WARSPEAR_25, ItemID.GUTHANS_WARSPEAR_0};

	private static final int[] AHRIM_HOOD = {ItemID.AHRIMS_HOOD, ItemID.AHRIMS_HOOD_100, ItemID.AHRIMS_HOOD_75,
												ItemID.AHRIMS_HOOD_50, ItemID.AHRIMS_HOOD_25, ItemID.AHRIMS_HOOD_0};
	private static final int[] AHRIM_BODY = {ItemID.AHRIMS_ROBETOP, ItemID.AHRIMS_ROBETOP_100, ItemID.AHRIMS_ROBETOP_75,
												ItemID.AHRIMS_ROBETOP_50, ItemID.AHRIMS_ROBETOP_25, ItemID.AHRIMS_ROBETOP_0};
	private static final int[] AHRIM_LEG = {ItemID.AHRIMS_ROBESKIRT, ItemID.AHRIMS_ROBESKIRT_100, ItemID.AHRIMS_ROBESKIRT_75,
												ItemID.AHRIMS_ROBESKIRT_50, ItemID.AHRIMS_ROBESKIRT_25, ItemID.AHRIMS_ROBESKIRT_0};
	private static final int[] AHRIM_STAFF = {ItemID.AHRIMS_STAFF, ItemID.AHRIMS_STAFF_100, ItemID.AHRIMS_STAFF_75,
												ItemID.AHRIMS_STAFF_50, ItemID.AHRIMS_STAFF_25, ItemID.AHRIMS_STAFF_0};

	private static final int[] KARIL_COIF = {ItemID.KARILS_COIF, ItemID.KARILS_COIF_100, ItemID.KARILS_COIF_75,
												ItemID.KARILS_COIF_50, ItemID.KARILS_COIF_25, ItemID.KARILS_COIF_0};
	private static final int[] KARIL_BODY = {ItemID.KARILS_LEATHERTOP, ItemID.KARILS_LEATHERTOP_100, ItemID.KARILS_LEATHERTOP_75,
												ItemID.KARILS_LEATHERTOP_50, ItemID.KARILS_LEATHERTOP_25, ItemID.KARILS_LEATHERTOP_0};
	private static final int[] KARIL_LEG = {ItemID.KARILS_LEATHERSKIRT, ItemID.KARILS_LEATHERSKIRT_100, ItemID.KARILS_LEATHERSKIRT_75,
												ItemID.KARILS_LEATHERSKIRT_50, ItemID.KARILS_LEATHERSKIRT_25, ItemID.KARILS_LEATHERSKIRT_0};
	private static final int[] KARIL_CROSSBOW = {ItemID.KARILS_CROSSBOW, ItemID.KARILS_CROSSBOW_100, ItemID.KARILS_CROSSBOW_75,
												ItemID.KARILS_CROSSBOW_50, ItemID.KARILS_CROSSBOW_25, ItemID.KARILS_CROSSBOW_0};

	private static final int[] TOXIC_BLOWPIPE = {ItemID.TOXIC_BLOWPIPE, ItemID.TOXIC_BLOWPIPE_EMPTY};
	private static final int[] TOXIC_STOD = {ItemID.TOXIC_STAFF_OF_THE_DEAD, ItemID.TOXIC_STAFF_UNCHARGED};
	private static final int[] SERPENTINE_HELM = {ItemID.SERPENTINE_HELM, ItemID.SERPENTINE_HELM_UNCHARGED};
	private static final int[] TRIDENT_OF_THE_SEAS = {ItemID.TRIDENT_OF_THE_SEAS_FULL, ItemID.TRIDENT_OF_THE_SEAS, ItemID.UNCHARGED_TRIDENT};
	private static final int[] TRIDENT_OF_THE_SEAS_E = {ItemID.TRIDENT_OF_THE_SEAS_E, ItemID.UNCHARGED_TRIDENT_E};
	private static final int[] TOXIC_TRIDENT = {ItemID.TRIDENT_OF_THE_SWAMP, ItemID.UNCHARGED_TOXIC_TRIDENT};
	private static final int[] TOXIC_TRIDENT_E = {ItemID.TRIDENT_OF_THE_SWAMP_E, ItemID.UNCHARGED_TOXIC_TRIDENT_E};

	private static final int[] BLACK_MASK_I = {ItemID.BLACK_MASK_10_I, ItemID.BLACK_MASK_9_I, ItemID.BLACK_MASK_8_I,
												ItemID.BLACK_MASK_7_I, ItemID.BLACK_MASK_6_I, ItemID.BLACK_MASK_5_I,
												ItemID.BLACK_MASK_4_I, ItemID.BLACK_MASK_3_I, ItemID.BLACK_MASK_2_I,
												ItemID.BLACK_MASK_1_I, ItemID.BLACK_MASK_I, 0};
	private static final int[] BLACK_MASK = {ItemID.BLACK_MASK_10, ItemID.BLACK_MASK_9, ItemID.BLACK_MASK_8,
												ItemID.BLACK_MASK_7, ItemID.BLACK_MASK_6, ItemID.BLACK_MASK_5,
												ItemID.BLACK_MASK_4, ItemID.BLACK_MASK_3, ItemID.BLACK_MASK_2,
												ItemID.BLACK_MASK_1, ItemID.BLACK_MASK, 0};
	// Farming
	private static final int[] CABBAGES = {ItemID.CABBAGES10, ItemID.CABBAGES9, ItemID.CABBAGES8, ItemID.CABBAGES7,
											ItemID.CABBAGES6, ItemID.CABBAGES5, ItemID.CABBAGES4, ItemID.CABBAGES3,
											ItemID.CABBAGES2, ItemID.CABBAGES1, ItemID.EMPTY_SACK};
	private static final int[] ONIONS = {ItemID.ONIONS10, ItemID.ONIONS9, ItemID.ONIONS8, ItemID.ONIONS7,
											ItemID.ONIONS6, ItemID.ONIONS5, ItemID.ONIONS4, ItemID.ONIONS3,
											ItemID.ONIONS2, ItemID.ONIONS1, ItemID.EMPTY_SACK};
	private static final int[] POTATOES = {ItemID.POTATOES10, ItemID.POTATOES9, ItemID.POTATOES8, ItemID.POTATOES7,
											ItemID.POTATOES6, ItemID.POTATOES5, ItemID.POTATOES4, ItemID.POTATOES3,
											ItemID.POTATOES2, ItemID.POTATOES1, ItemID.EMPTY_SACK};
	private static final int[] STRAWBERRIES = {ItemID.STRAWBERRIES5, ItemID.STRAWBERRIES4, ItemID.STRAWBERRIES3,
												ItemID.STRAWBERRIES2, ItemID.STRAWBERRIES1, ItemID.BASKET};
	private static final int[] APPLES = {ItemID.APPLES5, ItemID.APPLES4, ItemID.APPLES3,
											ItemID.APPLES2, ItemID.APPLES1, ItemID.BASKET};
	private static final int[] BANANAS = {ItemID.BANANAS5, ItemID.BANANAS4, ItemID.BANANAS3,
											ItemID.BANANAS2, ItemID.BANANAS1, ItemID.BASKET};
	private static final int[] ORANGES = {ItemID.ORANGES5, ItemID.ORANGES4, ItemID.ORANGES3,
											ItemID.ORANGES2, ItemID.ORANGES1, ItemID.BASKET};
	private static final int[] TOMATOES = {ItemID.TOMATOES5, ItemID.TOMATOES4, ItemID.TOMATOES3,
											ItemID.TOMATOES2, ItemID.TOMATOES1, ItemID.BASKET};

	// Jewellery
	private static final int[] DUELING_RING = {ItemID.RING_OF_DUELING8, ItemID.RING_OF_DUELING7, ItemID.RING_OF_DUELING6,
												ItemID.RING_OF_DUELING5, ItemID.RING_OF_DUELING4, ItemID.RING_OF_DUELING3,
												ItemID.RING_OF_DUELING2, ItemID.RING_OF_DUELING1};
	private static final int[] RETURNING_RING = {ItemID.RING_OF_RETURNING5, ItemID.RING_OF_RETURNING4, ItemID.RING_OF_RETURNING3,
													ItemID.RING_OF_RETURNING2, ItemID.RING_OF_RETURNING1};
	private static final int[] WEALTH_RING = {ItemID.RING_OF_WEALTH_5, ItemID.RING_OF_WEALTH_4, ItemID.RING_OF_WEALTH_3,
												ItemID.RING_OF_WEALTH_2, ItemID.RING_OF_WEALTH_1, ItemID.RING_OF_WEALTH};
	private static final int[] WEALTH_RING_I = {ItemID.RING_OF_WEALTH_I5, ItemID.RING_OF_WEALTH_I4, ItemID.RING_OF_WEALTH_I3,
												ItemID.RING_OF_WEALTH_I2, ItemID.RING_OF_WEALTH_I1, ItemID.RING_OF_WEALTH_I};
	private static final int[] GLORY_AMULET = {ItemID.AMULET_OF_GLORY6, ItemID.AMULET_OF_GLORY5, ItemID.AMULET_OF_GLORY4, ItemID.AMULET_OF_GLORY3,
												ItemID.AMULET_OF_GLORY2, ItemID.AMULET_OF_GLORY1, ItemID.AMULET_OF_GLORY};
	private static final int[] GLORY_AMULET_T = {ItemID.AMULET_OF_GLORY_T6, ItemID.AMULET_OF_GLORY_T5, ItemID.AMULET_OF_GLORY_T4, ItemID.AMULET_OF_GLORY_T3,
												ItemID.AMULET_OF_GLORY_T2, ItemID.AMULET_OF_GLORY_T1, ItemID.AMULET_OF_GLORY_T};
	private static final int[] BURNING_AMULET = {ItemID.BURNING_AMULET5, ItemID.BURNING_AMULET4, ItemID.BURNING_AMULET3, ItemID.BURNING_AMULET2,
													ItemID.BURNING_AMULET1};
	private static final int[] CASTLEWARS_BRACELET = {ItemID.CASTLE_WARS_BRACELET3, ItemID.CASTLE_WARS_BRACELET2, ItemID.CASTLE_WARS_BRACELET1};
	private static final int[] COMBAT_BRACELET = {ItemID.COMBAT_BRACELET6, ItemID.COMBAT_BRACELET5, ItemID.COMBAT_BRACELET4, ItemID.COMBAT_BRACELET3,
													ItemID.COMBAT_BRACELET2, ItemID.COMBAT_BRACELET1, ItemID.COMBAT_BRACELET};






	static final int[][] BASKETS = {CABBAGES, ONIONS, POTATOES, STRAWBERRIES, APPLES, BANANAS, ORANGES, TOMATOES};



	static final int[][] DEGRADABLES = {DHAROK_HELM, DHAROK_BODY, DHAROK_LEG, DHAROK_AXE,
										TORAG_HELM, TORAG_BODY, TORAG_LEG, TORAG_HAMMER,
										VERAC_HELM, VERAC_BODY, VERAC_LEG, VERAC_FLAIL,
										GUTHAN_HELM, GUTHAN_BODY, GUTHAN_LEG, GUTHAN_SPEAR,
										KARIL_BODY, KARIL_COIF, KARIL_CROSSBOW, KARIL_LEG,
										AHRIM_BODY, AHRIM_HOOD, AHRIM_LEG, AHRIM_STAFF, BLACK_MASK_I, BLACK_MASK};

    public static int[] getAllPhasesOfItem(int id)
    {
        for (int[] potion : MultiPhaseSupply.POTIONS)
        {
            if (ArrayUtils.contains(potion, id))
            {
                return potion;
            }
        }

        for (int[] stackFood : MultiPhaseSupply.FOOD)
        {
            if (ArrayUtils.contains(stackFood, id))
            {
                return stackFood;
            }
        }

        return null;
    }

    private MultiPhaseSupply() {}
}
