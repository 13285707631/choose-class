import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class User {
    String  userID;//系统自给
    String  name;
    String passWord;
    ArrayList<Borrow_record> borrow_records=new ArrayList<>();
    public ArrayList<Borrow_record> getBorrow_records() {
        return borrow_records;
    }
    public void setBorrow_records(ArrayList<Borrow_record> borrow_records) {
        this.borrow_records = borrow_records;
    }
    public void Register(ArrayList<User> arrayList_user)//注册函数-邹迎童
    {
        ArrayList<User> users=arrayList_user;
        User user=new User();
        Scanner scanner=new Scanner(System.in);
        String name;
        String code1;
        String code2;
        System.out.println("请输入您的用户名");
        name=scanner.next();
        int i;
        for(i=0;i<users.size();i++){
            if(users.get(i).getName().equals(name)){
                System.out.println("该用户名已存在，请重新输入");
                this.Register(users);
                return;
            }
        }if(i==users.size()){
        System.out.println("请输入您的密码");
        code1=scanner.next();
        System.out.println("请再次确认您的密码");
        code2=scanner.next();
        if(code1.equals(code2))
        {
            user.setName(name);
            user.setPassWord(code1);
            try {
                writeFile(user.getName(),user.getPassWord());
            }catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("注册成功");
            user.setUserID(arrayList_user.size()+"");
            arrayList_user.add(user);
        }
        else
        {
            System.out.println("您输入的密码不一致，注册失败");
            this.Register(users);
        }
    }

    }
    public void writeFile(String id, String pass) throws IOException {//写入文件-邹迎童
        File file=new File(".");
        String path=file.getCanonicalFile()+"\\Text.txt";
        BufferedWriter out = new BufferedWriter(new FileWriter(path,true));
        try{
            out.write(id);
            out.newLine();
            out.write(pass);
            out.newLine();
        }catch (NullPointerException e)
        {
            e.getMessage();
        }
        out.flush();
        out.close();
    }
    User() {}
    User(String name,String passWord,int i)
    {
        this.name=name;
        this.passWord=passWord;
        this.userID=i+"";
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
