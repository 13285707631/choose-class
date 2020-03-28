import Interface.TestAnnotation;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import javax.print.attribute.standard.NumberUp;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.rmi.server.ExportException;
import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import static javafx.application.Platform.exit;
@TestAnnotation
public class Start {//开始界面-邹迎童
    private ArrayList<User>arrayList_user=new ArrayList<User>();
    private ArrayList<String> arrayList_temp=new ArrayList();
    private  BaseDao baseDao=new BaseDao();
    private String uid;
    public void start()
    {
        System.out.println("欢迎来到学校教室会议室借用系统");
        init();
        step1();
    }
    public void search2_1(){
        System.out.println("1.查看空闲教室 2.查看空闲会议室");
        Scanner scanner=new Scanner(System.in);
        try{
            int chose2 = scanner.nextInt();
            if(chose2!=1&&chose2!=2){
                throw new MyException_2();
            }else{
                switch (chose2) {
                    case 1:
                        String[] s = {"序号", "房号", "状态", "用户", "星期", "课数", "日期"};
                        for (int i = 0; i < 7; i++) {
                            System.out.print(s[i]);
                            System.out.print("      ");
                        }
                        System.out.println();
                        baseDao.show_SQL("select * from borrow_record where borrow_state=0 and room_id in (select class_room_id from class_room)");
                        step2();
                        break;
                    case 2:
                        String[] s1 = {"序号", "房号", "状态", "用户", "星期", "课数", "日期"};
                        for (int i = 0; i < 7; i++) {
                            System.out.print(s1[i]);
                            System.out.print("      ");
                        }
                        System.out.println();
                        baseDao.show_SQL("select * from borrow_record where borrow_state=0 and room_id in (select meetingroom_id from meetingroom)");
                        step2();
                        break;
                    default:
                        step2();
                }
            }
        }catch (MyException_2 e){
            e.printStackTrace();
            search2_1();
        }catch (InputMismatchException e){
            System.out.println("请输入1或者2，请重试");
            search2_1();
        }
    }
    public void search1()//功能一：查看
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println("1.查看空闲房间 2.查看您的预约");
        try{
            int chose1 = scanner.nextInt();
            if(chose1!=1&&chose1!=2)
                throw  new MyException_2();
            else{
                switch (chose1) {
                    case 1:
                        search2_1();
                        break;
                    case 2:
                        String user_id = uid;
                        System.out.println("记录号    房间号    借用状态  借用人    星期      课号       日期  ");
                        baseDao.show_SQL("select * from borrow_record  where borrow_state=1 and borrow_person='" + user_id + "'");
                        step2();
                        break;
                    default:
                        return;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("请输入1或者2，请重试");
            search1();
        }catch (MyException_2 e){
        e.printStackTrace();
            search1();
    }
    }
    public boolean able_reserve(String sql,int chose){
        ResultSet rs;
        rs=baseDao.execute_DB(sql);
        try{
          //  System.out.println(rs.next());
            while(rs.next()){
                String a=rs.getString(1);
              //  System.out.println("a="+a);
                arrayList_temp.add(a);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        Iterator t1=arrayList_temp.iterator();
//        while(t1.hasNext()){
//            System.out.print(t1.next()+"\t");
//        }
        String s=""+chose;
        if(arrayList_temp.contains(s)){
            return true;
        }else
        {
            return false;
        }
    }
    public void reserve()//功能二：预约
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println("请选择您的预约：1.教室 2.会议室");
        try{
            int chose3 = scanner.nextInt();
            if(chose3!=1&&chose3!=2){
                throw  new MyException_2();
            }
            else{
                switch (chose3) {
                    case 1:
                        System.out.println("请输入教室编号（没有就填‘无’）：");
                        String id = scanner.next();
                        if (id.equals("无")) {
                            String sql = "select distinct record_number,room_id,borrow_person,day,lesson,normal,seat_number from  borrow_record join class_room on borrow_record.room_id=class_room.class_room_id where borrow_state=0 ";
                            String user_id = null;
                            int seat = 0;
                            int isNormal = 0;
                            String date = null;
                            int lesson = 0;
                            user_id = uid;
                            System.out.println("请输入您的需求(没有填无)：");
                            System.out.print("座位数：");
                            String s = scanner.next();
                            if (s.equals("无")) {
                            } else {
                                seat = Integer.valueOf(s);
                                sql += " and class_room.seat_number = " + seat;
                            }
                            System.out.print("是否是多媒体(y/n):");
                            s = scanner.next();
                            if (s.equals("y")) {
                                isNormal = 0;
                                sql += " and normal=" + isNormal;

                            } else if (s.equals("n")) {
                                isNormal = 1;
                                sql += " and normal=" + isNormal;
                            }
                            System.out.println("星期:（Mo/Tu/We/Th/Fr/Sa/Su）");
                            String s1 = scanner.next();
                            if (s1.equals("无")) {

                            } else {
                                date = s1;
                                sql += " and day = '" + date+"'";
                            }
                            System.out.println("请输入第几节课（1，2，3，4，5）:");
                            String s2 = scanner.next();
                            if (s2.equals("无")) {
                            } else {
                                lesson = Integer.valueOf(s2);
                                sql += " and lesson = '" + lesson+"'";
                            }
                            //System.out.println(sql);
                            //    System.out.println("根据您的需求：\n座位数："+seat+"\n多媒体："+s+"\n时间："+date+"\n第几节课："+s2);
                            String[] s3 = {"记录号", "房间号", "用户", "星期", "第几节课", "普通教室", "座位数"};
                            for (int i = 0; i < 7; i++) {
                                System.out.printf("%-6s", s3[i]);
                            }
                            System.out.println();
                            boolean flag=baseDao.show_SQL(sql);
                            boolean flag1=false;
                            if(flag){
                                System.out.println("请输入您选择的记录号：");
                                int chose_record_id = scanner.nextInt();
                                if(!able_reserve(sql,chose_record_id)){
                                    System.out.print("此记录号不在您可预约的范围内，");
                                    flag1=true;
                                }else{
                                    baseDao.update_DB("Update borrow_record set borrow_state = 1,borrow_person='" + user_id + "' where record_number = " + chose_record_id);
                                   add_Borrow_record(user_id,chose_record_id);
                                    System.out.print("预约成功");
                                }
                            }
                            if(flag1) {
                                System.out.print("预约失败");
                            }
                            System.out.println(",返回上一级");
                            step2();

                            break;
                        } else {
                            String user_id;
                            user_id = uid;
                            String sql = "select record_number,room_id,day,lesson,borrow_date from borrow_record where borrow_state=0 and room_id='" + id + "' ";
                            System.out.println("请输入您的需求（没有填无）");
                            System.out.println("星期:(Mo,Tu,We,Th,Fr)");
                            String s1 = scanner.next();
                            String date;
                            int lesson;
                            if (s1.equals("无")) {
                            } else {
                                date = s1;
                                sql += " and day ='" + date + "'";
                            }
                            System.out.println("请输入第几节课（1，2，3，4，5）:");
                            String s2 = scanner.next();
                            if (s2.equals("无")) {
                            } else {
                                lesson = Integer.valueOf(s2);
                                sql += " and lesson = '" + lesson + "' ";
                            }
                            String[] s3 = {"记录号", "房间号", "星期", "第几节课", "日期"};
                            for (int i = 0; i < 5; i++) {
                                System.out.printf("%-7s", s3[i]);
                            }
                            System.out.println();
                            boolean flag=baseDao.show_SQL(sql);
                            if(flag){
                                System.out.println("请输入您选择的记录号：");
                                int chose_record_id = scanner.nextInt();
                                baseDao.update_DB("Update borrow_record set borrow_state = 1,borrow_person='" + user_id + "' where record_number = " + chose_record_id);
                                add_Borrow_record(user_id,chose_record_id);
                                System.out.print("预约成功");
                            }
                            else{
                                System.out.print("预约失败");
                            }
                            System.out.println(",返回上一级");
                            step2();
                        }
                        break;
                    case 2:
                        String sql = "select distinct record_number,room_id,borrow_person,day,lesson,seat_number from  borrow_record join meetingroom on borrow_record.room_id=meetingroom.meetingroom_id where borrow_state=0 ";
                        int seat = 0;
                        int isNormal = 0;
                        String date = null;
                        int lession = 0;
                        String user_id = uid;
                        System.out.println("请输入您的需求(没有填无)：");
                        System.out.println("请输入会议室编号（没有就填‘无’）：");
                        id = scanner.next();
                        if (!id.equals("无")) {
                            sql += " and meetingroom_id = '" + id + "' ";

                            System.out.println("星期:(Mo,Tu,We,Th,Fr)");
                            String s1 = scanner.next();
                            if (s1.equals("无")) {
                            } else {
                                date = s1;
                                sql += " and day ='" + date +"'";
                            }
                            System.out.println("请输入第几节课（1，2，3，4，5）:");
                            String s2 = scanner.next();
                            if (s2.equals("无")) {
                            } else {
                                lession = Integer.valueOf(s2);
                                sql += " and lesson = '" + lession + "' ";
                            }
                        } else {
                            System.out.print("座位数：");
                            String s4 = scanner.next();
                            if (s4.equals("无")) {
                            } else {
                                seat = Integer.valueOf(s4);
                                sql += " and meetingroom.seat_number = '" + seat + "' ";

                            }
                            System.out.println("星期:(Mo,Tu,We,Th,Fr)");
                            String s1 = scanner.next();
                            String date1 = null;
                            int lesson = 0;
                            if (s1.equals("无")) {
                            } else {
                                date = s1;
                                sql += " and day ='" + date + "' ";
                            }
                            System.out.println("请输入第几节课（1，2，3，4，5）:");
                            String s2 = scanner.next();
                            if (s2.equals("无")) {
                            } else {
                                lesson = Integer.valueOf(s2);
                                sql += " and lesson = '" +lesson+ "' ";
                            }
                        }
                        System.out.println("根据您的需求有以下记录：");
                        System.out.println("记录号    房间号    星期     第几节课   日期  ");
                        boolean flag1 = baseDao.show_SQL(sql);
                        boolean flag2=false;
                        if (flag1) {
                            System.out.println("请输入您选择的记录号：");
                            int chose_record_id = scanner.nextInt();
                            if(!able_reserve(sql,chose_record_id)){
                                System.out.println("此记录号不在您可预约的范围内，");
                                flag2=true;
                            }
                            else{
                                baseDao.update_DB("Update borrow_record set borrow_state = 1,borrow_person = '" + user_id + "'where record_number = '" + chose_record_id+"'");
                                add_Borrow_record(user_id,chose_record_id);
                                System.out.print("预约成功！");
                            }

                        } if(flag2) {
                            System.out.print("预约失败");
                        }
                        System.out.println("返回上一级");
                        step2();
                        break;
                }
            }
        }catch (MyException_2 e){
            e.printStackTrace();
            reserve();
        }catch (InputMismatchException e){
            System.out.println("请输入1或者2，请重试");
            reserve();
        }
    }
    public void can_reserve()
    {
        String user_id = uid;
        System.out.println("记录号    房间号    借用者    星期      课号      普通教室   座位数");
        baseDao.show_SQL("select * from borrow_record  where borrow_state=1 and borrow_person='" + user_id + "'");
        cancel(user_id);

    }
    public void cancel(String user_id){
        System.out.print("请输入您要取消的记录");
        Scanner scanner=new Scanner(System.in);
        int record_id = scanner.nextInt();
        boolean flag=false;
        for(int i=0;i<arrayList_user.size();i++){
            if(arrayList_user.get(i).getName().equals(user_id)){
                for(int j=0;j<arrayList_user.get(i).borrow_records.size();j++){
                    if(arrayList_user.get(i).borrow_records.get(j).getBorrow_record()==record_id)
                        flag=true;
                }
            }
        }
        //在数据库中删除那条记录
        if(!flag){
            System.out.println("您没有该条预约记录，请重试");
            cancel(user_id);

        }else{
            baseDao.update_DB("Update borrow_record set borrow_state=0 where record_number = " + record_id);
            del_Borrow_record(user_id,record_id);
            System.out.println("取消预约成功，返回上一级");
            step2();
        }
    }

    public void step2( ) {
        System.out.println("请选择您的操作：1.查看 2.预约 3.取消预约 4.退出登录");
        Scanner scanner = new Scanner(System.in);
        try{
            int chose = scanner.nextInt();
            if(chose!=1&&chose!=2&&chose!=3&&chose!=4){
                throw new MyException_4();
            }else
            {
                switch (chose) {
                    case 1:
                        search1();
                        break;
                    case 2:
                        reserve();
                        break;
                    case 3:
                        can_reserve();
                        break;
                    case 4:
                        start();
                        break;
                    default:
                }
            }
        }catch (InputMismatchException e){
            System.out.println("请输入1，2,3或者4，请重试");
            step2();
        }catch (MyException_4 e){
            e.printStackTrace();
            step2();
        }
    }
    public void init()//用文件中的数据初始化用户队列-邹迎童
    {
        File file=new File(".");
        try
        {
            String path=file.getCanonicalPath()+"\\Text.txt";
            FileInputStream inputStream=new FileInputStream(path);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String str=null;
            int i=0;
            while((str=bufferedReader.readLine())!=null){
                try{
                    User user=new User(str,bufferedReader.readLine(),i);
                    arrayList_user.add(user);
                }catch (IndexOutOfBoundsException e){
                    e.getMessage();
                }
                i++;
            }
            inputStream.close();
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        for(int i=0;i<arrayList_user.size();i++){
            String per_id=arrayList_user.get(i).getName();
            ResultSet rs;
            String sql="select * from borrow_record where borrow_state=1 and borrow_person='"+per_id+"'";
            rs=baseDao.execute_DB(sql);
            try{
                if(!rs.next()){
                }
                else
                {
                    do {
                        ResultSetMetaData data = rs.getMetaData();
                        Borrow_record borrow_record=new Borrow_record();
                        borrow_record.setBorrow_record(rs.getInt(1));
                        borrow_record.setRoom_id(rs.getString(2));
                        borrow_record.setBorrow_state(rs.getBoolean(3));
                        borrow_record.setBorrow_person(rs.getString(4));
                        borrow_record.setDay(rs.getString(5));
                        borrow_record.setLesson(rs.getInt(6));
                        borrow_record.setBorrow_date(rs.getString(7));
                        arrayList_user.get(i).borrow_records.add(borrow_record);
                    }while(rs.next());
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public  void add_Borrow_record(String userId,int record_number){
        Borrow_record borrow_record=new Borrow_record();
        borrow_record.setBorrow_record(record_number);
        for(int i=0;i<arrayList_user.size();i++){
            if(arrayList_user.get(i).getName().equals(userId)){
                arrayList_user.get(i).borrow_records.add(borrow_record);
            }
        }
    }
    public void del_Borrow_record(String user_id,int record_number){
        for(int i=0;i<arrayList_user.size();i++){
            if(arrayList_user.get(i).getName().equals(user_id)){
                for(int j=0;j<arrayList_user.get(i).borrow_records.size();j++){
                    if(arrayList_user.get(i).borrow_records.get(j).getBorrow_record()==record_number){
                        arrayList_user.get(i).borrow_records.remove(j);
                    }
                }
            }
        }//测试
//        for(int i=0;i<arrayList_user.size();i++){
//            if(arrayList_user.get(i).getName().equals(user_id)){
//                for(int j=0;j<arrayList_user.get(i).borrow_records.size();j++){
//                    System.out.print(arrayList_user.get(i).borrow_records.get(j).getBorrow_record()+"\t");
//                }
//            }
//        }
    }
    public void step1()//setp1-注册和登录-邹迎童
    {
        System.out.println("请选择您的操作：1.注册 2.登录 3.退出");
        Scanner scanner=new Scanner(System.in);
        try{
            int chose=scanner.nextInt();
            if(chose!=1&&chose!=2&&chose!=3){
                throw new MyException_3();
            }else{
                switch (chose){
                    case 1:
                        User user=new User();
                        user.Register(arrayList_user);

                        step1();
                        break;
                    case 2:
                        login_chose();
                        break;
                    case 3:
                        System.out.print("感谢您的使用");
                        baseDao.close_DB();
                        exit();
                        break;
                    default:
                }
            }

        }catch (MyException_3 e){
            e.printStackTrace();
            step1();
        }catch (InputMismatchException e){
            System.out.println("请输入1，2或者3，请重试");
            step1();
        }
    }
    public void login_chose()
    {
        System.out.println("1.普通用户登录 2.管理员登录");
        Scanner scanner=new Scanner(System.in);
        try{
            int chose2=scanner.nextInt();
            if(chose2!=1&&chose2!=2){
                throw new MyException_2();
            }else{
                switch (chose2){
                    case 1:
                        User user1=new User();
                        user1=login();
                        step2();
                        break;
                    case 2:
                        //管理员登录
                        System.out.println("请输入管理员的账号和密码");
                        String name=scanner.next();
                        String code=scanner.next();
                        if(name.equals("admin")&&code.equals("123456")){
                            Manager m = new Manager();
                            System.out.println("管理员你好");
                            m.init();
                            step1();
                        }else{
                            System.out.println("您输入的信息错误，登录失败");
                            step1();
                        }
                        break;
                }
            }
        }catch (MyException_2 e){
            e.printStackTrace();
            login_chose();
        }catch (InputMismatchException e){
            System.out.println("请输入1或者2，请重试");
            login_chose();
        }


    }
    public User login()//登录功能-邹迎童
    {
        System.out.println("请输入您的用户名和密码");
        Scanner scanner=new Scanner(System.in);
        String name=scanner.next();
        String code=scanner.next();
        for(int i=0;i<arrayList_user.size();i++){
            if(name.equals(arrayList_user.get(i).getName())&&code.equals(arrayList_user.get(i).getPassWord())){
                System.out.println("登录成功");
                uid = name;
                return new User(name,code,i);
            }
        }
        System.out.println("用户名或密码错误请重新输入");
        login();
        return null;
    }

}

//7-8 完成了用户的登录和注册功能-管理员只有一个
//7-9  完成了数据库的连接和基本的数据导入
//7-10 查看完成，预约完成教室部分，剩余会议室部分没有完成
