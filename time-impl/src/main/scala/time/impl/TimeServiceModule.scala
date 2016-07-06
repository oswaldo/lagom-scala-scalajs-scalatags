package time.impl

import com.google.inject.AbstractModule
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport
import time.api.TimeService

class TimeServiceModule extends AbstractModule with ServiceGuiceSupport {
  
  override def configure = {
    bindServices(serviceBinding(classOf[TimeService], classOf[TimeServiceImpl]))
  }

}