package plugin.npc;

import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.world.map.Location;
import org.arios.tools.RandomFunction;

/**
 * Handles the NPC skippy.
 * @author Vexia
 */
public class SkippyNPC extends AbstractNPC {

	/**
	 * Constructs a new {@code SkippyNPC} {@code Object}
	 * @param id the id.
	 * @param location the location.
	 */
	public SkippyNPC(int id, Location location) {
		super(id, location);
	}

	/**
	 * Constructs a new {@code SkippyNPC} {@code Object}
	 */
	public SkippyNPC() {
		this(2796, null);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new SkippyNPC(id, location);
	}

	@Override
	public void tick() {
		if (RandomFunction.random(100) < 8) {
			sendChat("You can skip the tutorial by talking to me!");
		}
	}

	@Override
	public int[] getIds() {
		return new int[] { 2796 };
	}

}
