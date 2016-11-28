import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Richard on 26. 11. 2016.
 */
public class CrashSites {

    static Logger log = Logger.getLogger(CrashSites.class.getName());

    public static String processData(ResultSet rs){
       StringBuilder result = new StringBuilder();
    int i = 1;
        try {
            if (!rs.isBeforeFirst() ) {
                return "";
            }
            rs.next();
            result.append("{\"type\": \"geojson\",\"data\": {\"type\": \"FeatureCollection\", \"features\": [{\"type\": \"Feature\",\"geometry\": ");
            result.append(rs.getString("miesto_havarie"));
            result.append(",\"properties\": { \"title\": \"crash "+i+"\",\"description\": \"Datum: "+rs.getString("date")+ "</br>Pocet obeti: "+rs.getString("obete")+"</br>Pocet aut: "+rs.getString("auta")+"\"}}");
            i++;
            while(rs.next()){
                String locationGeoJson = rs.getString("miesto_havarie");
                String casualties = rs.getString("obete");
                String cars = rs.getString("auta");
                result.append(",{\"type\": \"Feature\",\"geometry\": ");
                result.append(rs.getString("miesto_havarie"));
                result.append(",\"properties\": { \"title\": \"crash "+i+"\",\"description\": \"Datum: "+rs.getString("date")+ "</br>Pocet obeti : "+rs.getString("obete")+"</br>Pocet aut: "+rs.getString("auta")+"\"}}");
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

    public static String processData2(ResultSet rs){
        StringBuilder result = new StringBuilder();
        int i = 1;
        try {
            if (!rs.isBeforeFirst() ) {
                return "";
            }
            rs.next();
            result.append("{\"type\": \"geojson\",\"data\": {\"type\": \"FeatureCollection\", \"features\": [{\"type\": \"Feature\",\"geometry\": ");
            result.append(rs.getString("miesto_havarie"));
            result.append(",\"properties\": { \"title\": \"crash "+i+"\",\"description\": \"Datum: "+rs.getString("date")+ "</br>Pocet obeti : "+rs.getString("obete")+"</br>Pocet aut: "+rs.getString("auta")+"\"}}");
            i++;
            while(rs.next()){
                result.append(",{\"type\": \"Feature\",\"geometry\": ");
                result.append(rs.getString("miesto_havarie"));
                result.append(",\"properties\": { \"title\": \"crash "+i+"\",\"description\": \"Datum: "+rs.getString("date")+ "</br>Pocet obeti : "+rs.getString("obete")+"</br>Pocet aut: "+rs.getString("auta")+"\"}}");
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

    public static String processData3(ResultSet cesta, ResultSet nehody){

        StringBuilder result = new StringBuilder();
        int i = 1;
        try {

            result.append("{\"type\": \"geojson\",\"data\": {\"type\": \"FeatureCollection\", \"features\": [{\"type\": \"Feature\",\"geometry\": ");
            result.append(cesta.getString("buff"));
            result.append("}");

            while(nehody.next()){
                result.append(",{\"type\": \"Feature\",\"geometry\": ");
                result.append(nehody.getString("miesto_havarie"));
                result.append(",\"properties\": { \"title\": \"crash "+i+"\",\"description\": \"Datum: "+nehody.getString("date")+ "</br>Pocet obeti : "+nehody.getString("obete")+"</br>Pocet aut: "+nehody.getString("auta")+"\"}}");
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
