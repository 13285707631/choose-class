import Interface.DataBaseDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao implements DataBaseDao {
        private Connection conn=null;
        private Statement stmt=null;
        private ResultSet rs=null;
        BaseDao(){
            init_DB();
        }
    @Override
    public  void close_DB() {
        if(stmt!=null)
        {
            try{
                stmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        try{
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean show_SQL(String sql) {
            try{
                stmt=conn.createStatement();
                rs=stmt.executeQuery(sql);
                if(!rs.next()){
                    System.out.println("没有纪录");
                    return false;
                }else{
                    do{
                        ResultSetMetaData data=rs.getMetaData();
                        for(int i=1;i<=data.getColumnCount();i++){
                            System.out.printf("%-10s",rs.getString(i));
                        }
                        System.out.println();
                    }while(rs.next());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        return true;
    }
    @Override
    public  ResultSet execute_DB(String sql) {
        try{
             rs=stmt.executeQuery(sql);
            return rs;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public void update_DB(String sql){
        try{
            stmt.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int showLastRecordNumber(String sql){
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            while(rs.next()) {
                return rs.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public  boolean isLeageDB(String sql){
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                return false;
            }
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    @Override
    public void init_DB() {
        Properties params=new Properties();
        String configFile="DB.properties";
        InputStream is=BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        try{
            params.load(is);
        }catch (IOException e){
            e.printStackTrace();
        }
        String driver=params.getProperty("driver");
        String url=params.getProperty("url");
        String user=params.getProperty("user");
        String password=params.getProperty("password");
        try{
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            conn= DriverManager.getConnection(url,user,password);
            stmt=conn.createStatement();
         //   System.out.println("连接成功");//数据库连接部分
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
