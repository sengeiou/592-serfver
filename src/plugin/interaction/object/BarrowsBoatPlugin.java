package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.component.Component;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Handles the barrows boat plugin.
 * @author Vexia
 */
public class BarrowsBoatPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(6970).getConfigurations().put("option:board", this);
		ObjectDefinition.forId(6969).getConfigurations().put("option:board", this);
		return this;
	}

	@Override
	public boolean handle(final Player player, Node node, String option) {
		switch (option) {
		case "board":
			final Location dest = node.getId() == 6970 ? new Location(3522, 3285, 0) : new Location(3500, 3380, 0);
			final String name = node.getId() == 6970 ? "Mort'on." : "the swamp";
			player.lock();
			player.getInterfaceManager().open(new Component(321));
			GameWorld.submit(new Pulse(7, player) {

				@Override
				public boolean pulse() {
					player.unlock();
					player.teleport(dest);
					player.getInterfaceManager().close();
					player.getDialogueInterpreter().sendDialogue("You arrive at " + name + ".");
					return true;
				}

			});
			break;
		}
		return true;
	}

}
