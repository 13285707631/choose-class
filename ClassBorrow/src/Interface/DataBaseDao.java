package Interface;

import java.sql.ResultSet;

public interface DataBaseDao {
    public  void init_DB();
    public boolean show_SQL(String sql);
    public void close_DB();
    public ResultSet execute_DB(String sql);
}
