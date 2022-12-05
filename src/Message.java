public class Message {
    Object origin;
    String topic;
    Object payload;
    double[] arrPaylaod;

    Message(Object origin, String topic, Object payload){
        this.origin=origin;
        this.topic=topic;
        this.payload=payload;
        this.arrPaylaod=null;
    }

    Message(Object origin, String topic, double[] payload){ //created since array stuff cant be done on payload of type Object
        this.origin=origin;
        this.topic=topic;
        this.arrPaylaod=payload;
        this.payload=null;
    }

    @Override
    public String toString() {
        return "origin:"+origin+"\ntopic:"+topic+"\npayload:"+payload;
    }
}
