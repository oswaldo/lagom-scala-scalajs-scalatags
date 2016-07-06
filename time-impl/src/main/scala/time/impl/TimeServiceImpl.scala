package time.impl

import time.api.TimeService
import com.lightbend.lagom.javadsl.api.ServiceCall
import akka.NotUsed
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

class TimeServiceImpl extends TimeService{

  
  override def timeAt(tz: String): ServiceCall[NotUsed, String] = {
    new ServiceCall[NotUsed, String]() {
      
      override def invoke(obj: NotUsed) : CompletionStage[String] = {
        val c = new CompletableFuture[String]()
        c.complete("some date")
        c
      }
      
    }
  }

  
}
