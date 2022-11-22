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
    TCP_Client (String ip, int port){
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
				String accY = (String) jsonObject.get("accelerometerAccelerationY");
                //save the selected value
				
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
