public class Borrow_record {
    private  int borrow_record;
    private String room_id;
    private boolean borrow_state;
    private String borrow_person;
    private String day;
    private int lesson;
    private String borrow_date;
    public int getBorrow_record() {
        return borrow_record;
    }
    public void setBorrow_record(int borrow_record) {
        this.borrow_record = borrow_record;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public boolean isBorrow_state() {
        return borrow_state;
    }

    public void setBorrow_state(boolean borrow_state) {
        this.borrow_state = borrow_state;
    }

    public String getBorrow_person() {
        return borrow_person;
    }

    public void setBorrow_person(String borrow_person) {
        this.borrow_person = borrow_person;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public String getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(String borrow_date) {
        this.borrow_date = borrow_date;
    }
}
