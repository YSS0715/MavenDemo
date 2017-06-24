package cn.com.util;

import java.sql.*;

/**
 * Created by YSS on 2017/6/23.
 */
public class BaseDao {
    public static final String driver="com.mysql.jdbc.Driver";
    public static final String url="jdbc:mysql://localhost:3306/employee";
    public static String username="root";
    public static String password="root";

    public Connection con;  //连接对象
    public PreparedStatement ps;//命令对象
    public ResultSet rs;  //结果集对象

    //获取数据库连接对象
    public Connection getConnection(){
        try {
            Class.forName(driver);
            //如果连接对象为空 ，或者连接被关闭，重新构建连接对象
            if(con==null||con.isClosed()){
                con= DriverManager.getConnection(url,username,password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    //执行增删改操作的方法
    public int executeUpdate(String sql,Object...objs) throws Exception{
        con=getConnection();
        ps = con.prepareStatement(sql);
        for (int i = 0; i < objs.length; i++) {
            ps.setObject(i+1, objs[i]);
        }
        int count = ps.executeUpdate();
        return count;
    }
    //执行查询操作的方法
    public ResultSet executeQuery(String sql,Object...prams){
        con=getConnection();
        try {
            ps=con.prepareStatement(sql);
            for (int i = 0; i < prams.length; i++) {
                ps.setObject(i+1, prams[i]);
            }
            rs=ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{

        }
        return rs;
    }
    //释放资源
    public void closeResource(){
        //若结果集对象不为空，则关闭
        try{
            if(rs!=null){
                rs.close();
            }
            if(ps!=null){
                ps.close();
            }
            if(con!=null){
                con.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
