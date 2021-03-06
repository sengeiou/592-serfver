package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the grimgnash dialogue plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class GrimgnashDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code GrimgnashDialogue} {@code Object}.
	 */
	public GrimgnashDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code GrimgnashDialogue} {@code Object}.
	 * @param player the player.
	 */
	public GrimgnashDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new GrimgnashDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "What you want, little human? Grimgnash hungry. Want", "tasty morsel like you!");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Like me? Why?  Who are you?");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "I Grimngnash and I hungry! Perhaps I eat you!");
			stage = 2;
			break;
		case 2:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "I'm really not that tasty. I think I should be going now.", "Goodbye.");
			stage = 3;
			break;
		case 3:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Human lucky Grimgnash too tired to hunt for food. Stupid", "wolves keep Grimgnsh awake with howling. Grimgnash", "can't sleep.");
			stage = 4;
			break;
		case 4:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 5997 };
	}

}