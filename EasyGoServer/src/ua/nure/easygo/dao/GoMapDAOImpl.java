package ua.nure.easygo.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.easygo.DBconnect.MySqlConnector;
import ua.nure.easygo.model.GoMap;
import ua.nure.easygo.model.Point;
import ua.nure.easygo.server.ServerUtils;

public class GoMapDAOImpl implements GoMapDAO {
	@Override
	public GoMap getMap(long mapId) throws SQLException {
		List<GoMap> maps = MySqlConnector.selectGoMap("select * from `gomaps` where map_id = " + Long.toString(mapId));
		if (maps != null && maps.size() > 0) {
			GoMap map = maps.get(0);
			return map;
		} else {
			return null;
		}
	}

	@Override
	public boolean postGoMap(GoMap map) throws SQLException {
		if (map.mapId == 0) {
			try {
				createMap(map);
				return true;
			} catch (Exception ex) {
				return false;
			}
		} else {
			try {
				updateMap(map);
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
	}

	public GoMap createMap(GoMap map) throws SQLException {
		if (map != null) {
			if (map.isPrivate) {
				MySqlConnector.execute(String.format(
						"insert into `EasyGoDB`.`gomaps` (`owner_login`, `name`, `tags`, `map_attributes`, `is_private`) VALUES ('%s', '%s', '%s', '%s', 1);",
						map.ownerLogin, map.name, map.tags, map.mapAttributes));
			} else {
				MySqlConnector.execute(String.format(
						"insert into `EasyGoDB`.`gomaps` (`owner_login`, `name`, `tags`, `map_attributes`, `is_private`) VALUES ('%s', '%s', '%s', '%s', 0);",
						map.ownerLogin, map.name, map.tags, map.mapAttributes));
			}
			return map;
		} else {
			return null;
		}
	}

	@Override
	public GoMap updateMap(GoMap map) throws SQLException {
		if (map != null && map.mapId != 0) {
			GoMap oldmap = getMap(map.mapId);
			if (map.isPrivate) {
				MySqlConnector.execute(String.format(
						"update `EasyGoDB`.`gomaps` set `owner_login`='%s', `name`='%s', `tags`='%s', `map_attributes`='%s', `is_private`=1 where map_id=%d;",
						map.ownerLogin, map.name, map.tags, map.mapAttributes, map.mapId));
			} else {
				MySqlConnector.execute(String.format(
						"update `EasyGoDB`.`gomaps` set `owner_login`='%s', `name`='%s', `tags`='%s', `map_attributes`='%s', `is_private`=0 where map_id=%d;",
						map.ownerLogin, map.name, map.tags, map.mapAttributes, map.mapId));
			}
			return oldmap;
		} else {
			return null;
		}
	}

	@Override
	public boolean removeMap(long id) {
		try {
			MySqlConnector.execute("DELETE FROM maps WHERE map_id = '" + id + "';");
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	// special methods

	public List<GoMap> getMapByQuery(String sqlQuery) throws SQLException {
		List<GoMap> maps = MySqlConnector.selectGoMap(sqlQuery);
		if (maps != null && maps.size() > 0) {
			return maps;
		} else {
			return null;
		}
	}

	public List<GoMap> getMapByUserLogin(String login) throws SQLException {
		return ServerUtils.getAllUserMaps(login);
	}

	public List<Point> getPointsOfMap(long map_id) throws SQLException {
		List<Point> points = MySqlConnector.selectPoint("select * from `points` where map_id = '" + map_id + "'");
		if (points != null && points.size() > 0) {
			return points;
		} else {
			return null;
		}
	}

	public List<GoMap> getAllMaps() throws SQLException {
		List<GoMap> maps = MySqlConnector.selectGoMap("select * from `gomaps`");
		if (maps != null && maps.size() > 0) {
			return maps;
		} else {
			return null;
		}
	}
}
