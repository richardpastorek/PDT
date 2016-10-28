import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import static spark.Spark.port;

/**
 * Created by richardpastorek on 21.10.2016.
 */
public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        port(4567);

        String id;



        try {
            id = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {

            id = UUID.randomUUID().toString();
            e1.printStackTrace();
        }

        try {
            logger.info("Starting server on port 4567");
            new ApiListener();
            new DBManager();
            DBManager.getConnection();
        } catch(Exception e) {
            e.printStackTrace();
            logger.fatal("Cannot initialize server, exiting...");
            return;
        }
    }

}