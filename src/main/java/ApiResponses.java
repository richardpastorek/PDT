import org.apache.log4j.Logger;

/**
 * Created by Richard on 21. 10. 2016.
 */
public class ApiResponses {

    static Logger log = Logger.getLogger(ApiResponses.class.getName());

    public static String getHello() {
        return "Hello from the Spark";
    }

    public static String getCarCrashesInRadius(String lon, String lat, String rad){
        if(lon==null || lon == "" || lat == null || lat=="" || rad==null || rad==""){
            log.info("One of the parameters was empty");
            return "";
        }

        else{
            return DBManager.getCarCrashesInRadius(lon, lat, rad);
        }
    }

    public static String getRegions(){

        return DBManager.getRegions();

    }

    public static String getCarCrashesByRegion(String lon, String lat){
        if(lon==null || lon == "" || lat == null || lat==""){
            log.info("One of the parameters was empty");
            return "";
        }

        else{
            return DBManager.getCrashesByRegion(lon, lat);
        }
    }

    public static String getCarCrashesByRoad(String lon, String lat, String width){
        if(lon==null || lon == "" || lat == null || lat=="" || width == null || width ==""){
            log.info("One of the parameters was empty");
            return "";
        }

        else{
            return DBManager.getCrashesByRoad(lon, lat, width);
        }
    }

}
