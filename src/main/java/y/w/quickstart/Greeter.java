package y.w.quickstart;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;
import y.w.stream.common.Busy;

// #greeter
public class Greeter extends AbstractBehavior<Greeter.Greet> {
  private final Busy busy = new Busy();
  private final Random random = new Random();

  public static final class Greet {
    public final String whom;
    //public final ActorRef<Greeted> replyTo;
    public final ActorRef<Greeter.Greeted> replyTo;

    public Greet(String whom, ActorRef<Greeter.Greeted> replyTo) {
      this.whom = whom;
      this.replyTo = replyTo;
    }
  }

  public static final class Greeted {
    public final String whom;
    public final ActorRef<Greet> from;

    public Greeted(String whom, ActorRef<Greet> from) {
      this.whom = whom;
      this.from = from;
    }

// #greeter
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Greeted greeted = (Greeted) o;
      return Objects.equals(whom, greeted.whom) &&
              Objects.equals(from, greeted.from);
    }

    @Override
    public int hashCode() {
      return Objects.hash(whom, from);
    }

    @Override
    public String toString() {
      return "Greeted{" +
              "whom='" + whom + '\'' +
              ", from=" + from +
              '}';
    }
// #greeter
  }

  public static Behavior<Greet> create() {
    return Behaviors.setup(Greeter::new);
  }

  private Greeter(ActorContext<Greet> context) {
    super(context);
  }

  @Override
  public Receive<Greet> createReceive() {
    return newReceiveBuilder().onMessage(Greet.class, this::onGreet).build();
  }

  private Behavior<Greet> onGreet(Greet command) {
    busy.busy(Duration.ofMillis(random.nextInt(1000)));

    getContext().getLog().info("Hello {}!", command.whom);
    //#greeter-send-message
    command.replyTo.tell(new Greeted(command.whom, getContext().getSelf()));
    //#greeter-send-message
    return this;
  }
}
// #greeter

