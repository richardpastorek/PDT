import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

/**
 * Created by Richard on 21. 10. 2016.
 */
public class  DBManager {

    static Logger log = Logger.getLogger(DBManager.class.getName());

    private static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/projekt", "postgres",
                    "pdt");
        } catch (SQLException e) {

            log.info("Connection Failed! Check output console");
            e.printStackTrace();
            return null;

        }

        log.info("Connected to database");
        return connection;
    }

    private static void closeConnection(Connection connection) {
        try{
            if(connection!= null){
                connection.close();
                log.info("Connection closed");
            }
        }catch(Exception e){
            log.info("Closing connection failed!");
            e.printStackTrace();
        }

    }

    public static String getCarCrashesInRadius(String longitude, String latitude, String radius){
        Connection con = getConnection();
        Statement statement = null;
        String query = "SELECT ST_AsGeoJSON(geom) AS miesto_havarie, number_of_vehicles AS auta, number_of_casualties AS obete, date FROM accidents_2015 " +
                "where ST_DISTANCE(geom::geography, ST_GeomFromText('POINT("+longitude+" "+latitude+")',4326)::geography) <= "+radius;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            closeConnection(con);
            return CrashSites.processData(rs);
        }catch(Exception e){
            log.info("FAILED TO GET CAR CRASHES IN DBMANAGER!");
            e.printStackTrace();
        }
        log.info("DBMANAGER RETURNING: nothing");
        return "";
    }

    public static String getRegions(){
        Connection con = getConnection();
        Statement statement = null;
        String query = "SELECT ST_AsGeoJSON(geom) AS region, ST_AsGeoJSON(ST_CENTROID(geom)) AS point, name_2 AS name FROM gbr_adm2";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            closeConnection(con);
            return Regions.processData(rs);
        }catch(Exception e){
            log.info("FAILED TO GET GBR_ADM2 geoms IN DBMANAGER!");
            e.printStackTrace();
        }
        log.info("DBMANAGER RETURNING: nothing");
        return "";
    }



    public static String getCrashesByRegion(String longitude, String latitude){
        Connection con = getConnection();
        Statement statement = null;
        String query = "SELECT ST_AsGeoJSON(geom) AS miesto_havarie, number_of_vehicles AS auta, number_of_casualties AS obete, date from accidents_2015 " +
                "where ST_WITHIN(ST_SetSRID(geom, 4326), ST_SetSRID( (SELECT geom FROM gbr_adm2 WHERE ST_WITHIN(ST_SetSRID(ST_MAKEPOINT("+longitude+", "+latitude+"),4326), ST_SetSRID((geom),4326)  ) is true), 4326)) is true";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            closeConnection(con);
            return CrashSites.processData2(rs);
        }catch(Exception e){
            log.info("FAILED TO GET GBR_ADM2 geoms IN DBMANAGER!");
            e.printStackTrace();
        }
        log.info("DBMANAGER RETURNING: nothing");
        return "";
    }

    public static String getCrashesByRoad(String lon, String lat, String width){

        Connection con = getConnection();
        Statement statement = null;
        String query1 = "select ST_AsGeoJson(ST_BUFFER(ST_SetSRID(geom, 4326)::geography, "+width+")::geometry) AS buff FROM \"gis.osm_roads_free_1\" WHERE ST_CROSSES(ST_BUFFER(ST_SETSRID(ST_MAKEPOINT("+lon+", "+lat+"),4326)::geography, 2)::geometry, ST_SetSRID(geom,4326)) is true LIMIT 1";
        try {
            long time = System.currentTimeMillis();
            log.info("Started executing 1st query");
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query1);
            log.info("1st query finished in: "+ Long.toString(System.currentTimeMillis() - time));


            closeConnection(con);

            ResultSet rs2 = null;

            if(rs.next()) {

                String query2 = "SELECT ST_AsGeoJSON(geom) AS miesto_havarie, number_of_vehicles AS auta, number_of_casualties AS obete, date FROM accidents_2015 where ST_WITHIN (ST_SetSRID(geom, 4326), ST_SetSRID(ST_GeomFromGeoJson('" + rs.getString("buff") + "'),4326)) is true";
                con = getConnection();
                statement = null;
                time = System.currentTimeMillis();
                log.info("Started executing 2nd query");
                statement = con.createStatement();
                rs2 = statement.executeQuery(query2);
                log.info("2nd query finished in: " + Long.toString(System.currentTimeMillis() - time));
                closeConnection(con);
                return CrashSites.processData3(rs, rs2);
            }


        }catch(Exception e){
            log.info("FAILED TO GET CRASHES BY ROAD geoms IN DBMANAGER!");
            e.printStackTrace();
        }
        log.info("No roads found");
        return "";
    }

}
