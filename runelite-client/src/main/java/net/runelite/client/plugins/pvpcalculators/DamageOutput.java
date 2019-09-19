package net.runelite.client.plugins.pvpcalculators;
/**
 * Created by Hamza on 2017-09-22.
 */
/*A doc comment is written in HTML and must precede a class, field, constructor or method declaration. It is made up of
 two parts -- a description followed by block tags. In this example, the block tags are @param, @return, and @see.

Javadoc makes an API documentation of your own class using your own descriptions
*/


/**
 * This class's purpose is to output the maximum damage a Player can do based on their
 * stats. Its sole purpose is to calculate the damage and nothing else.
 *
 * @author Hamza Khurram *
 * @version 1.0
 * @since 2017-10-03
 */
final class DamageOutput
{
	// DOUBLE CONSTANTS
	private static final double BURST_OF_STRENGTH_MULTIPLIER = 1.05;
	private static final double SUPERHUMAN_STRENGTH_MULTIPLIER = 1.10;
	private static final double ULTIMATE_STRENGTH_MULTIPLIER = 1.15;
	private static final double CHIVALRY_MULTIPLIER = 1.18;
	private static final double PIETY_MULTIPLIER = 1.23;

	private DamageOutput()
	{
	}

	/**
	 * The {@code getSuperStrengthPotionBonus} consumes an integer, {@code strength}, to produce an integer which
	 * depicts the extra strength levels a super strength potion will give the player.
	 *
	 * @param strength
	 * @return The integer value of the strength bonus a super strength potion will yield, given the Player's strength
	 * level.
	 */
	private static int getSuperStrengthPotionBonus(int strength)
	{
		int potionStrengthBonus = (int) (5 + (0.15 * strength));
		return potionStrengthBonus;
	}

	/**
	 * The {@code calculateDamage} method is the central method that does the main calculation.
	 * <p>
	 * It consumes three integers, {@code strength}, {@code prayer} and {@code wornStrengthBonus},
	 * to produce an integers that outputs the maximum damage possible.
	 *
	 * @param strength          The Player's strength level
	 * @param prayer            The Player's prayer level
	 * @param wornStrengthBonus The Player's strength bonus from their equipment level
	 * @return The maximum possible damage output the player can produce, given their stats and equipment.
	 */
	public static int calculateMaxDamage(int strength, int prayer, int wornStrengthBonus)
	{
		double damage = 0.5 + (effectiveLevel(strength, prayer) * ((wornStrengthBonus + 64) / 640.0));
		return (int) damage;
	}

	/**
	 * The {@code effectiveLevel} method consumes two integers, {@code strength} and {@code prayer}, to produce an
	 * integer value representing the player's effective strength level.
	 * <p>
	 * Note: Identical to the website's formula, checked already.
	 *
	 * @param strength
	 * @param prayer
	 * @return The effective strength level of the Player.
	 */
	private static int effectiveLevel(int strength, int prayer)
	{
		int physicalLevel = ((((int) ((strength + getSuperStrengthPotionBonus(strength)) * getPrayerBonusMultiplier(prayer))) + 3) + 8);
		return physicalLevel;
	}

	/**
	 * {@code getPrayerBonusMultiplier} consumes an integer, prayer, to produce a double which represents the
	 * strength level multiplier the given player possesses, based on their prayer level.
	 *
	 * @param prayer
	 * @return The appropriate prayer multiplier based on your prayer level.
	 */
	private static double getPrayerBonusMultiplier(int prayer)
	{
		if (prayer >= 70)
		{
			return PIETY_MULTIPLIER;
		}
		else if (prayer >= 60)
		{
			return CHIVALRY_MULTIPLIER;
		}
		else if (prayer >= 31)
		{
			return ULTIMATE_STRENGTH_MULTIPLIER;
		}
		else if (prayer >= 13)
		{
			return SUPERHUMAN_STRENGTH_MULTIPLIER;
		}
		else if (prayer >= 4)
		{
			return BURST_OF_STRENGTH_MULTIPLIER;
		}
		else
		{
			return 1;
		}
	}

	/**
	 * {@code dharokDamageMultiplier} consumes two ints, {@code currentHP} and {@code maxHP}, to produce a double that
	 * represents the bonus damage multiplier the Dharok armour offers.
	 *
	 * @param currentHP
	 * @param maxHP
	 * @return The Dharok-set damage multiplier.
	 */
	private static double dharokDamageMultiplier(int currentHP, int maxHP)
	{
		return (1 + ((maxHP - currentHP) / 100.0 * maxHP / 100.0));
	}

	/**
	 * {@code calculateMaximumDharokDamage} consumes 5 ints, {@code str}, {@code pray}, {@code currentHP}, {@code maxHP}
	 * and {@code wornStrengthBonus}, to produce the maximum damage possible, based on these parameters.
	 *
	 * @param str
	 * @param pray
	 * @param currentHP
	 * @param maxHP
	 * @param wornStrengthBonus
	 * @return Maximum damage possible with given parameters.
	 */
	public static int calculateMaximumDharokDamage(int str, int pray, int currentHP, int maxHP, int wornStrengthBonus)
	{
		return (int) (calculateMaxDamage(str, pray, wornStrengthBonus) * dharokDamageMultiplier(currentHP, maxHP));
	}

	/**
	 * {@code getDragonClawsSpecDMG} consumes an integer, {@code maxHit}, to produce the maximum damage the dragon claws
	 * special attack should output.
	 * <p>
	 * Note: This may be slightly inaccurate. Haven't really tested. Read some weird shit about dragon claws damage.
	 *
	 * @param maxHit
	 * @return Max hit Dragon Claws can spec out.
	 */
	public static int getDragonClawsSpecDMG(int maxHit)
	{
		return maxHit + (maxHit / 2) + ((maxHit / 4) * 2);
	}

	/**
	 * {@code getAGSSpecDMG} consumes an integer, {@code maxHit}, to produce the maximum damage the AGS
	 * special attack should output.
	 *
	 * @param maxHit
	 * @return Max hit AGS can spec out.
	 */
	public static int getAgsSpecDMG(int maxHit)
	{
		return (int) ((Math.floor(maxHit * 1.1)) * 1.25);
	}
}
