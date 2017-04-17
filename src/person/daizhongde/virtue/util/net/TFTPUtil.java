package person.daizhongde.virtue.util.net;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.net.tftp.TFTPClient;
import org.apache.commons.net.tftp.TFTPPacket;

public class TFTPUtil {
	public static boolean receive(int transferMode, String hostname, String localFilename, String remoteFilename,
            int timeout, boolean verbose, String user, String password) {
//        int transferMode = TFTP.BINARY_MODE, argc;
//        String arg, hostname, localFilename, remoteFilename;
        final TFTPClient tftp;
//        int timeout = 60000;
//        boolean verbose = false;
        
        if (verbose) {
            tftp = new TFTPClient() {
                @Override
                protected void trace(String direction, TFTPPacket packet) {
                    System.out.println(direction + " " + packet);
                }
            };
        } else {
            tftp = new TFTPClient();
        }

//        tftp.login(user, password);
        // We want to timeout if a response takes longer than 60 seconds
        tftp.setDefaultTimeout(timeout);

        
		return receive(transferMode, hostname, localFilename, remoteFilename, tftp);
	}
	
	
    private static boolean receive(int transferMode, String hostname, String localFilename, String remoteFilename,
            TFTPClient tftp) {
        boolean closed;
        FileOutputStream output = null;
        File file;

        file = new File(localFilename);

        System.out.println("localFilename:"+localFilename);
        // If file exists, don't overwrite it.
        if (file.exists())
        {
            System.err.println("Error: " + localFilename + " already exists.");
            System.exit(1);
        }

        // Try to open local file for writing
        try
        {
            output = new FileOutputStream(file);
        }
        catch (IOException e)
        {
            tftp.close();
            System.err.println("Error: could not open local file for writing.");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        open(tftp);

        // Try to receive remote file via TFTP
        try
        {
            String [] parts = hostname.split(":");
            if (parts.length == 2) {
                tftp.receiveFile(remoteFilename, transferMode, output, parts[0], Integer.parseInt(parts[1]));
            } else {
                tftp.receiveFile(remoteFilename, transferMode, output, hostname);
            }
        }
        catch (UnknownHostException e)
        {
            System.err.println("Error: could not resolve hostname.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println(
                "Error: I/O exception occurred while receiving file.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        finally
        {
            // Close local socket and output file
            closed = close(tftp, output);
        }

        return closed;
    }
    private static boolean close(TFTPClient tftp, Closeable output) {
        boolean closed; 
        tftp.close();
        try
        {
            if (output != null) {
                output.close();
            }
            closed = true;
        }
        catch (IOException e)
        {
            closed = false;
            System.err.println("Error: error closing file.");
            System.err.println(e.getMessage());
        }
        return closed;
    }
    private static void open(TFTPClient tftp) {
        try
        {
            tftp.open();
        }
        catch (SocketException e)
        {
            System.err.println("Error: could not open local UDP socket.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    
}
