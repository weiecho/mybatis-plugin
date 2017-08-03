package cn.lemon.mybatis.pageable;

import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import cn.lemon.framework.query.Page;

/**
 * 分页拦截器 oracle
 * 
 * @author lonyee
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class OraclePageInterceptor extends PageInterceptor {
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if ("oracle".equalsIgnoreCase(this.getDialect())) {
			return super.intercept(invocation);
		}
		return invocation.proceed();
	}
	
	@Override
	protected String getPageSql(Page page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		//计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(page.getOffset() + page.getLimit() + 1);
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(page.getOffset());
		return sqlBuffer.toString();
	}
}
