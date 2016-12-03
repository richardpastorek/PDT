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
            double longitude = Double.parseDouble(lon);
            double latitude = Double.parseDouble(lat);
            int radius = Integer.parseInt(rad);


            return DBManager.getCarCrashesInRadius(longitude, latitude, radius);
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
            double longitude = Double.parseDouble(lon);
            double latitude = Double.parseDouble(lat);

            return DBManager.getCrashesByRegion(longitude, latitude);
        }
    }

    public static String getCarCrashesByRoad(String lon, String lat, String width){
        if(lon==null || lon == "" || lat == null || lat=="" || width == null || width ==""){
            log.info("One of the parameters was empty");
            return "";
        }

        else{
            double longitude = Double.parseDouble(lon);
            double latitude = Double.parseDouble(lat);
            int road_width = Integer.parseInt(width);
            return DBManager.getCrashesByRoad(longitude, latitude, road_width);
        }
    }

}
