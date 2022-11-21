public class Message {
    Object origin;
    String topic;
    Object payload;

    Message(Object origin, String topic, Object payload){
        this.origin=origin;
        this.topic=topic;
        this.payload=payload;
    }

    @Override
    public String toString() {
        return "origin:"+origin+"\ntopic:"+topic+"\npayload:"+payload;
    }
}
