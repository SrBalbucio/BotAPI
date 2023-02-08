package xyz.balbucio.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.ai.event.NavigationBeginEvent;
import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.ai.event.NavigationReplaceEvent;

public class NavigationListener implements Listener {
	
	@EventHandler
	public void onStart(NavigationBeginEvent evt) {
	}
	
	@EventHandler
	public void onComplete(NavigationCompleteEvent evt) {
		
	}
	
	@EventHandler
	public void onChange(NavigationReplaceEvent evt) {
		
	}
	
	@EventHandler
	public void onCancel(NavigationCancelEvent evt) {
		
	}

}
