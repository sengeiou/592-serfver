package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the TostigDialogue dialogue.
 * @author 'Vexia
 */
public class TostigDialogue extends DialoguePlugin {

	public TostigDialogue() {

	}

	public TostigDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {

		return new TostigDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.NORMAL, "Hi, what ales are you serving?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Well sir, our speciality is Asgarnian Ale, we also serve", "Wizard's Mind Bomb and Dwarven Stout.");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Would you like to buy a drink?");
			stage = 2;
			break;
		case 2:
			interpreter.sendOptions("Select An Option", "Yes, please.", "No, thanks.");
			stage = 3;
			break;
		case 3:
			switch (buttonId) {
			case 1:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Yes, please.");
				stage = 100;
				break;
			case 2:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "No, thanks.");
				stage = 4;
				break;
			}
			break;
		case 4:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Ah well... so um... does the grey squirrel sing in the", "grove?");
			stage = 5;
			break;
		case 5:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Huh?");
			stage = 6;
			break;
		case 6:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Er... nevermind.");
			stage = 7;
			break;
		case 7:
			end();
			break;
		case 100:
			end();
			npc.openShop(player);
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 1079 };
	}
}