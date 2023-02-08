package xyz.balbucio.goal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.ai.Navigator;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.npc.NPC;
import xyz.balbucio.commands.AddNpcCommand;
import xyz.balbucio.objects.PlayerBot;

public class ObjectiveGoal extends BehaviorGoalAdapter {

	private Location obj;
	private PlayerBot bot;
	private LivingEntity entity;
	private NPC npc;

	public ObjectiveGoal(Location obj, PlayerBot bot) {
		super();
		this.obj = obj;
		this.bot = bot;
		this.npc = bot.getNpc();
		this.entity = (LivingEntity) npc.getEntity();
	}

	private Entity target;
	private Location locationTarget = obj;

	/**
	 * Checa se o bot pode atacar alguém antes de cumprir o seu objetivo
	 * 
	 * @return
	 */
	public boolean attack() {
		List<Entity> enemys = filter(entity.getNearbyEntities(3, 3, 3));
		if (target != null) {
			if (!target.isDead() && enemys.contains(target) && npc.getNavigator().canNavigateTo(target.getLocation())) {
				sendMsg("O alvo anterior continua sendo focado!");
				npc.getNavigator().setTarget(target, true);
				return true;
			}
			target = null;
			npc.getNavigator().cancelNavigation();
			sendMsg("O alvo anterior saiu do foco do bot por estar fora do alcance dele!");
		} else {
			if (!enemys.isEmpty()) {
				Entity fe = enemys.stream().filter(e -> npc.getNavigator().canNavigateTo(e.getLocation())).findAny()
						.orElse(null);
				if (fe != null) {
					sendMsg("Novo alvo para ataque adicionado!");
					target = fe;
					npc.getNavigator().setTarget(target, true);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void reset() {
		Navigator navigator = npc.getNavigator();
		navigator.getDefaultParameters().attackDelayTicks(5);
		navigator.getDefaultParameters().attackRange(2.5);
		// navigator.getDefaultParameters().baseSpeed(2f);
		navigator.getDefaultParameters().destinationTeleportMargin(-1);
		navigator.getDefaultParameters().straightLineTargetingDistance(500);
		// navigator.getDefaultParameters().

	}

	@Override
	public BehaviorStatus run() {
		reset();
		if (!attack()) {
			if (entity.getLocation().distance(obj) < 2) {
				locationTarget = bot.getSpawn();
				npc.getNavigator().setTarget(locationTarget);
				sendMsg("§aO bot chegou ao objetivo, retornando ao spawn!");
			} else if (entity.getLocation().distance(bot.getSpawn()) < 2) {
				locationTarget = obj;
				npc.getNavigator().setTarget(locationTarget);
				sendMsg("§aO bot está no spawn e irá para o objetivo agora!");
			} else {
				npc.getNavigator().setTarget(locationTarget);
			}

			if (npc.getNavigator().canNavigateTo(locationTarget)) {
				construct();
			}
		}
		return BehaviorStatus.SUCCESS;
	}

	@Override
	public boolean shouldExecute() {
		return AddNpcCommand.canStart;
	}

	public List<Entity> filter(List<Entity> l) {
		List<Entity> newlist = new ArrayList<>();
		for (Entity e : l) {
			if (e instanceof Player) {
				Player p = (Player) e;
				if (p.getGameMode() == GameMode.SURVIVAL) {
					newlist.add(e);
				}
			}
		}
		return newlist;
	}

	int delay = 10;

	/**
	 * Adiciona blocos a frente do bot para que ele possa continuar o seu caminho
	 */
	private void construct() {
		Location loc = npc.getStoredLocation().clone().subtract(0, 1, 0);
		Block of = loc.getBlock();
		
		// v = bloco da frente abaixo, verifica se ta realmente no void, se n tiver
		// fodase

		double rotation = ((loc.getYaw() - 90.0F) % 360.0F);
		if (rotation < 0.0D)
			rotation += 360.0D;
		if (45.0D <= rotation && rotation < 135.0D) { // south
			Block b = loc.clone().add(0, 0, 1).getBlock();
			Block b2 = loc.clone().add(0, 0, 2).getBlock();
			Block v = b2.getLocation().clone().subtract(0, 1, 0).getBlock();
			if (b2.isEmpty()) {
				b2.setType(Material.COBBLESTONE);
			}
			if (b.isEmpty()) {
				b.setType(Material.SANDSTONE);
			}

		} else if (225.0D <= rotation && rotation < 315.0D) { // north
			Block b = loc.clone().subtract(0, 0, 1).getBlock();
			Block b2 = loc.clone().subtract(0, 0, 2).getBlock();
			Block v = b2.getLocation().clone().subtract(0, 1, 0).getBlock();
			if (b2.isEmpty()) {
				b2.setType(Material.COBBLESTONE);
			}
			if (b.isEmpty()) {
				b.setType(Material.SANDSTONE);
			}

		} else if (135.0D <= rotation && rotation < 225.0D) { // west
			Block b = loc.clone().subtract(1, 0, 0).getBlock();
			Block b2 = loc.clone().subtract(2, 0, 0).getBlock();
			Block v = b2.getLocation().clone().subtract(0, 1, 0).getBlock();
			if (b2.isEmpty()) {
				b2.setType(Material.COBBLESTONE);
			}

			if (b.isEmpty()) {
				b.setType(Material.SANDSTONE);
			}

		} else if (0.0D <= rotation && rotation < 45.0D) { // east
			Block b = loc.clone().add(1, 0, 0).getBlock();
			Block b2 = loc.clone().add(2, 0, 0).getBlock();
			Block v = b2.getLocation().subtract(0, 1, 0).getBlock();
			if (b2.isEmpty()) {
				// b2.setType(Material.COBBLESTONE);
			}

			if (b.isEmpty()) {
				b.setType(Material.SANDSTONE);
			}
		} else if (315.0D <= rotation && rotation < 360.0D) { // east
			Block b = loc.clone().subtract(1, 0, 0).getBlock();
			Block b2 = loc.clone().subtract(2, 0, 0).getBlock();
			Block v = b2.getLocation().clone().subtract(0, 1, 0).getBlock();
			if (b2.isEmpty()) {
				b2.setType(Material.COBBLESTONE);
			}

			if (b.isEmpty()) {
				b.setType(Material.SANDSTONE);
			}
		}
	}

	public void sendMsg(String msg) {
		entity.getWorld().getPlayers().forEach(p -> p.sendMessage(msg.replace("&", "§")));
	}

}
