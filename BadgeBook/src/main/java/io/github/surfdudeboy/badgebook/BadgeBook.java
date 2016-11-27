package io.github.surfdudeboy.badgebook;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

import io.github.surfdudeboy.badgebook.command.BadgesCommand;

@Plugin(id = "badgebook", name = "Badge Book", version = "0.1")
public class BadgeBook {
    
	@Inject
	Game game;
	
	@Inject
	Logger log;
	
	CommandSpec badgeCmd = CommandSpec.builder()
			.description(Text.of("Badge Command"))
			.executor(new BadgesCommand())
			.build();
	
	@Listener
	public void onServerStart(GameStartingServerEvent e){
		log.info("Badge Book has loaded");
		Sponge.getCommandManager().register(this, badgeCmd, "hello");
		
	}
}
