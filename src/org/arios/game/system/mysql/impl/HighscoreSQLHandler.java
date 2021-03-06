package org.arios.game.system.mysql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.system.mysql.SQLEntryHandler;
import org.arios.game.system.mysql.SQLManager;
import org.arios.game.world.GameWorld;

/**
 * Handles the sql handler.
 * @author Vexia
 */
public final class HighscoreSQLHandler extends SQLEntryHandler<Player> {

	/**
	 * Constructs a new {@code HighscoreSQLHandler} {@code Object}.
	 */
	public HighscoreSQLHandler(Player entry) {
		super(entry, "highscores", "username", entry.getName());
	}

	@Override
	public void parse() throws SQLException {

	}

	@Override
	public void create() throws SQLException {
		StringBuilder b = new StringBuilder("INSERT highscores(username,overall_xp,total_level,ironManMode,xp_0,xp_1,xp_2,xp_3,xp_4,xp_5,xp_6,xp_7,xp_8,xp_9,xp_10,xp_11,xp_12,xp_13,xp_14,xp_15,xp_16,xp_17,xp_18,xp_19,xp_20,xp_21,xp_22,xp_23) ");
		b.append("VALUES('" + value + "', '" + getTotalXp() + "', '" + entry.getSkills().getTotalLevel() + "', '" + entry.getIronmanManager().getMode().name() + "', ");
		int xp;
		for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
			xp = (int) entry.getSkills().getExperience(i);
			// System.out.println("xp for level " + i + " = " + xp + ", name=" +
			// Skills.SKILL_NAME[i]);
			b.append("'" + xp + "'" + (i == Skills.SKILL_NAME.length - 1 ? "" : ","));
		}
		b.append(")");
		PreparedStatement statement = connection.prepareStatement(b.toString());
		statement.executeUpdate();
		SQLManager.close(statement.getConnection());
	}

	@Override
	public void save() throws SQLException {
		if (entry.getDetails().getRights() == Rights.ADMINISTRATOR || GameWorld.getSettings().isDevMode()) {
			return;
		}
		super.read();
		if (result == null || !result.next()) {
			create();
			return;
		}
		StringBuilder b = new StringBuilder("UPDATE highscores SET overall_xp='" + getTotalXp() + "', total_level='" + entry.getSkills().getTotalLevel() + "', ironManMode='" + entry.getIronmanManager().getMode().name() + "', ");
		int xp;
		for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
			xp = (int) entry.getSkills().getExperience(i);
			// System.out.println("xp for level " + i + " = " + xp + ", name=" +
			// Skills.SKILL_NAME[i]);
			b.append("xp_" + i + "='" + xp + "'" + (i == Skills.SKILL_NAME.length - 1 ? "" : ","));
		}
		b.append("WHERE username='" + value + "'");
		PreparedStatement statement = connection.prepareStatement(b.toString());
		statement.executeUpdate();
		SQLManager.close(statement.getConnection());
	}

	/**
	 * Sets the data.
	 * @param statement the statement.
	 * @param startIndex the start index.
	 * @throws SQLException the exception.
	 */
	public void setData(PreparedStatement statement, int startIndex) throws SQLException {
		for (int i = 0; i < Skills.SKILL_NAME.length; i++) {
			statement.setInt(startIndex + i, entry.getSkills().getStaticLevel(i));
		}
	}

	@Override
	public Connection getConnection() {
		return SQLManager.getConnection();
	}

	/**
	 * Gets the total exp.
	 * @return the exp.
	 */
	public int getTotalXp() {
		int total = 0;
		for (int skill = 0; skill < Skills.SKILL_NAME.length; skill++) {
			total += entry.getSkills().getExperience(skill);
		}
		return total;
	}

}