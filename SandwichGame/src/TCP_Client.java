import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;

public class TCP_Client extends ConcreteSubject implements Runnable {
    //variables
    private static TCP_Client instance;//singleton
    private String host;
    private int port;
    private Thread t;
    private JSONParser parser;
    private double[] acc;
    private Object lockTime; //"lockX" objects used to synchronize variables of primitive data types
    private double timeStamp;
    private Object lockDB;
    private double dBpeak;
    private Object lockOrientation;
    private int orientation; //ranges from 1 to 6
    private double[] gyro;

    private TCP_Client () {
        this.acc = new double[3]; //X, Y, Z
        this.gyro = new double[3]; //X, Y, Z
        this.lockTime = new Object();
        this.lockDB = new Object();
        this.lockOrientation = new Object();
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
        parser = new JSONParser();
        t = new Thread(this);
        t.start();
    }

    //gets instance (for singleton)
    public static synchronized TCP_Client getInstance(){
        if(instance == null) instance = new TCP_Client();
        return instance;
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
                synchronized (acc) {
                    acc[0] = Double.parseDouble((String) jsonObject.get("accelerometerAccelerationX"));
                    acc[1] = Double.parseDouble((String) jsonObject.get("accelerometerAccelerationY"));
                    acc[2] = Double.parseDouble((String) jsonObject.get("accelerometerAccelerationZ"));
                }
                synchronized (lockTime) {
                    timeStamp = Double.parseDouble((String) jsonObject.get("accelerometerTimestamp_sinceReboot"));
                }
                synchronized (lockDB) {
                    dBpeak = Double.parseDouble((String) jsonObject.get("avAudioRecorderPeakPower"));
                }
                synchronized (lockOrientation) {
                    orientation = Integer.parseInt((String) jsonObject.get("deviceOrientation"));
                }
                synchronized (gyro) {
                    gyro[0] = Double.parseDouble((String) jsonObject.get("gyroRotationX"));
                    gyro[1] = Double.parseDouble((String) jsonObject.get("gyroRotationY"));
                    gyro[2] = Double.parseDouble((String) jsonObject.get("gyroRotationZ"));
                }
			}
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public synchronized double[] getAcc() {
        return acc;
    }

    public synchronized double getTimeStamp() {
        return timeStamp;
    }

    public synchronized double getdBpeak() {
        return dBpeak;
    }

    public synchronized int getOrientation() {
        return orientation;
    }

    public synchronized double[] getGyro() {
        return gyro;
    }

    private int getIndexFromDir(char dir) {
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

    //function made to synchronize the if statement only rather than keeping hold of resources for aprrox. 200ms
    private boolean check(double LHS, double RHS) {
        synchronized (acc) {
            if (LHS < RHS) return true;
            else return false;
        }
    }

    //useful for continuous shaking applications
    public boolean avgAccAboveThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        double totalAcc = 0; 
        double x = 0;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            synchronized (acc) {
                totalAcc += Math.abs(acc[index]);
                x++;
            }
        }
        double avgAcc = totalAcc/(double) x;
        if (avgAcc > threshold) return true;
        else return false;
    }
    //useful for attacks
    public double[] getAvgAcc(double duration) {
        double initTime = System.currentTimeMillis();
        double[] totalAcc = new double[3];
        totalAcc[0] = 0; 
        totalAcc[1] = 0; 
        totalAcc[2] = 0; 
        double x = 0;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            synchronized (acc) {
                totalAcc[0] += Math.abs(acc[0]);
                totalAcc[1] += Math.abs(acc[1]);
                totalAcc[2] += Math.abs(acc[2]);
                x++;
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
    public boolean peakAccAboveThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (check(acc[index], -threshold)) { 
                for(double x = System.currentTimeMillis()-1; System.currentTimeMillis() - x < 200;) {
                    synchronized (acc) { if (acc[index] > threshold) return true; }
                }
                return false;
            }
        }
        return false;
    }
    //useful for dodging (for -ve axis)
    public boolean minAccBelowThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (check(-threshold, acc[index])) { 
                for(double x = System.currentTimeMillis()-1; System.currentTimeMillis() - x < 200;) {
                    synchronized (acc) { if (acc[index] < threshold) return true; }
                }
                return false;
            }
        }
        return false;
    }
    //anything +/- 10 is considered a flick for gyro
    //useful for sabotage (for +ve axis)
    public boolean peakGyroAboveThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            synchronized (gyro) { if (gyro[index] > threshold) return true; }
        }
        return false;
    }
    //useful for sabotage (for -ve axis)
    public boolean minGyroBelowThreshold(char dir, double duration, double threshold) {
        int index = getIndexFromDir(dir);
        if (index == 3) return false;
        double initTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            synchronized (gyro) { if (gyro[index] < threshold) return true; }
        }
        return false;
    }
    //Sound value is always negative where 0 is full sound. Good threshold is -25 for silence (will change depending on environment)
    //useful for detecting sound
    public boolean peakSoundBelowThreshold(double duration, double threshold) {
        double initTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            synchronized (lockDB) { if (dBpeak > threshold) return false; }
        }
        return true;
    }
    public double getAvgSound(double duration) {
        double initTime = System.currentTimeMillis();
        double totalDB = 0;
        double x = 0;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            synchronized (lockDB) {
                totalDB += Math.abs(dBpeak);
                x++;
            }
        }
        double avgDB = totalDB/(double) x;
        return avgDB;
    }
    //useful for putting ingredients
    //Current pattern: tilt device to "landscape left" -> tilt device to "landscape right" -> move device down
    //ingName used to print. pass in legendary X
    //make sure duration is long (like maybe 10 or 20 secs)
    public boolean putIngredient(double duration, String ingName) {
        double initTime = System.currentTimeMillis();
        double bla = 0;
        boolean LL = false;
        boolean LR = false;
        boolean shake = false;
        while ((System.currentTimeMillis() - initTime) < duration*1000) {
            if (!LL && !LR && !shake) {
                synchronized (lockOrientation) {
                    if (orientation == 3) {
                        LL = true;
                        System.out.println("You picked up the " + ingName + "! Put it on the sandwich before its too late.");
                        bla = System.currentTimeMillis();
                    }
                }
            }
            else if (!LR && !shake) {
                while ((System.currentTimeMillis() - bla) < 1000) {
                    synchronized (lockOrientation) {
                        if (orientation == 4) {
                            LR = true;
                            bla = 0;
                        }
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
        }
        return false;
    }
}
