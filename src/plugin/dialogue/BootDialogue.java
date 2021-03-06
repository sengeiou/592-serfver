package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the dialogue plugin used with the boot npc.
 * @author 'Vexia
 * @version 1.0
 */
public final class BootDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code BootDialogue} {@code Object}.
	 */
	public BootDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code BootDialogue} {@code Object}.
	 * @param player the player.
	 */
	public BootDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new BootDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Hello tall person.");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendOptions("Select an Option", "Hello short person.", "Why are you called boot?");
			stage = 1;
			break;
		case 1:
			switch (buttonId) {
			case 1:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Hello short person.");
				stage = 10;
				break;
			case 2:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Why are you called boot?");
				stage = 20;
				break;
			}
			break;
		case 10:
			end();
			break;
		case 20:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "I'm called Boot, because when I was very young, I", "used to sleep, in a large boot.");
			stage = 21;
			break;
		case 21:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 665 };
	}
}