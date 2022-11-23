import java.net.Socket;
import java.net.UnknownHostException;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;

public class TCP_Client extends ConcreteSubject implements Runnable {
    //variables
    Message msg;
    String host;
    int port;
    Thread t;
    JSONParser parser;
    double[] acc;
    static double timeStamp;
    double dBpeak;
    int orientation; //ranges from 1 to 6
    double heading; //angle ranges from 0 to 360
    double[] headingXYZ;
    TCP_Client (String ip, int port){
        this.acc = new double[3]; //X, Y, Z
        this.headingXYZ = new double[3]; //X, Y, Z
        this.host = ip;
        this.port = port;
        msg = new Message(this, null, null);
        parser = new JSONParser();
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
		try {
			Socket socket = new Socket(this.host, this.port);
			InputStream input = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(input);

// -------------------------------- new reader -------------------------------
			BufferedReader br = new BufferedReader(reader, 1);
			String line = "";
			while ((line = br.readLine()) != null) {//read line by line
                //parse String to JSON
				JSONObject jsonObject = (JSONObject) parser.parse(line);
                //select a specific value using its key
				acc[0] = Double.parseDouble((String) jsonObject.get("accelerometerAccelerationX"));
                acc[1] = Double.parseDouble((String) jsonObject.get("accelerometerAccelerationY"));
                acc[2] = Double.parseDouble((String) jsonObject.get("accelerometerAccelerationZ"));
                timeStamp = Double.parseDouble((String) jsonObject.get("accelerometerTimestamp_sinceReboot"));
                dBpeak = Double.parseDouble((String) jsonObject.get("avAudioRecorderPeakPower"));
                orientation = Integer.parseInt((String) jsonObject.get("deviceOrientation"));
                heading = Double.parseDouble((String) jsonObject.get("locationTrueHeading"));
                headingXYZ[0] = Double.parseDouble((String) jsonObject.get("locationHeadingX"));
                headingXYZ[1] = Double.parseDouble((String) jsonObject.get("locationHeadingY"));
                headingXYZ[2] = Double.parseDouble((String) jsonObject.get("locationHeadingZ"));
                //sending messages depending on bool values in UIClient
                if(UIClient.getAcc == true)
                {
                    msg = new Message(this, "acc", acc);
                    publishMessage(msg);
                }
                if(UIClient.getDB == true)
                {
                    msg = new Message(this, "dB", dBpeak);
                    publishMessage(msg);
                }
                if(UIClient.getHeading == true)
                {
                    msg = new Message(this, "heading", headingXYZ);
                    publishMessage(msg);
                }
                if(UIClient.getOrientation == true)
                {
                    msg = new Message(this, "orientation", orientation);
                    publishMessage(msg);
                }
			}
            
//----------------------------------------------------------------------------

		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
