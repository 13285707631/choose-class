public class MyException_3 extends Exception {
    @Override
    public void printStackTrace() {
        System.out.println("您只能输入1，2和3，请重试");
    }
}
