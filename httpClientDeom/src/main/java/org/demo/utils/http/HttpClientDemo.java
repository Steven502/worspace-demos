package org.demo.utils.http;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * TODO(用一句话描述该文件的作用)
 * 
 * @title: HttpClientDemo.java
 * @author zhangjinshan-ghq
 * @date 2014-6-11 14:59:04
 */

public class HttpClientDemo
{

    /**
     * The main method.
     * 
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception
    {
        getResoucesByLoginCookies();
    }

    /**
     * 根据登录Cookie获取资源
     * 一切异常均未处理，需要酌情检查异常
     * 
     * @throws Exception
     */
    private static void getResoucesByLoginCookies() throws Exception
    {
        HttpClientDemo demo = new HttpClientDemo();
		String username = "root";// 登录用户
		String password = "123456";// 登录密码

        // 需要提交登录的信息
		String urlLogin = "http://localhost:8080/roleplay/login?username=" + username + "&password=" + password;

        // 登录成功后想要访问的页面 可以是下载资源 需要替换成自己的iteye Blog地址
		String urlAfter = "http://localhost:8080/roleplay/form/object/getDataJson?"
				+ "fieldCode=fgouty&filter=003&selectType=single&existObjCode=outy_0000030001&"
				+ "fgouty.fgouty001operator=and&filtervalue0=aa&filtercondition0=EQUAL&filteroperator0=0&filterdatafield0=fgouty.fgouty001"
				+ "&filtervalue1=bb&filtercondition1=EQUAL&filteroperator1=0&filterdatafield1"
				+ "=fgouty.fgouty001&fgouty.fgouty002operator=and&filtervalue2=cc&"
				+ "filtercondition2=EQUAL&filteroperator2=1&filterdatafield2=fgouty.fgouty002&"
				+ "filtervalue3=dd&filtercondition3=EQUAL&filteroperator3=1&filterdatafield3=fgouty.fgouty002"
				+ "&fgouty.fgouty003operator=and&filtervalue4=ee&filtercondition4=EQUAL&filteroperator4=0&"
				+ "filterdatafield4=fgouty.fgouty003&filtervalue5=ff&filtercondition5=EQUAL&filteroperator5=0&"
				+ "filterdatafield5=fgouty.fgouty003&filterscount=6&groupscount=0&pagenum=0&pagesize=10&recordstartindex=0&"
				+ "recordendindex=10&gridInfo=R&_=1453883755409";

        DefaultHttpClient client = new DefaultHttpClient(new PoolingClientConnectionManager());

        /**
         * 第一次请求登录页面 获得cookie
         * 相当于在登录页面点击登录，此处在URL中 构造参数，
         * 如果参数列表相当多的话可以使用HttpClient的方式构造参数
         * 此处不赘述
         */
        HttpPost post = new HttpPost(urlLogin);
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        CookieStore cookieStore = client.getCookieStore();
        client.setCookieStore(cookieStore);

        /**
         * 带着登录过的cookie请求下一个页面，可以是需要登录才能下载的url
         * 此处使用的是iteye的博客首页，如果登录成功，那么首页会显示【欢迎XXXX】
         * 
         */
        HttpGet get = new HttpGet(urlAfter);
        response = client.execute(get);
        entity = response.getEntity();

        /**
		 * 将请求结果放到文件系统中保存为 myindex.html,便于使用浏览器在本地打开 查看结果
		 */
		// String pathName = "d:\\myindex.html";
		// writeHTMLtoFile(entity, pathName);

		String str = convertStreamToString(entity.getContent());
		System.err.println(str);
    }

    /**
     * Write htmL to file.
     * 将请求结果以二进制形式放到文件系统中保存为.html文件,便于使用浏览器在本地打开 查看结果
     * 
     * @param entity the entity
     * @param pathName the path name
     * @throws Exception the exception
     */
    public static void writeHTMLtoFile(HttpEntity entity, String pathName) throws Exception
    {

        byte[] bytes = new byte[(int) entity.getContentLength()];

        FileOutputStream fos = new FileOutputStream(pathName);

        bytes = EntityUtils.toByteArray(entity);

        fos.write(bytes);

        fos.flush();

        fos.close();
    }

	/**
	 * Convert stream to string.
	 *
	 * @param is the is
	 * @return the string
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
