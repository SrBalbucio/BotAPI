package xyz.balbucio.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import xyz.balbucio.objects.PlayerBot;

public class AttackGoal extends BehaviorGoalAdapter {

	private PlayerBot bot;
	private int range;
	private Entity target;

	public AttackGoal(PlayerBot bot, int range) {
		this.bot = bot;
		this.range = range;
	}

	private List<Entity> nearbyEntities = new ArrayList<>();

	@Override
	public void reset() {
		nearbyEntities = filter(bot.getNpc().getEntity().getNearbyEntities(range, range, range)); // Recarrega a lista
																									// de entidades por
																									// perto
		if (target != null) { // se o inimigo for diferente de null
			if (!nearbyEntities.contains(target)) { // verifica se ele ainda esta por perto
				bot.getNpc().getNavigator().cancelNavigation();
				target = null; // se nao tiver remove
			}
		}
	}

	private List<Entity> filter(List<Entity> list) {
		List<Entity> finallist = new ArrayList<>();
		for (Entity e : list) {
			if (e instanceof Player && ((Player) e).getGameMode() == GameMode.SURVIVAL) {
				finallist.add(e);
			}
		}
		return finallist;
	}

	@Override
	public BehaviorStatus run() {
		reset();
		if (target != null) { // se o bot ja tiver um inimigo a vista mantenha o foco nele
			bot.getNpc().getNavigator().setTarget(target, true); // corre atras e bate no fdp
			return BehaviorStatus.SUCCESS;
		} else {
			if (nearbyEntities.isEmpty()) { // se n tem ngn pra bater ignora
				return BehaviorStatus.SUCCESS;
			}
			this.target = nearbyEntities.stream().findAny().get(); // seleciona um randola pra bater
			bot.getNpc().getNavigator().setTarget(target, true); // corre atras e bate
		}
		return BehaviorStatus.SUCCESS;
	}

	@Override
	public boolean shouldExecute() {
		return target != null || !bot.getNpc().getEntity().getNearbyEntities(range, range, range).isEmpty();
	}

}
