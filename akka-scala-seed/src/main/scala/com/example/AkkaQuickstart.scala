//#full-example
package com.example

import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
//import com.example.GreeterMain.SayHello
import com.example.OrderProcessor.order

//#greeter-actor
/*object Greeter {
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  final case class Greeted(whom: String, from: ActorRef[Greet])

  def apply(): Behavior[Greet] = Behaviors.receive { (context, message) =>
    context.log.info("Hello {}!", message.whom)
    //#greeter-send-messages
    message.replyTo ! Greeted(message.whom, context.self)
    //#greeter-send-messages
    Behaviors.same
  }
}*/
//#greeter-actor

//#greeter-bot
/*object GreeterBot {

  def apply(max: Int): Behavior[Greeter.Greeted] = {
    bot(0, max)
  }

  private def bot(greetingCounter: Int, max: Int): Behavior[Greeter.Greeted] =
    Behaviors.receive { (context, message) =>
      val n = greetingCounter + 1
      context.log.info("Greeting {} for {}", n, message.whom)
      if (n == max) {
        Behaviors.stopped
      } else {
        message.from ! Greeter.Greet(message.whom, context.self)
        bot(n, max)
      }
    }
}*/
//#greeter-bot

//#greeter-main
/*object GreeterMain {

  final case class SayHello(name: String)

  def apply(): Behavior[SayHello] =
    Behaviors.setup { context =>
      //#create-actors
      val greeter = context.spawn(Greeter(), "greeter")
      //#create-actors

      Behaviors.receiveMessage { message =>
        //#create-actors
        val replyTo = context.spawn(GreeterBot(max = 3), message.name)
        //#create-actors
        greeter ! Greeter.Greet(message.name, replyTo)
        Behaviors.same
      }
    }
}*/
//#greeter-main

/* The main actor (entry point for our actor system) */
object OrderProcessor {
  final case class order(id: Int, product: String, number: Int)

  /* Behaviors method receives orders, print message  */
  def apply(): Behavior[order] = Behaviors.receiveMessage {
    message =>
      printf(message.toString())

      /* advice to use previous behavior, avoiding overhead to recreate behaviors */
      Behaviors.same
  }
}

//#main-class
object AkkaQuickstart extends App {
  val orderProcessor: ActorSystem[OrderProcessor.order] =
    ActorSystem(OrderProcessor(), "orders")

    /* ! is async call */
    orderProcessor ! order(0, "Jacket", 2)
    orderProcessor ! order(1, "Shoes", 1)
    orderProcessor ! order(2, "Socks", 5)
    orderProcessor ! order(3, "Flip flops", 3)
  //#actor-system
  //val greeterMain: ActorSystem[GreeterMain.SayHello] = ActorSystem(GreeterMain(), "AkkaQuickStart")
  //#actor-system

  //#main-send-messages
  //greeterMain ! SayHello("Charles")
  //#main-send-messages
}
//#main-class
//#full-example
