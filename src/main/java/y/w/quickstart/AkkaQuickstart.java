package y.w.quickstart;

import akka.actor.typed.ActorSystem;
import akka.stream.Materializer;
import java.util.Random;

public class AkkaQuickstart {
  public static void main(String[] args) throws InterruptedException {
    Random r = new Random();

    //#actor-system
    final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");

    Materializer materializer = Materializer.createMaterializer(greeterMain);

    //#actor-system

      try {
        long count = 1l;

        do {
          //#main-send-messages
          for (int i = 1; i < r.nextInt(50); i++ )
            greeterMain.tell(new GreeterMain.SayHello("Charles_" + count++ ));

          //#main-send-messages
//          System.out.println(">>> Press ENTER to exit <<<");
//          System.in.read();

          Thread.sleep(r.nextInt(10000));
        } while (true);
      } catch (Exception ignored) {
      } finally {
        greeterMain.terminate();
      }

  }
}
