import org.apache.log4j.Logger;
import static spark.Spark.*;

/**
 * Created by Richard on 21. 10. 2016.
 */
public class ApiListener {
    static Logger log = Logger.getLogger(ApiListener.class.getName());

    public ApiListener() {

        String logMessage = "[ ]";


        get("/hello", (request, response) -> {

            log.info("method: " + request.requestMethod() + " " + request.url() + " " + logMessage);

            return ApiResponses.getHello();
        });
    }
}
