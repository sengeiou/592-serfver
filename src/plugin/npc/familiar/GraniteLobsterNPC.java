package plugin.npc.familiar;

import org.arios.game.content.skill.SkillBonus;
import org.arios.game.content.skill.Skills;
import org.arios.game.content.skill.free.fishing.Fish;
import org.arios.game.content.skill.member.summoning.familiar.Familiar;
import org.arios.game.content.skill.member.summoning.familiar.FamiliarSpecial;
import org.arios.game.content.skill.member.summoning.familiar.Forager;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.impl.Projectile;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.tools.RandomFunction;

/**
 * Represents the Granite Lobster familiar.
 * @author Aero
 */
public class GraniteLobsterNPC extends Forager {

	/**
	 * The fish.
	 */
	private static final Item[] FISH = new Item[] { new Item(383), new Item(377) };

	/**
	 * Constructs a new {@code GraniteLobsterNPC} {@code Object}.
	 */
	public GraniteLobsterNPC() {
		this(null, 6849);
	}

	/**
	 * Constructs a new {@code GraniteLobsterNPC} {@code Object}.
	 * @param owner The owner.
	 * @param id The id.
	 */
	public GraniteLobsterNPC(Player owner, int id) {
		super(owner, id, 4700, 12069, 6);
		boosts.add(new SkillBonus(Skills.FISHING, 4));
	}

	@Override
	public Familiar construct(Player owner, int id) {
		return new GraniteLobsterNPC(owner, id);
	}

	@Override
	protected boolean specialMove(FamiliarSpecial special) {
		Entity target = special.getTarget();
		if (!canCombatSpecial(target)) {
			return false;
		}
		animate(new Animation(8118));
		graphics(Graphics.create(1351));
		Projectile.ranged(this, target, 1352, 60, 40, 1, 45).send();
		sendFamiliarHit(target, 14);
		return true;
	}

	@Override
	public void handlePassiveAction() {
		if (RandomFunction.random(4) == 1) {
			final Item item = FISH[RandomFunction.random(FISH.length)];
			animate(Animation.create(8107));
			Fish fish = Fish.forItem(item);
			owner.getSkills().addExperience(Skills.FISHING, fish.getExperience() * 0.10);
			produceItem(item);
		}
	}

	@Override
	public int[] getIds() {
		return new int[] { 6849, 6850 };
	}

}