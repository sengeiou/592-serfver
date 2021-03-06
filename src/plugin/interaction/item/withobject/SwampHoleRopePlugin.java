package plugin.interaction.item.withobject;

import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.item.Item;
import org.arios.plugin.Plugin;

/**
 * Represents the swamp hole rope plugin.
 * @author 'Vexia
 * @date 01/12/2013
 */
public class SwampHoleRopePlugin extends UseWithHandler {

	/**
	 * Represents the rope item.
	 */
	private static final Item ROPE = new Item(954);

	/**
	 * Constructs a new {@code SwampHoleRopePlugin.java} {@code Object}.
	 */
	public SwampHoleRopePlugin() {
		super(954);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(5947, OBJECT_TYPE, this);
		return this;
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		if (!event.getPlayer().getSavedData().getGlobalData().hasTiedLumbridgeRope()) {
			if (event.getPlayer().getInventory().remove(ROPE)) {
				event.getPlayer().getDialogueInterpreter().sendItemMessage(954, "You tie the rope to the top of the entrance and throw it down.");
				event.getPlayer().getSavedData().getGlobalData().setLumbridgeRope(true);
			}
		} else {
			event.getPlayer().getDialogueInterpreter().sendDialogue("There is already a rope tied to the entrance.");
		}
		return true;
	}

}
