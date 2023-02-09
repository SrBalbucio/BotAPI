package xyz.balbucio;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.balbucio.goal.AttackGoal;
import xyz.balbucio.goal.ConstructGoal;
import xyz.balbucio.goal.ObjectiveGoal;
import xyz.balbucio.listener.NavigationListener;
import xyz.balbucio.objects.PlayerBot;

public class BotAPI {

	private static BotAPI instance;
	private JavaPlugin main;
	public ArrayList<PlayerBot> bots = new ArrayList<>();

	public BotAPI(JavaPlugin main) {
		this.main = main;
		instance = this;
	}
	
	public void registerListeners() {
		PluginManager manager = Bukkit.getPluginManager();
		manager.registerEvents(new NavigationListener(), main);
	}
	
	public void clear() {
		bots.stream().forEach(p -> p.getNpc().destroy());
		bots.clear();
	}

	public static BotAPI getInstance() {
		return instance;
	}

	public PlayerBot createBot(String name, Location loc) {
		return new PlayerBot(name, loc, false);
	}

	public PlayerBot createAttackBot(String name, Location loc) {
		PlayerBot bot = createBot(name, loc);
		return bot.addGoal(new AttackGoal(bot, 6));
	}
	
	public PlayerBot createConstructorBot(String name, Player player) {
		PlayerBot bot = createBot(name, player.getLocation());
		return bot.addGoal(new ConstructGoal(bot, player));
	}
	
	public PlayerBot createObjectiveBot(String name, Location loc, Location obj) {
		PlayerBot bot = createBot(name, loc);
		return bot.addGoal(new ObjectiveGoal(obj, bot));
	}

}
