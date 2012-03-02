package de.xghostkillerx.cookme;

import java.sql.Timestamp;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * CookMePlayerListener
 * Handles the players activities!
 *
 * Refer to the forum thread:
 * http://bit.ly/cookmebukkit
 * Refer to the dev.bukkit.org page:
 * http://bit.ly/cookmebukkitdev
 *
 * @author xGhOsTkiLLeRx
 * @thanks nisovin for his awesome code snippet!
 *
 */

public class CookMePlayerListener implements Listener {

	public CookMe plugin;
	public CookMePlayerListener(CookMe instance) {
		plugin = instance;
	}
	private boolean message = true;
	private String effect;

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		// Check if player is affected
		if (!player.hasPermission("cookme.safe")) {
			// Check for item & right clicking
			if (sameItem(player.getItemInHand().getTypeId()) == true && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				// No block like bed, chest, etc...
				if (CookMe.config.getBoolean("configuration.noBlocks") == true) {
					if (event.hasBlock()) {
						if (event.getClickedBlock().getType() == Material.BED
								|| event.getClickedBlock().getType() == Material.BED_BLOCK
								|| event.getClickedBlock().getType() == Material.FENCE_GATE
								|| event.getClickedBlock().getType() == Material.FURNACE
								|| event.getClickedBlock().getType() == Material.BURNING_FURNACE
								|| event.getClickedBlock().getType() == Material.CHEST
								|| event.getClickedBlock().getType() == Material.WORKBENCH
								|| event.getClickedBlock().getType() == Material.IRON_DOOR
								|| event.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK
								|| event.getClickedBlock().getType() == Material.WOODEN_DOOR
								|| event.getClickedBlock().getType()== Material.WOOD_DOOR)
							return;
					}
				}
				// If the player is in cooldown phase cancel it
				if (!CooldownManager.hasCooldown(player, now)) {
					// Check for food level
					if (player.getFoodLevel() != 20) {
						int randomNumber = (int)(Math.random()*29) +1, randomEffectStrength = (int)(Math.random()*16);
						int minimum = 20*CookMe.config.getInt("configuration.duration.min"), maximum = 20*CookMe.config.getInt("configuration.duration.max");
						int randomEffectTime = (int)(Math.random() * ((maximum - minimum)  + 1)  + minimum);
						// Player gets random damage, stack minus 1
						if (CookMe.config.getBoolean("effects.damage") == true) {
							if ((randomNumber == 1) || (randomNumber == 14)) {
								int randomDamage = (int) (Math.random()*9) +1;
								effect = plugin.localization.getString("damage");
								message(player, effect);
								decreaseItem(player, event);
								player.damage(randomDamage);
							}
						}
						// Food bar turns green (poison)
						if (CookMe.config.getBoolean("effects.hungervenom") == true) {
							if ((randomNumber == 2 ) || (randomNumber == 15)) {
								effect = plugin.localization.getString("hungervenom");
								message(player, effect);
								decreaseItem(player, event);
								player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, randomEffectTime, randomEffectStrength));
							}
						}
						// Player dies, stack minus 1
						if (CookMe.config.getBoolean("effects.death") == true) {
							if (randomNumber == 4) {
								effect = plugin.localization.getString("death");
								message(player, effect);
								decreaseItem(player, event);
								player.setHealth(0);
							}
						}
						// Random venom damage (including green hearts :) )
						if (CookMe.config.getBoolean("effects.venom") == true) {
							if ((randomNumber == 5) || (randomNumber == 16)) {
								effect = plugin.localization.getString("venom");
								message(player, effect);
								decreaseItem(player, event);
								player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, randomEffectTime, randomEffectStrength));
							}
						}
						// Sets the food level down. Stack minus 1
						if (CookMe.config.getBoolean("effects.hungerdecrease") == true) {
							if ((randomNumber == 6) || (randomNumber == 17)) {
								int currentFoodLevel = player.getFoodLevel(), randomFoodLevel = (int)(Math.random()*currentFoodLevel);
								effect = plugin.localization.getString("hungerdecrease");
								message(player, effect);
								decreaseItem(player, event);
								player.setFoodLevel(randomFoodLevel);
							}
						}
						// Confusion
						if (CookMe.config.getBoolean("effects.confusion") == true) {
							if ((randomNumber == 7) || (randomNumber == 18)) {
								effect = plugin.localization.getString("confusion");
								message(player, effect);
								decreaseItem(player, event);
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, randomEffectTime, randomEffectStrength));
							}
						}
						// Blindness
						if (CookMe.config.getBoolean("effects.blindness") == true) {
							if ((randomNumber == 8) || (randomNumber == 19)) {
								effect = plugin.localization.getString("blindness");
								message(player, effect);
								decreaseItem(player, event);
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, randomEffectTime, randomEffectStrength));
							}
						}
						// Weakness
						if (CookMe.config.getBoolean("effects.weakness") == true) {
							if ((randomNumber == 9) || (randomNumber == 20)) {
								effect = plugin.localization.getString("weakness");
								message(player, effect);
								decreaseItem(player, event);
								player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, randomEffectTime, randomEffectStrength));
							}
						}
						// Slowness
						if (CookMe.config.getBoolean("effects.slowness") == true) {
							if ((randomNumber == 10) || (randomNumber == 21)) {
								effect = plugin.localization.getString("slowness");
								message(player, effect);
								decreaseItem(player, event);
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, randomEffectTime, randomEffectStrength));
							}
						}
						// Slowness for blocks
						if (CookMe.config.getBoolean("effects.slowness_blocks") == true) {
							if ((randomNumber == 11) || (randomNumber == 22)) {
								effect = plugin.localization.getString("slowness_blocks");
								message(player, effect);
								decreaseItem(player, event);
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, randomEffectTime, randomEffectStrength));
							}
						}
						// Instant Damage
						if (CookMe.config.getBoolean("effects.instant_damage") == true) {
							if (randomNumber == 12 || (randomNumber == 23)) {
								effect = plugin.localization.getString("instant_damage");
								message(player, effect);
								decreaseItem(player, event);
								player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, randomEffectTime, randomEffectStrength));
							}
						}
						// Refusing
						if (CookMe.config.getBoolean("effects.refusing") == true) {
							if (randomNumber == 13 || (randomNumber == 24)) {
								effect = plugin.localization.getString("refusing");
								message(player, effect);
								event.setCancelled(true);
							}
						}
						// Add player to cooldown list
						if (CookMe.config.getInt("configuration.cooldown") != 0) CooldownManager.addPlayer(player);
					}
				}
			}
		}
	}

	private void message(Player player, String message) {
		if (CookMe.config.getBoolean("configuration.messages") == true) {
			plugin.message(null, player, message, null);
		}
	}

	// Is the item in the list? Yes or no
	private boolean sameItem(int item) {
		for (int i = 0; i < plugin.itemList.size(); i++) {
			String itemName = plugin.itemList.get(i);
			try {
				Material material = Material.valueOf(itemName);
				if (material.getId() == item) {
					return true;
				}
			}
			catch (Exception e) {
				// Prevent spamming
				if (message == true) {
					CookMe.log.warning("CookMe couldn't load the foods! Please check your config!");
					message = false;
				}
			}
		}
		return false;
	}
	
	// Sets the raw food -1
	@SuppressWarnings("deprecation")
	public void decreaseItem (Player player, PlayerInteractEvent event) {
		ItemStack afterEating = player.getItemInHand();
		if (afterEating.getAmount() == 1) {
			player.setItemInHand(null);
		}
		else {
			afterEating.setAmount(afterEating.getAmount() -1);
			player.setItemInHand(afterEating);
		}
		player.updateInventory();
		event.setCancelled(true);
	}
}
