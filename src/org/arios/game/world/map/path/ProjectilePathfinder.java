package org.arios.game.world.map.path;

import java.util.ArrayList;
import java.util.List;

import org.arios.game.world.map.Direction;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.Point;
import org.arios.game.world.map.RegionManager;

/**
 * A pathfinder implementation used for checking projectile paths.
 * @author Emperor
 */
public final class ProjectilePathfinder extends Pathfinder {

	/**
	 * If a path can be found.
	 */
	private boolean found;

	/**
	 * The plane.
	 */
	private int z;

	/**
	 * The x-coordinate.
	 */
	private int x;

	/**
	 * The y-coordinate.
	 */
	private int y;

	@Override
	public Path find(Location start, int size, Location end, int sizeX, int sizeY, int rotation, int type, int walkingFlag, boolean near) {
		Path path = new Path();
		z = start.getZ();
		x = start.getX();
		y = start.getY();
		List<Point> points = new ArrayList<>();
		path.setSuccesful(true);
		while (x != end.getX() || y != end.getY()) {
			Direction[] directions = getDirection(x, y, end);
			found = true;
			checkSingleTraversal(points, directions);
			if (!found) {
				path.setMoveNear(x != start.getX() || y != start.getY());
				path.setSuccesful(false);
				break;
			}
		}
		if (!points.isEmpty()) {
			Direction last = null;
			for (int i = 0; i < points.size() - 1; i++) {
				Point p = points.get(i);
				if (p.getDirection() != last) {
					path.getPoints().add(p);
				}
			}
			path.getPoints().add(points.get(points.size() - 1));
		}
		return path;
	}

	/**
	 * Checks traversal for a size 1 entity.
	 * @param points The points list.
	 * @param directions The directions.
	 */
	private void checkSingleTraversal(List<Point> points, Direction... directions) {
		for (Direction dir : directions) {
			found = true;
			switch (dir) {
			case NORTH:
				if ((RegionManager.getProjectileFlag(z, x, y + 1) & 0x12c0120) != 0) {
					found = false;
					break;
				}
				points.add(new Point(x, y + 1, dir));
				y++;
				break;
			case NORTH_EAST:
				if ((RegionManager.getProjectileFlag(z, x + 1, y) & 0x12c0180) != 0 || (RegionManager.getProjectileFlag(z, x, y + 1) & 0x12c0120) != 0 || (RegionManager.getProjectileFlag(z, x + 1, y + 1) & 0x12c01e0) != 0) {
					found = false;
					break;
				}
				points.add(new Point(x + 1, y + 1, dir));
				x++;
				y++;
				break;
			case EAST:
				if ((RegionManager.getProjectileFlag(z, x + 1, y) & 0x12c0180) != 0) {
					found = false;
					break;
				}
				points.add(new Point(x + 1, y, dir));
				x++;
				break;
			case SOUTH_EAST:
				if ((RegionManager.getProjectileFlag(z, x + 1, y) & 0x12c0180) != 0 || (RegionManager.getProjectileFlag(z, x, y - 1) & 0x12c0102) != 0 || (RegionManager.getProjectileFlag(z, x + 1, y - 1) & 0x12c0183) != 0) {
					found = false;
					break;
				}
				points.add(new Point(x + 1, y - 1, dir));
				x++;
				y--;
				break;
			case SOUTH:
				if ((RegionManager.getProjectileFlag(z, x, y - 1) & 0x12c0102) != 0) {
					found = false;
					break;
				}
				points.add(new Point(x, y - 1, dir));
				y--;
				break;
			case SOUTH_WEST:
				if ((RegionManager.getProjectileFlag(z, x - 1, y) & 0x12c0108) != 0 || (RegionManager.getProjectileFlag(z, x, y - 1) & 0x12c0102) != 0 || (RegionManager.getProjectileFlag(z, x - 1, y - 1) & 0x12c010e) != 0) {
					found = false;
					break;
				}
				points.add(new Point(x - 1, y - 1, dir));
				x--;
				y--;
				break;
			case WEST:
				if ((RegionManager.getProjectileFlag(z, x - 1, y) & 0x12c0108) != 0) {
					found = false;
					break;
				}
				points.add(new Point(x - 1, y, dir));
				x--;
				break;
			case NORTH_WEST:
				if ((RegionManager.getProjectileFlag(z, x - 1, y) & 0x12c0108) != 0 || (RegionManager.getProjectileFlag(z, x, y + 1) & 0x12c0120) != 0 || (RegionManager.getProjectileFlag(z, x - 1, y + 1) & 0x12c0138) != 0) {
					found = false;
					break;
				}
				points.add(new Point(x - 1, y + 1, dir));
				x--;
				y++;
				break;
			}
			if (found) {
				break;
			}
		}
	}

	/**
	 * Gets the direction.
	 * @param start The start direction.
	 * @param end The end direction.
	 * @return The direction.
	 */
	private static Direction[] getDirection(int startX, int startY, Location end) {
		int endX = end.getX();
		int endY = end.getY();
		if (startX == endX) {
			if (startY > endY) {
				return new Direction[] { Direction.SOUTH };
			} else if (startY < endY) {
				return new Direction[] { Direction.NORTH };
			}
		} else if (startY == endY) {
			if (startX > endX) {
				return new Direction[] { Direction.WEST };
			}
			return new Direction[] { Direction.EAST };
		} else {
			if (startX < endX && startY < endY) {
				return new Direction[] { Direction.NORTH_EAST, Direction.EAST, Direction.NORTH };
			} else if (startX < endX && startY > endY) {
				return new Direction[] { Direction.SOUTH_EAST, Direction.EAST, Direction.SOUTH };
			} else if (startX > endX && startY < endY) {
				return new Direction[] { Direction.NORTH_WEST, Direction.WEST, Direction.NORTH };
			} else if (startX > endX && startY > endY) {
				return new Direction[] { Direction.SOUTH_WEST, Direction.WEST, Direction.SOUTH };
			}
		}
		return new Direction[0];
	}
}