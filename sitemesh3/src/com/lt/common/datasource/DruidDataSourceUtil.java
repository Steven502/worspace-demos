/*package com.lt.common.datasource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import com.wabacus.config.database.datasource.AbsDataSource;
//import com.mchange.v2.c3p0.DataSources;
//import com.mchange.v2.c3p0.PoolConfig;
import com.wabacus.exception.WabacusConfigLoadingException;
import com.wabacus.exception.WabacusRuntimeException;
import com.wabacus.util.DesEncryptTools;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.wabacusdemo.util.StringUtil;

public class DruidDataSourceUtil extends AbsDataSource
{
  private static Log log=LogFactory.getLog(DruidDataSourceUtil.class);
    // 配置初始化大小、最小、最大
    private int initialSize = 10;
    private int minIdle = 10;
    private int maxActive = 100;
    
    // 配置获取连接等待超时的时间
    private long maxWait = 60000;
    
    // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    private long timeBetweenEvictionRunsMillis = 60000;
    
    // 配置一个连接在池中最小生存的时间，单位是毫秒
    private long minEvictableIdleTimeMillis = 300000;
    
    *//**
   * hsqldb - "select 1 from INFORMATION_SCHEMA.SYSTEM_USERS"
   * Oracle - "select 1 from dual"
   * DB2 - "select 1 from sysibm.sysdummy1"
   * mysql - "select 1"
   *//*
    private String validationQuery = "select 1";
    private boolean testWhileIdle = true;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    
    // 打开PSCache，并且指定每个连接上PSCache的大小。
    //mysql不需要做PSCache，只有Oracle/DB2/SQL Server之类支持游标的数据库才需要这样配置。所以设置为false。
    private boolean poolPreparedStatements = false;    // mysql 建议为 false, oracle 建议为 true;
    private int maxPoolPreparedStatementPerConnectionSize = 20;
    
    //连接泄漏处理。Druid提供了RemoveAbandanded相关配置，用来关闭长时间不使用的连接。
    private boolean removeAbandoned = true;    // 打开removeAbandoned功能
    private int removeAbandonedTimeout = 1800;//1800秒，也就是30分钟
    private boolean logAbandoned = true;//关闭abanded连接时输出错误日志
    
    // 配置监控统计拦截的filters
    private String filters;    // 监控统计："stat"  防SQL注入："wall" 组合使用： "stat,wall"
    private List<Filter> filterList;
  private DruidDataSource ds;
//  private WallConfig wc;

  public Connection getConnection()
  {
  try
  {
    log.debug("从数据源"+this.getName()+"获取数据库连接");
    return ds.getConnection();
  }catch(SQLException e)
  {
    throw new WabacusRuntimeException("获取"+this.getName()+"数据源的数据库连接失败",e);
  }
  }

  public DataSource getDataSource()
  {
  return this.ds;
  }
  
  public void closePool()
  {
  super.closePool();
  try {
            if(this.ds!=null)
            {
            log.debug("正在关闭druid连接池....................................................");
            this.ds.close();
            }
            this.ds=null;
        } catch (Exception e) {
            throw new WabacusRuntimeException("关闭druid数据源失败",e);
        }
  }

  public void loadConfig(Element eleDataSource)
  {
  super.loadConfig(eleDataSource);
  //System.out.println("===========loadConfig");
  List lstEleProperties=eleDataSource.elements("property");
  if(lstEleProperties==null||lstEleProperties.size()==0)
  {
    throw new WabacusConfigLoadingException("没有为数据源："+this.getName()
      +"配置alias、configfile等参数");
  }
  String driver=null;
  String url=null;
  String user=null;
  String password=null;

  Element eleChild;
  String name;
  String value;
  for(int i=0;i<lstEleProperties.size();i++)
  {
    eleChild=(Element)lstEleProperties.get(i);
    name=eleChild.attributeValue("name");
    value=eleChild.getText();
    name=name==null?"":name.trim();
    value=value==null?"":value.trim();
    if(value.equals(""))
    {
      continue;
    }
    if(name.equals("initialSize"))
    {
        initialSize=Integer.parseInt(value);
    }else if(name.equals("minIdle"))
    {
        minIdle=Integer.parseInt(value);
    }else if(name.equals("maxActive"))
    {
        maxActive=Integer.parseInt(value);
    }else if(name.equals("maxWait"))
    {
        maxWait=Integer.parseInt(value);
    }else if(name.equals("timeBetweenEvictionRunsMillis"))
    {
        timeBetweenEvictionRunsMillis=Integer.parseInt(value);
    }else if(name.equals("minEvictableIdleTimeMillis"))
    {
        minEvictableIdleTimeMillis=Integer.parseInt(value);
    }else if(name.equals("validationQuery"))
    {
        validationQuery=value;
    }else if(name.equals("testWhileIdle"))
    {
        testWhileIdle=Boolean.parseBoolean(value);
    }else if(name.equals("testOnBorrow"))
    {
        testOnBorrow=Boolean.parseBoolean(value);
    }else if(name.equals("testOnReturn"))
    {
        testOnReturn=Boolean.parseBoolean(value);
    }else if(name.equals("poolPreparedStatements"))
    {
        poolPreparedStatements=Boolean.parseBoolean(value);
    }else if(name.equals("maxPoolPreparedStatementPerConnectionSize"))
    {
        maxPoolPreparedStatementPerConnectionSize=Integer.parseInt(value);
    }else if(name.equals("filters"))
    {
        filters=value;
    }    
    else if(name.equals("driver"))
    {
      driver=value;
    }else if(name.equals("url"))
    {
      url=value;
    }else if(name.equals("user"))
    {
      user=value;
    }else if(name.equals("password"))
    {
      password=value;
    }else if(name.equals("removeAbandoned"))
    {
        removeAbandoned=Boolean.parseBoolean(value);
    }else if(name.equals("removeAbandonedTimeout"))
    {
        removeAbandonedTimeout=Integer.parseInt(value);
    }else if(name.equals("logAbandoned"))
    {
        logAbandoned=Boolean.parseBoolean(value);
    }
  }
  if(driver.equals("")||url.equals("")||user.equals(""))
  {
    throw new WabacusConfigLoadingException("数据源："+this.getName()
      +"配置的参数不完整，必须配置driver，url，user几个参数");
  }
  //解密password
  password=password==null?"":password.trim();
  if(password.startsWith("{3DES}"))
  {
    password=password.substring("{3DES}".length());
    if(DesEncryptTools.KEY_OBJ==null)
    {
      throw new WabacusConfigLoadingException("没有取到密钥文件，无法完成数据库密码解密操作");
    }
    password=DesEncryptTools.decrypt(password);
  }
  
  //System.out.println("===========url:"+url);
  
  ds = new DruidDataSource();        
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        ds.setInitialSize(initialSize);
        ds.setMinIdle(minIdle);
        ds.setMaxActive(maxActive);
        ds.setMaxWait(maxWait);
        ds.setTimeBetweenConnectErrorMillis(timeBetweenEvictionRunsMillis);
        ds.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        
        ds.setValidationQuery(validationQuery);
        ds.setTestWhileIdle(testWhileIdle);
        ds.setTestOnBorrow(testOnBorrow);
        ds.setTestOnReturn(testOnReturn);
        
        ds.setRemoveAbandoned(removeAbandoned);
        ds.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        ds.setLogAbandoned(logAbandoned);
        
        ds.setPoolPreparedStatements(poolPreparedStatements);
        if (poolPreparedStatements == true)
            ds.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        
        if (StringUtil.notBlank(filters))
            try {ds.setFilters(filters);} catch (SQLException e) {throw new RuntimeException(e);}
        
        
        
        addFilterList(ds);
        //WallFilter设置
//        wc = new WallConfig();
//        wc.setMultiStatementAllow(true);
//        wc.setWrapAllow(false);
  }

  protected void finalize() throws Throwable
  {
  closePool();
  super.finalize();
  }
    private void addFilterList(DruidDataSource ds) {
        if (filterList != null) {
            List<Filter> targetList = ds.getProxyFilters();
            for (Filter add : filterList) {
                boolean found = false;
                for (Filter target : targetList) {
                    if (add.getClass().equals(target.getClass())) {
                        found = true;
                        break;
                    }
                }
                if (! found)
                    targetList.add(add);
            }
        }
    }  
}
*/