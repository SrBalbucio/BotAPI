package xyz.balbucio.objects;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.tree.Behavior;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.Sequence;
import net.citizensnpcs.api.npc.NPC;
import xyz.balbucio.BotAPI;

public class PlayerBot {

	private NPC npc;
	private String name;
	private Location spawn;

	public PlayerBot() {
		BotAPI.getInstance().bots.add(this);
	}

	public PlayerBot(String name, Location location, boolean persistent) {
		this();
		this.spawn = location;
		this.name = name.replace("&", "§");
		this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, this.name, location);
		npc.setFlyable(true);
		npc.setProtected(false);
	}

	public PlayerBot(String name, Location location, EntityType type, boolean persistent) {
		this();
		this.name = name.replace("&", "§");
		this.npc = CitizensAPI.getNPCRegistry().createNPC(type, this.name, location);
		npc.setFlyable(true);
		npc.setProtected(false);
	}

	public PlayerBot addGoal(BehaviorGoalAdapter goal) {
		this.npc.getDefaultGoalController().addBehavior(goal, 100);
		return this;
	}

	public NPC getNpc() {
		return npc;
	}

	public void setNpc(NPC npc) {
		this.npc = npc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.npc.setName(name);
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

}
