package cn.lemon.mybatis.pageable;

import cn.lemon.mybatis.util.ReflectUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 分页拦截器
 * 
 * @author lonyee
 *
 */
public abstract class PageInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
		BoundSql boundSql = delegate.getBoundSql();
		Object obj = boundSql.getParameterObject();
		Page page = seekPage(obj);
		if (page != null) {
			MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
			Connection connection = (Connection) invocation.getArgs()[0];
			String sql = boundSql.getSql();
			this.setTotalRecord(page, mappedStatement, connection);
			String pageSql = this.getPageSql(page, sql);
			ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
		}
		return invocation.proceed();
	}
	
    /**
     * 寻找page对象
    */
    @SuppressWarnings("rawtypes")
    protected Page seekPage(Object parameter) {
        Page page = null;
        if (parameter == null) {
            return null;
        }
        if (parameter instanceof Page) {
            page = (Page) parameter;
        } else if (parameter instanceof Map) {
            Map map = (Map) parameter;
            for (Object arg : map.values()) {
                if (arg instanceof Page) {
                    page = (Page) arg;
                    break;
                }
            }
        }
        return page;
    }

	private void setTotalRecord(Page page, MappedStatement mappedStatement, Connection connection) {
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				//给当前的参数page对象设置总记录数
				page.setTotal(totalRecord);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
	
	public void setProperties(Properties properties) {
		// nothing to do
	}
	
	private String getCountSql(String sql) {
		int index = sql.indexOf(" from ");
		if (index<0) {
			throw new StringIndexOutOfBoundsException("分页查询SQL没有找到[from]关键字，from必须为小写且前后加空格");
		}
		return "select count(*) " + sql.substring(index);
	}

	protected abstract String getPageSql(Page page, String sql);
	
}
