import Interface.RoomInter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager extends User {
    private String MgrID="admin";
    private String passWord="123456";

    public String getMgrID() {
        return MgrID;
    }

    @Override
    public String getPassWord() {
        return passWord;
    }

    public void init(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请选择您的操作（1.增加 2.删除 3.退出登录）");
        try{
            int choose = scanner.nextInt();
            if(choose!=1&&choose!=2&&choose!=3){
                throw  new MyException_3();
            }else
            {
                switch (choose){
                    case 1:
                        System.out.println("请选择您要操作的房间类型（1.教室 2.会议室）");
                        int choose1 = scanner.nextInt();
                        roomFactory roomFactory=new roomFactory();
                        RoomInter  roomInter=roomFactory.getRoomType(choose1);
                        roomInter.addRoom();
                        init();
                        if(choose1!=1&&choose1!=2){
                            System.out.println("您只能输入1或者2");
                            init();
                        }
                        break;
                    case 2:
                        System.out.println("请选择您要操作的房间类型（1.教室 2.会议室）");
                        int choose2 = scanner.nextInt();
                        if(choose2==1){
                            classRoom cla = new classRoom();
                            cla.delRoom();
                            init();
                        }
                        else if(choose2==2){
                            meetingRoom meeting = new meetingRoom();
                            meeting.delRoom();
                            init();
                        }
                        else{
                            System.out.println("您只能输入1或者2");
                            init();
                        }
                        break;
                    case 3:
                        return;
                }
            }
        }catch (MyException_3 e){
            e.printStackTrace();
            init();
        }catch (InputMismatchException e){
            System.out.println("请输入1，2或者3，请重试");
            init();
        }
    }





}
