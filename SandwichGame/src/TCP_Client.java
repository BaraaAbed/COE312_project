import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
    static double[] acc;
    static double timeStamp;
    static double dBpeak;
    static int orientation; //ranges from 1 to 6
    static double heading; //angle ranges from 0 to 360
    static double[] headingXYZ;
    static double[] gyro;
    TCP_Client (){
        this.acc = new double[3]; //X, Y, Z
        this.headingXYZ = new double[3]; //X, Y, Z
        this.gyro = new double[3]; //X, Y, Z
        Scanner scan = new Scanner(System.in);
        String c = "bla";
        do {
            System.out.print("Enter IP: ");
            if(scan.hasNextLine()) {
                String ip = scan.nextLine();
                this.host = ip;
            }
            System.out.print("Enter port: ");
            if(scan.hasNextLine()) {
                String port = scan.nextLine();
                this.port = Integer.parseInt(port);
            }
            do {
                System.out.print("IP: " + this.host + " | Port: " + this.port + ". Are you sure? [y/n]: ");
                if(scan.hasNextLine())
                {
                    c = scan.nextLine();
                }
            } while (!(c.equalsIgnoreCase("y")) && !(c.equalsIgnoreCase("n")));
        } while(!(c.equalsIgnoreCase("y")));
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
                gyro[0] = Double.parseDouble((String) jsonObject.get("gyroRotationX"));
                gyro[1] = Double.parseDouble((String) jsonObject.get("gyroRotationY"));
                gyro[2] = Double.parseDouble((String) jsonObject.get("gyroRotationZ"));
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
                if(UIClient.getGyro == true)
                {
                    msg = new Message(this, "orientation", gyro);
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

    static int getIndexFromDir(char dir) {
        int index;
        switch (dir) {
            case 'X':
            index = 0;
            break;
            case 'Y':
            index = 1;
            break;
            case 'Z':
            index = 2;
            break;
            default:
            index = 3;
        }
        return index;
    }

    //useful for continuous shaking applications
    static boolean avgAccAboveThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        double totalAcc = 0; 
        double x = 0;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            totalAcc += Math.abs(acc[index]);
            x++;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        double avgAcc = totalAcc/(double) x;
        if (avgAcc > threshold) return true;
        else return false;
    }
    //useful for attacks
    static double[] getAvgAcc(double duration) {
        double initTime = System.currentTimeMillis();
        double[] totalAcc = new double[3];
        totalAcc[0] = 0; 
        totalAcc[1] = 0; 
        totalAcc[2] = 0; 
        double x = 0;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            totalAcc[0] += Math.abs(acc[0]);
            totalAcc[1] += Math.abs(acc[1]);
            totalAcc[2] += Math.abs(acc[2]);
            x++;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        double[] avgAcc = new double[3];
        avgAcc[0] = totalAcc[0]/(double) x;
        avgAcc[1] = totalAcc[1]/(double) x;
        avgAcc[2] = totalAcc[2]/(double) x;
        return avgAcc;
    }
    //anything +/- 2 is considered a large movement
    //useful for dodging (for +ve axis)
    static boolean peakAccAboveThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        double maxAcc = 0; 
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (acc[index] > maxAcc) { maxAcc = acc[index]; }
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        if (maxAcc > threshold) return true;
        else return false;
    }
    //useful for dodging (for -ve axis)
    static boolean minAccBelowThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        double minAcc = 0;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (acc[index] < minAcc) minAcc = acc[index];
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        if (minAcc < threshold) return true;
        else return false;
    }
    //anything +/- 10 is considered a flick for gyro
    //useful for sabotage (for +ve axis)
    static boolean peakGyroAboveThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        double maxGyro = 0; 
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (gyro[index] > maxGyro) maxGyro = gyro[index];
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        if (maxGyro > threshold) return true;
        else return false;
    }
    //useful for sabotage (for -ve axis)
    static boolean minGyroBelowThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        double minGyro = 0;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (gyro[index] < minGyro) minGyro = gyro[index];
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        if (minGyro < threshold) return true;
        else return false;
    }
    //Sound value is always negative where 0 is full sound. Good threshold is -25 for silence (will change depending on environment)
    //useful for detecting sound
    static boolean peakSoundBelowThreshold(double duration, double threshold) {
        double initTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (dBpeak > threshold) return false;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        return true;
    }
    //useful for putting ingredients
    //Current pattern: tilt device to "landscape left" -> tilt device to "landscape right" -> move device down
    //ingName used to print. pass in legendary X
    //make sure duration is long (like maybe 10 or 20 secs)
    static boolean putIngredient(double duration, String ingName) {
        double initTime = System.currentTimeMillis();
        double bla = 0;
        boolean LL = false;
        boolean LR = false;
        boolean shake = false;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (!LL && !LR && !shake) {
                if (orientation == 3) {
                    LL = true;
                    System.out.println("You picked up the " + ingName + "! Put it on the sandwich before its too late.");
                    bla = System.currentTimeMillis();
                }
            }
            else if (!LR && !shake) {
                while ((System.currentTimeMillis() - bla) < 1000) {
                    if (orientation == 4) {
                        LR = true;
                        bla = 0;
                    }
                }
                if (LR) {
                    System.out.println("The " + ingName + " is in position. You must exert great force to place it!");
                    bla = System.currentTimeMillis();
                }
                else {
                    System.out.println("You were too late! The " + ingName + " teleported back to your inventory!");
                    LL = false;
                }
            }
            else if (!shake) {
                if (peakAccAboveThreshold('X', 0.5, 5.0)) {
                    System.out.println("The " + ingName + " has been placed succesfully!");
                    bla = System.currentTimeMillis();
                    shake = true;
                }
                else {
                    System.out.println("Come on. You need to exert more force than that to place the " + ingName + "!");
                    LL = false;
                    LR = false;
                }
            }
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        return false;
    }
}
