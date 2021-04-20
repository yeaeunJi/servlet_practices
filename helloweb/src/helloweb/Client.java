package helloweb;
public class Client {
    public static void main(String args[]){
        Movable train = new Train();
        Movable bus = new Bus();
        Movable tram = new Tram();

        train.move();
        bus.move();
        tram.move();
    }
}

