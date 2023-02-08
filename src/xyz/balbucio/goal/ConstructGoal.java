package xyz.balbucio.goal;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.hamcrest.core.CombinableMatcher.CombinableBothMatcher;

import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import xyz.balbucio.objects.PlayerBot;

public class ConstructGoal extends BehaviorGoalAdapter {

	private Player player;
	private PlayerBot bot;

	public ConstructGoal(PlayerBot bot, Player player) {
		this.bot = bot;
		this.player = player;
	}

	@Override
	public void reset() {

	}

	int delay = 10;

	@Override
	public BehaviorStatus run() {
		if (delay == 0) {
			bot.getNpc().getNavigator().setTarget(player.getLocation());
			LivingEntity e = (LivingEntity) bot.getNpc().getEntity();
			Location loc = e.getLocation().clone().subtract(0, 1, 0);

			double rotation = ((loc.getYaw() - 90.0F) % 360.0F);
			if (rotation < 0.0D)
				rotation += 360.0D;
			if (45.0D <= rotation && rotation < 135.0D) { // south
				Block b = loc.clone().add(0, 0, 1).getBlock();
				if (b.isEmpty()) {
					b.setType(Material.DIAMOND_BLOCK);
				} 
			} else if (225.0D <= rotation && rotation < 315.0D) { // north
				Block b = loc.clone().subtract(0, 0, 1).getBlock();
				if (b.isEmpty()) {
					b.setType(Material.DIAMOND_BLOCK);
				}
			} else if (135.0D <= rotation && rotation < 225.0D) { // west
				Block b = loc.clone().subtract(1, 0, 0).getBlock();
				if (b.isEmpty()) {
					b.setType(Material.DIAMOND_BLOCK);
				}
			} else if (0.0D <= rotation && rotation < 45.0D) { // east
				Block b = loc.clone().add(1, 0, 0).getBlock();
				if (b.isEmpty()) {
					b.setType(Material.DIAMOND_BLOCK);
				}
			} else if (315.0D <= rotation && rotation < 360.0D) { // east
				Block b = loc.clone().subtract(1, 0, 0).getBlock();
				if (b.isEmpty()) {
					b.setType(Material.DIAMOND_BLOCK);
				}
			}
			delay = 5;
		}
		delay--;
		return BehaviorStatus.RUNNING;
	}

	@Override
	public boolean shouldExecute() {
		return player.getGameMode() == GameMode.SURVIVAL;
	}

}
