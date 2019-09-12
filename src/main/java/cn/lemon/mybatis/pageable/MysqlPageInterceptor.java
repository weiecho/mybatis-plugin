package cn.lemon.mybatis.pageable;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;

/**
 * 分页拦截器 mysql
 * 
 * @author lonyee
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MysqlPageInterceptor extends PageInterceptor {
	
	@Override
	protected String getPageSql(Page page, String sql) {
	    StringBuffer sqlBuffer = new StringBuffer(sql);    
		//计算第一条记录的位置，Mysql中记录的位置是从0开始的。 
		sqlBuffer.append(" limit ").append(page.getOffset()).append(",").append(page.getLimit());
		return sqlBuffer.toString();
	}
}
