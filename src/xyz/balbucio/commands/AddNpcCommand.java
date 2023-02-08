package xyz.balbucio.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.balbucio.BotAPI;

public class AddNpcCommand implements CommandExecutor {

	private Location objective;
	private List<Location> objectives = new ArrayList<>();
	public static boolean canStart = false;

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;
		if (args.length == 0) {
			BotAPI.getInstance().createBot("Normal Bot", player.getLocation());
			return false;
		}

		String arg = args[0];
		if (arg.equalsIgnoreCase("bw")) {
			BotAPI.getInstance().createBot("BW Bot", player.getLocation());
		} else if (arg.equalsIgnoreCase("attack")) {
			BotAPI.getInstance().createAttackBot("Attack Bot", player.getLocation());
		} else if (arg.equalsIgnoreCase("construct")) {
			BotAPI.getInstance().createConstructorBot("Construct Bot", player);
		} else if (arg.equalsIgnoreCase("objective")) {
			if (objective != null) {
				BotAPI.getInstance().createObjectiveBot("Objective Bot", player.getLocation(), objective);
			} else {
				sender.sendMessage("§cSete o objetivo!");
			}
		} else if(arg.equalsIgnoreCase("setobjective")) {
			objective = player.getLocation();
			sender.sendMessage("§aSetado!");
		} else if(arg.equalsIgnoreCase("addobjective")) {
			objectives.add(player.getLocation());
			sender.sendMessage("§aAdicionado!");
		} else if(arg.equalsIgnoreCase("start")) {
			if(objective== null) {
				sender.sendMessage("§cAdicione um objetivo!");
			} else {
				canStart = !canStart;
				sender.sendMessage(canStart ? "§aObjetivos iniciados!" : "§cObjetivos travados!");
			}
		}
		return false;
	}

}
