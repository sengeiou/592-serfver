package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the RustyDialogue dialogue.
 * @author 'Vexia
 */
public class RustyDialogue extends DialoguePlugin {

	public RustyDialogue() {

	}

	public RustyDialogue(Player player) {
		super(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 3239 };
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Why are you asking?");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Um... It's a quiz. I'm asking everyone I meet if they're", "carrying anything valuable.");
			stage = 2;
			break;
		case 2:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "What would you do if I said I had loads of expensive items", "with me?");
			stage = 3;
			break;
		case 3:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Ooh, do you? It's been ages since anyone said they'd got", "anything worth stealing.");
			stage = 4;
			break;
		case 4:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "'Anything worth stealing'?");
			stage = 5;
			break;
		case 5:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Um... Not that I'd dream of stealing anything!");
			stage = 6;
			break;
		case 6:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Well, I'll say I'm not carrying anything valuable at all.");
			stage = 7;
			break;
		case 7:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Oh, what a shame.");
			stage = 8;
			break;
		case 8:
			end();
			break;
		}
		return true;
	}

	@Override
	public DialoguePlugin newInstance(Player player) {

		return new RustyDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Hiya. Are you carrying anything valuable?");
		stage = 0;
		return true;
	}
}