package cn.lemon.mybatis.plugin;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import cn.lemon.framework.utils.JsonUtil;

/**
 * 扩展mybatis支持json格式
 * 配置mapper.xml： 
 * <result typeHandler="cn.lemon.dubbo.provider.config.ArrayTypeHandler"/> 
 * 
 * @author lonyee
 */
public class JsonTypeHandler extends BaseTypeHandler<Object> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JsonUtil.writeValue(parameter));		
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
        return JsonUtil.readValue(rs.getString(columnName), Object.class);
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
        return JsonUtil.readValue(rs.getString(columnIndex), Object.class);
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return JsonUtil.readValue(cs.getString(columnIndex), Object.class);
	}

}
