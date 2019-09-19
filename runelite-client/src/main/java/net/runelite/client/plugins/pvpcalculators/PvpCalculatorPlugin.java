package net.runelite.client.plugins.pvpcalculators;

import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.events.ExperienceChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
					name = "(G) Dharok Damage Calculator",
					description = "Show maximum damage the opponent can hit. Only useful for DH pking atm.",
					tags = {"Damage", "Dharok", "Calculator"},
					enabledByDefault = false
				 )
public class PvpCalculatorPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PvpCalculatorOverlay overlay;

	@Inject
	private ScheduledExecutorService executor;

	@Getter
	private PvpPlayer pvpPlayer;

	private HashMap<Actor, PvpPlayer> cachedPlayers = new HashMap<>();

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		cachedPlayers.clear();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGIN_SCREEN)
		{
			cachedPlayers.clear();
		}
	}

	@Subscribe
	public void onExperienceChanged(ExperienceChanged event)
	{
		if (event.getSkill() == Skill.HITPOINTS)
		{
			Actor localPlayer = client.getLocalPlayer();
			Actor opponent = localPlayer == null ? null : localPlayer.getInteracting();

			if (opponent instanceof Player)
			{
				if (!cachedPlayers.containsKey(opponent))
				{
					cachedPlayers.put(opponent, new PvpPlayer(opponent, executor));
				}

				pvpPlayer = cachedPlayers.get(opponent);
			}
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (pvpPlayer != null)
		{
			Actor opponent = pvpPlayer.getPlayer();
			if (opponent.getHealth() > 0)
			{
				int lastMaxHealth = pvpPlayer.getMaxHpLevel();
				int lastRatio = opponent.getHealthRatio();
				int lastHealthScale = opponent.getHealth();
				pvpPlayer.setCurrentHp(getCurrentHp(lastMaxHealth, lastRatio, lastHealthScale));
			}
		}
	}

	private int getCurrentHp(int lastMaxHealth, int lastRatio, int lastHealthScale)
	{
		if (lastMaxHealth > 0)
		{
			// This is the reverse of the calculation of healthRatio done by the server
			// which is: healthRatio = 1 + (healthScale - 1) * health / maxHealth (if health > 0, 0 otherwise)
			// It's able to recover the exact health if maxHealth <= healthScale.
			if (lastRatio > 0)
			{
				int minHealth = 1;
				int maxHealth;
				if (lastHealthScale > 1)
				{
					if (lastRatio > 1)
					{
						// This doesn't apply if healthRatio = 1, because of the special case in the server calculation that
						// health = 0 forces healthRatio = 0 instead of the expected healthRatio = 1
						minHealth = (lastMaxHealth * (lastRatio - 1) + lastHealthScale - 2) / (lastHealthScale - 1);
					}
					maxHealth = (lastMaxHealth * lastRatio - 1) / (lastHealthScale - 1);
					if (maxHealth > lastMaxHealth)
					{
						maxHealth = lastMaxHealth;
					}
				}
				else
				{
					// If healthScale is 1, healthRatio will always be 1 unless health = 0
					// so we know nothing about the upper limit except that it can't be higher than maxHealth
					maxHealth = lastMaxHealth;
				}
				// Take the average of min and max possible healts
				return (minHealth + maxHealth + 1) / 2;
			}
		}

		return -1;
	}
}
