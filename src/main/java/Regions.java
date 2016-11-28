import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 27. 11. 2016.
 */
public class Regions {

    static Logger log = Logger.getLogger(Regions.class.getName());

    public static String processData(ResultSet rs){
        StringBuilder result = new StringBuilder();

        try {
            if (!rs.isBeforeFirst() ) {
                return "";
            }
            rs.next();
            //beginning
            result.append("{\"type\": \"geojson\",\"data\": {\"type\": \"FeatureCollection\", \"features\": [");
            //polygon
            result.append("{\"type\": \"Feature\",\"geometry\": ");
            result.append(rs.getString("region"));
            result.append("},");
            //centroid
            result.append("{\"type\": \"Feature\",\"geometry\": ");
            result.append(rs.getString("point"));
            result.append(",\"properties\": { \"title\": \""+rs.getString("name")+"\"}}");

            while(rs.next()){
                String region = rs.getString("region");
                String centroid = rs.getString("point");
                String name = rs.getString("name");
                result.append(",{\"type\": \"Feature\",\"geometry\": ");
                result.append(rs.getString("region"));
                result.append("},");
                result.append("{\"type\": \"Feature\",\"geometry\": ");
                result.append(rs.getString("point"));
                result.append(",\"properties\": { \"title\": \""+rs.getString("name")+"\"}}");


            }
            result.append("]}}");
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("FAILED TO GENERATE JSON FOR CRASH SITES!");
            return "";
        }
        log.info("returned regions geoJSON");
        return result.toString();
    }
}
