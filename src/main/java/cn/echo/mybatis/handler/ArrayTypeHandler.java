package cn.echo.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * 扩展mybatis支持数组格式
 * @author lonyee
 */
public class ArrayTypeHandler extends BaseTypeHandler<String[]> {
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
    	StringBuilder arrayBuilder = new StringBuilder();
    	if (parameter != null && parameter.length>0) {
            for(String val: parameter){
            	arrayBuilder.append(",");
            	arrayBuilder.append(val);
            }
            arrayBuilder.deleteCharAt(0);
        }
        ps.setString(i, arrayBuilder.toString());
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {

        return getArray(rs.getString(columnName));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return getArray(rs.getString(columnIndex));
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {

        return getArray(cs.getString(columnIndex));
    }
    
    private String[] getArray(String array) {
        
        if (array == null) {
            return null;
        }

        try {
            return array.split(",");
        } catch (Exception e) {
        }
        
        return null;
    }
}
