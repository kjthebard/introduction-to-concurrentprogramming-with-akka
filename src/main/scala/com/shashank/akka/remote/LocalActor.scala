package com.shashank.akka.remote

import java.io.File

import akka.actor._
import com.typesafe.config.ConfigFactory

/**
  * Created by shashank on 20/03/17.
  */
class LocalActor extends Actor{
  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    /*
      Connect to remote actor. The following are the different parts of actor path

      akka.tcp : enabled-transports  of remote_application.conf

      RemoteSystem : name of the actor system used to create remote actor

      127.0.0.1:5150 : host and port

      user : The actor is user defined

      remote : name of the actor, passed as parameter to system.actorOf call

     */
    val remoteActor = context.actorSelection("akka.tcp://RemoteSystem@127.0.0.1:5150/user/remote")
    println("That 's remote:" + remoteActor)
    remoteActor ! "hi"
  }
  override def receive: Receive = {

    case msg:String => {
      println("got message from remote" + msg)
    }
  }
}


object LocalActor {
  def main(args: Array[String]) {
    val config = ConfigFactory.parseFile(new File("src/main/resources/remote/local_application.conf"))
    val system = ActorSystem("LocalSystem" , config)
    val remote = system.actorOf(Props[LocalActor], name="local")
  }
}