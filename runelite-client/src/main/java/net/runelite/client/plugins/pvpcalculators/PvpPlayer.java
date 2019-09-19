package net.runelite.client.plugins.pvpcalculators;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import lombok.Getter;
import net.runelite.api.Actor;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.PlayerComposition;
import net.runelite.api.kit.KitType;
import net.runelite.client.game.ItemVariationMapping;
import net.runelite.client.plugins.pvpcalculators.data.PvpMeleeEquipment;
import net.runelite.http.api.hiscore.HiscoreClient;
import net.runelite.http.api.hiscore.HiscoreEndpoint;
import net.runelite.http.api.hiscore.HiscoreResult;

class PvpPlayer
{
	@Getter
	private Actor player;

	@Getter
	private int currentHp;

	@Getter
	private int maxHpLevel;

	@Getter
	private int maxAgsSpec;

	@Getter
	private int maxDharokAxe;

	private int strengthLevel;
	private int prayLevel;
	private int strBonusNoWep;

	private final HiscoreClient hiscoreClient = new HiscoreClient();

	PvpPlayer(Actor player, ScheduledExecutorService executor)
	{
		this.player = player;
		setEquipmentBonus(player);

		executor.execute(this::getStats);
	}

	private void getStats()
	{
		HiscoreResult result = null;

		try
		{
			result = hiscoreClient.lookup(player.getName(),  HiscoreEndpoint.NORMAL);
		}
		catch (IOException ex)
		{
			System.out.println(("Error fetching Hiscore data " + ex.getMessage()));
			return;
		}

		if (result == null)
			return;

		strengthLevel = result.getStrength().getLevel();
		prayLevel = result.getPrayer().getLevel();
		maxHpLevel = result.getHitpoints().getLevel();

		if (strengthLevel > 0)
		{
			setMaxAgsSpec();
		}
	}

	private int getStrBonus(Integer strBonus)
	{
		if (strBonus == null)
			return 0;

		return strBonus;
	}

	private void setEquipmentBonus(Actor player)
	{
		PlayerComposition playerComp = ((Player) player).getPlayerComposition();

		int capeID = ItemVariationMapping.map(playerComp.getEquipmentId(KitType.CAPE));
		int amuletId = ItemVariationMapping.map(playerComp.getEquipmentId(KitType.AMULET));
		int torsoId = ItemVariationMapping.map(playerComp.getEquipmentId(KitType.TORSO));
		int shieldId = ItemVariationMapping.map(playerComp.getEquipmentId(KitType.SHIELD));
		int legId = ItemVariationMapping.map(playerComp.getEquipmentId(KitType.LEGS));
		int headId = ItemVariationMapping.map(playerComp.getEquipmentId(KitType.HEAD));
		int handId = ItemVariationMapping.map(playerComp.getEquipmentId(KitType.HANDS));
		int bootId = ItemVariationMapping.map(playerComp.getEquipmentId(KitType.BOOTS));
		int ringId = 0; // Get from config

		int capeStr = getStrBonus(PvpMeleeEquipment.capeMap.get(capeID));
		int amuletStr = getStrBonus(PvpMeleeEquipment.amuletMap.get(amuletId));
		int torsoStr = getStrBonus(PvpMeleeEquipment.torsoMap.get(torsoId));
		int shieldStr = getStrBonus(PvpMeleeEquipment.shieldMap.get(shieldId));
		int legStr = getStrBonus(PvpMeleeEquipment.legMap.get(legId));
		int headStr = getStrBonus(PvpMeleeEquipment.headMap.get(headId));
		int handStr = getStrBonus(PvpMeleeEquipment.handMap.get(handId));
		int bootStr = getStrBonus(PvpMeleeEquipment.bootMap.get(bootId));
		int ringStr = 0; // Config

		strBonusNoWep = capeStr + amuletStr + torsoStr + shieldStr + legStr + headStr + handStr + bootStr + ringStr;
	}

	void setCurrentHp(int currentHp)
	{
		if (currentHp > 0)
		{
			this.currentHp = currentHp;
			setMaxDharokHit(currentHp);
		}
	}

	private void setMaxAgsSpec()
	{
		int totalStrBonus = strBonusNoWep + PvpMeleeEquipment.weaponMap.get(ItemID.ARMADYL_GODSWORD);
		int maxWhack = DamageOutput.calculateMaxDamage(strengthLevel, prayLevel, totalStrBonus);
		maxAgsSpec = DamageOutput.getAgsSpecDMG(maxWhack);
	}

	private void setMaxDharokHit(int currentHp)
	{
		if (currentHp <= 0)
		{
			maxDharokAxe = -1;
			return;
		}

		int totalStrBonus = this.strBonusNoWep + PvpMeleeEquipment.weaponMap.get(ItemID.DHAROKS_GREATAXE);
		maxDharokAxe = DamageOutput.calculateMaximumDharokDamage(strengthLevel, prayLevel, currentHp, maxHpLevel, totalStrBonus);
	}
}
