package egovframework.eam.api.user;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import egovframework.rte.fdl.security.userdetails.EgovUserDetails;
import egovframework.rte.fdl.security.userdetails.jdbc.EgovUsersByUsernameMapping;

public class DefaultUserDetailsMapping extends EgovUsersByUsernameMapping {

	public DefaultUserDetailsMapping(DataSource ds, String usersByUsernameQuery) {
		super(ds, usersByUsernameQuery);
	}

	@Override
	protected Object mapRow(ResultSet rs, int rownum) throws SQLException {

		String userid = rs.getString("user_id");
		String password = rs.getString("password");
		boolean enabled = rs.getBoolean("enabled");

		Map<String, String> map = new HashMap<String, String>();
		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();
		for (int i = 1; i <= cnt; i++) {
			String column = md.getColumnName(i).toLowerCase();
			String value = rs.getString(column);
			map.put(CamelCaseUtil.convert2CamelCase(column), value);
		}

		return new EgovUserDetails(userid, password, enabled, map);
	}

}
