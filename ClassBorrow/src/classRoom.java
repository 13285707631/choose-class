import Interface.RoomInter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.math.*;

public class classRoom extends Room implements RoomInter {
    BaseDao baseDao = new BaseDao();
    Scanner scanner = new Scanner(System.in);
    boolean isNormal;
    char scheduleID;
    public void addRoom() {//增加教室
            System.out.print("请输入教室号");
            String room_id = scanner.next();
            if(isLegal(room_id)) {
                System.out.print("请输入座位数目(40,60,200)");
                int seat = scanner.nextInt();
                if(seat!=40&&seat!=60&&seat!=200){
                    System.out.println("座位数不合法，请重试");
                    addRoom();
                }else{
                    System.out.print("是否为多媒体教室？(y/n)");
                    String normal = scanner.next();
                    int normalInt = 0;
                    if (normal.equals("y")) {
                        normalInt = 0;
                        //添加记录到class_room表格
                        baseDao.update_DB("insert into class_room(class_room_id ,normal,seat_number,schedule_id)" +
                                "values('" + room_id + "'," + normalInt + "," + seat + "," + "0)");
                        //添加记录到borrow_record
                        int nextNumber = 0;
                        nextNumber = 1 + baseDao.showLastRecordNumber("select max(record_number) from borrow_record");
                        //修改为表的最后一行的record_number +1
                        createRecord(nextNumber, room_id);
                        System.out.println("添加教室完成");
                    } else if (normal.equals("n")) {
                        normalInt = 1;
                        //添加记录到class_room表格
                        baseDao.update_DB("insert into class_room(class_room_id ,normal,seat_number,schedule_id)" +
                                "values('" + room_id + "'," + normalInt + "," + seat + "," + "0)");
                        //添加记录到borrow_record
                        int nextNumber = 0;
                        nextNumber = 1 + baseDao.showLastRecordNumber("select max(record_number) from borrow_record");
                        //修改为表的最后一行的record_number +1
                        createRecord(nextNumber, room_id);
                        System.out.println("添加教室完成");
                    } else {
                        System.out.println("输入错误");
                        addRoom();
                    }
                }
            } else {
               // System.out.println("教室号无效，请重新输入");
                addRoom();
            }
        }
    public void delRoom() {
        System.out.print("请输入要删除的教室的教室号");
        String room_id = scanner.next();
        ResultSet rs=baseDao.execute_DB("select borrow_state from borrow_record where room_id='"+room_id+"'");
        try{
            rs.next();

            boolean flag=true;
           // System.out.println("a="+a);
            do{
                String a=rs.getString(1);
                if(a.equals("1")){
                    System.out.println("该教室有借出记录，教室删除失败");
                    flag=false;
                    break;
                }
            }while(rs.next());
            if(flag){
                baseDao.update_DB("delete from class_room where class_room_id='" + room_id + "'");
                baseDao.update_DB("delete from borrow_record where room_id='" + room_id + "'");
                //删除在表class_room中的记录
                //删除在record中的记录
                System.out.println("该教室成功删除！");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public boolean isLegal(String room_id) {//检查教室号是否合法//待修改
        //是否是A100这种形式
        //教室是否已经存在
        String sql="select * from class_room where class_room_id='"+room_id+"'";
        if(baseDao.isLeageDB(sql)){//TO DO
            return true;
        }
        else{
            System.out.println("该教室已经存在,请重试");
            return false;
        }

    }

    public void createRecord(int nextRecordNumber, String room_id) {
        String day = "";
        int lesson = 0;
        String borrow_date = "";

        for (int i = 0; i < 35; i++) {

            switch (i / 5) {
                case 0:
                    day = "Mo";
                    borrow_date = "2017-7-15";
                    break;
                case 1:
                    day = "Tu";
                    borrow_date = "2017-7-16";
                    break;
                case 2:
                    day = "We";
                    borrow_date = "2017-7-17";
                    break;
                case 3:
                    day = "Th";
                    borrow_date = "2017-7-18";
                    break;
                case 4:
                    day = "Fr";
                    borrow_date = "2017-7-19";
                    break;
                case 5:
                    day = "Sa";
                    borrow_date = "2017-7-20";
                    break;
                case 6:
                    day = "Su";
                    borrow_date = "2017-7-31";
                    break;
                default:

            }




            switch (i%5){
                case 0:
                    lesson = 1;
                    break;
                case 1:
                    lesson = 2;
                    break;
                case 2:
                    lesson = 3;
                    break;
                case 3:
                    lesson  =4;
                    break;
                case 4:
                    lesson =5;
                    break;

            }


            int x = i + nextRecordNumber;

            baseDao.update_DB("insert into borrow_record(record_number,room_id,borrow_state, borrow_person,day,lesson,borrow_date)" +
                    "values(" + x + ",'" + room_id + "',0,null,'" + day + "'," + lesson + ",'" + borrow_date + "');");
        }
    }
}
