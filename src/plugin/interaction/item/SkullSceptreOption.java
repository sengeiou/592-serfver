package plugin.interaction.item;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.TeleportManager;
import org.arios.game.node.entity.player.link.diary.DiaryType;
import org.arios.game.node.item.Item;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Handles the skull sceptre options.
 * @author 'Vexia
 */
public class SkullSceptreOption extends OptionHandler {

	@Override
	public boolean handle(Player player, Node node, String option) {
		Item item = (Item) node;
		switch (option) {
		case "invoke":
			if (player.getTeleporter().send(Location.create(3081, 3421, 0), TeleportManager.TeleportType.NORMAL, 1)) {
				if (!player.getAchievementDiaryManager().getDiary(DiaryType.VARROCK).isComplete(2, 1)) {
					player.getAchievementDiaryManager().getDiary(DiaryType.VARROCK).updateTask(player, 2, 1, true);
				}
				item.setCharge(item.getCharge() - 200);
				if (item.getCharge() < 1) {
					player.getInventory().remove(item);
					player.getPacketDispatch().sendMessage("Your staff crumbles to dust as you use its last charge.");
				}
			}
			break;
		case "divine":
			if (item.getCharge() < 1) {
				player.getPacketDispatch().sendMessage("You don't have enough charges left.");
				return true;
			}
			player.getPacketDispatch().sendMessage("Concentrating deeply, you divine that the sceptre has " + (item.getCharge() / 200) + " charges left.");
			break;
		}
		return true;
	}

	@Override
	public boolean isWalk() {
		return false;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.forId(9013).getConfigurations().put("option:invoke", this);
		ItemDefinition.forId(9013).getConfigurations().put("option:divine", this);
		return this;
	}
}
