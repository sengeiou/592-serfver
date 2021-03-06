package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the dialogue plugin used for the gerrant npc.
 * @author 'Vexia
 * @version 1.0
 */
public final class GerrantDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code GerrantDialogue} {@code Object}.
	 */
	public GerrantDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code GerrantDialogue} {@code Object}.
	 * @param player the player.
	 */
	public GerrantDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new GerrantDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Welcome! You can buy fishing equipment at my store.", "We'll also buy anything you catch off you.");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendOptions("Choose an option:", "Let's see what you've got then.", "Sorry, I'm not interested.");
			stage = 1;
			break;
		case 1:
			switch (buttonId) {
			case 1:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Let's see what you've got then.");
				stage = 10;
				break;
			case 2:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Sorry, I'm not interested.");
				stage = 20;
				break;

			}
			break;
		case 10:
			end();
			npc.openShop(player);
			break;
		case 20:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 558 };
	}
}