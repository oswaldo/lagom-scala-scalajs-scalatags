package time.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import akka.NotUsed;

public interface TimeService extends Service {

    /**
     * Example: curl http://localhost:9000/api/timeAt/UTC
     */
    ServiceCall<NotUsed, String> timeAt(String tz);

    @Override
    default Descriptor descriptor() {
        return named("timeservice") //
                .withCalls(pathCall("/api/timeAt/:tz", this::timeAt)).withAutoAcl(true);
    }

}
