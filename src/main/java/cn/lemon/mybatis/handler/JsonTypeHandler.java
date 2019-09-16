package cn.lemon.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.json.JSONStringer;
import org.json.JSONWriter;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 扩展mybatis支持json格式
 * 
 * @author lonyee
 */
public class JsonTypeHandler extends BaseTypeHandler<Object> {


	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONWriter.valueToString(parameter));
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
        return JSONStringer.valueToString(rs.getString(columnName));
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
        return JSONStringer.valueToString(rs.getString(columnIndex));
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return JSONStringer.valueToString(cs.getString(columnIndex));
	}

}
