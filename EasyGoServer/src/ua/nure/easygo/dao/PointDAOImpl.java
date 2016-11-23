package ua.nure.easygo.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.easygo.DBconnect.MySqlConnector;
import ua.nure.easygo.model.Point;

/**
 * Created by Анна on 23.11.2016.
 */
public class PointDAOImpl implements PointDAO {
	public final String DB_NAME = "EasyGoDB";
	public final String TABLE_NAME = "points";

	private PointDAO pointDAO;

	public PointDAO getPointDAO() {
		return pointDAO;
	}

	public void setPointDAO(PointDAO pointDAO) {
		this.pointDAO = pointDAO;
	}

	@Override
	public Point createPoint(Point point) {
		// if no such point in DB
		if (getPoint(point.pointId) == null) {
			final String queryInsert = "INSERT INTO " + DB_NAME + "." + TABLE_NAME
					+ " (x, y, name, map_id, attribute_values) values " + point.x + ", " + point.y + ", '" + point.name
					+ "', " + point.mapId + ", '" + point.attributeValues + "';";
			try {
				MySqlConnector.execute(queryInsert);
				return point;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// point already exists
		return null;
	}

	@Override
	public Point getPoint(long id) {
		final String query = "SELECT * from" + DB_NAME + "." + TABLE_NAME + " where point_id=" + id + ";";
		List<Point> list = null;
		try {
			list = MySqlConnector.selectPoint(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (list != null) {
			// such point exists
			return list.get(0);
		}
		// else point not exists
		return null;
	}

	@Override
	public Point updatePoint(Point point) {
		if (getPoint(point.pointId) != null) {

			final String queryUpdate = String.format(
					"UPDATE %s.%s SET x=%f, y=%f, name='%s', map_id=%d, attribute_values=%s where point_id=%d", DB_NAME,
					TABLE_NAME, point.x, point.y, point.name, point.mapId, point.attributeValues, point.pointId);
			try {
				MySqlConnector.execute(queryUpdate);
				return point;
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		// point already exists
		return null;
	}

	@Override
	public boolean removePoint(long id) {
		if (getPoint(id) != null) {
			final String queryDelete = String.format("REMOVE FROM %s.%s" + " where point_id=%d", DB_NAME, TABLE_NAME, id);
			try {
				MySqlConnector.execute(queryDelete);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return false;
	}
}