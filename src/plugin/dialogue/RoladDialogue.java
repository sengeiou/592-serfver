package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the RoladDialogue dialogue.
 * @author 'Vexia
 */
public class RoladDialogue extends DialoguePlugin {

	public RoladDialogue() {

	}

	public RoladDialogue(Player player) {
		super(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 1841 };
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Ehm... well... my name is " + player.getUsername() + ", if that rings any bell?");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "No, never heard of you.");
			stage = 2;
			break;
		case 2:
			end();
			break;
		}
		return true;
	}

	@Override
	public DialoguePlugin newInstance(Player player) {

		return new RoladDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Oh, hello... do I know you?");
		stage = 0;
		return true;
	}
}