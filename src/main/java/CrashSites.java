import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 26. 11. 2016.
 */
public class CrashSites {

    static Logger log = Logger.getLogger(CrashSites.class.getName());


    public static String processData3(ResultSet cesta, ResultSet nehody){

        StringBuilder result = new StringBuilder();
        int i = 1;
        try {

            result.append("{\"type\": \"geojson\",\"data\": {\"type\": \"FeatureCollection\", \"features\": [{\"type\": \"Feature\",\"geometry\": ");
            result.append(cesta.getString("buff"));
            result.append("}");
            while(cesta.next()){
                result.append(",{\"type\": \"Feature\",\"geometry\": ");
                result.append(cesta.getString("buff"));
                result.append("}");
            }


            while(nehody.next()){
                result.append(",{\"type\": \"Feature\",\"geometry\": ");
                result.append(nehody.getString("miesto_havarie"));
                result.append(",\"properties\": { \"date\": \""+nehody.getString("date")+"\",\"auta\": \""+nehody.getString("auta")+ "\",\"obete\": \""+nehody.getString("obete")+"\"}}");
                i++;
            }
            result.append("]}}");
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("FAILED TO GENERATE JSON FOR CRASH SITES!");
            return "";
        }
        log.info(result.toString());
        return result.toString();


    }
}
