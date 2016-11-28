import org.apache.log4j.Logger;
import static spark.Spark.*;


/**
 * Created by Richard on 21. 10. 2016.
 */
public class ApiListener {
    static Logger log = Logger.getLogger(ApiListener.class.getName());

    public ApiListener() {

        String logMessage = "[ ]";

        // GET http://localhost:4567/showcrash?lon=45.44&lan=54.321&rad=30000
        get("/showcrash", (request, response) -> {

            log.info("method: " + request.requestMethod() + " " + request.url() + " "+ request.queryParams("lon")+ request.queryParams("lat") +request.queryParams("rad") + " " + logMessage);

            return ApiResponses.getCarCrashesInRadius(request.queryParams("lon"), request.queryParams("lat"), request.queryParams("rad"));
        });

        get("/showcrashbyregion", (request, response) -> {

            log.info("method: " + request.requestMethod() + " " + request.url() + " "+ request.queryParams("lon")+ request.queryParams("lat") +" " + logMessage);

            return ApiResponses.getCarCrashesByRegion(request.queryParams("lon"), request.queryParams("lat"));
        });

        get("/showcrashbyroad", (request, response) -> {

            log.info("method: " + request.requestMethod() + " " + request.url() + " "+ request.queryParams("lon")+ request.queryParams("lat") +request.queryParams("rad") + " " + logMessage);

            return ApiResponses.getCarCrashesByRoad(request.queryParams("lon"), request.queryParams("lat"), request.queryParams("rad"));
        });

        get("/showregions", (request, response) -> {

            log.info("method: " + request.requestMethod() + " " + request.url()+" " + logMessage);

            return ApiResponses.getRegions();
        });

    }
}
