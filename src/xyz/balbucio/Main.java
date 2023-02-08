package xyz.balbucio;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.balbucio.commands.AddNpcCommand;

public class Main extends JavaPlugin {
	
	private static BotAPI api;
	private static Main instance;

	@Override
	public void onEnable() {
		this.api = new BotAPI(this);
		this.getCommand("addnpc").setExecutor(new AddNpcCommand());
	}

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onDisable() {
	}
	
	

}
