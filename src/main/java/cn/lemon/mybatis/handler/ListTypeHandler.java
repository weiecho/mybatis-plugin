package cn.lemon.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 扩展mybatis支持List格式
 * 
 * @author lonyee
 */
public class ListTypeHandler extends BaseTypeHandler<List<String>> {
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
    	StringBuilder arrayBuilder = new StringBuilder();
    	if (parameter != null && parameter.size()>0) {
            for(String val: parameter){
            	arrayBuilder.append(",");
            	arrayBuilder.append(val);
            }
            arrayBuilder.deleteCharAt(0);
        }
        ps.setString(i, arrayBuilder.toString());
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName)
            throws SQLException {

        return getArray(rs.getString(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return getArray(rs.getString(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {

        return getArray(cs.getString(columnIndex));
    }
    
    private List<String> getArray(String array) {
        
        if (array == null) {
            return null;
        }

        try {
            String[] arrays = array.split(",");
            List<String> list = new ArrayList<String>();
            Collections.addAll(list, arrays);
            return list;
        } catch (Exception e) {
        }
        
        return null;
    }
}
