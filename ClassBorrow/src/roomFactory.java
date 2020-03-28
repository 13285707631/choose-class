import Interface.RoomInter;

public class roomFactory {
    public RoomInter getRoomType(int type){

         if(type==1){
             return new classRoom();
         }
         else if(type==2){
            return new meetingRoom();
         } else{
            System.out.println("房间类型错误");
            return null;
         }
    }

}
