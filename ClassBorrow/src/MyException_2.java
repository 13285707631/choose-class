public class MyException_2 extends Exception {
    @Override
    public void printStackTrace() {
        System.out.println("您只能输入1和2，请重试");
    }
}
class MyException_4 extends Exception{
    @Override
    public void printStackTrace() {
        System.out.println("您只能输入1,2,3和4，请重试");
    }
}
