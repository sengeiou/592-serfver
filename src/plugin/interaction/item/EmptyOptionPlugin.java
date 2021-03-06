package plugin.interaction.item;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.global.consumable.Consumable;
import org.arios.game.content.global.consumable.Consumables;
import org.arios.game.content.global.consumable.Drink;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to handle the emptying of an item.
 * @author 'Vexia
 * @date 23/12/2013
 */
public final class EmptyOptionPlugin extends OptionHandler {

	/**
	 * Represents the vial consumable.
	 */
	private static final VialConsumable VIAL_CONSUMABLE = new VialConsumable();

	@Override
	public boolean handle(Player player, Node node, String option) {
		Consumable consumable = Consumables.forConsumable(((Item) node));
		if (consumable == null) {
			String name = node.getName().toLowerCase();
			if (name.contains("(unf)")) {
				consumable = VIAL_CONSUMABLE;
			}
			consumable = name.contains("potion") || name.contains("+") || name.contains("mix") || name.equals("plant cure") ? VIAL_CONSUMABLE : new Drink(((Item) node), null);
		}
		consumable.interact(player, node, option);
		return true;
	}

	@Override
	public boolean isWalk() {
		return false;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.setOptionHandler("empty", this);
		ItemDefinition.setOptionHandler("empty dish", this);
		return this;
	}

	/**
	 * Represents a vial consumable.
	 * @author 'Vexia
	 * @date 23/12/2013
	 */
	public static class VialConsumable extends Consumable {

		/**
		 * Constructs a new {@code VialConsumable} {@code Object}.
		 * @param item the item.
		 */
		public VialConsumable() {
			super(null, null);
		}

		public String getEmptyMessage(Item item) {
			return "You empty the vial.";
		}

		@Override
		public Item getEmptyItem() {
			return VIAL;
		}
	}
}