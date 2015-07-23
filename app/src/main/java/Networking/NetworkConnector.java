package Networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class NetworkConnector extends Thread {

    private ServerSocket mMySocket;

    private int mSendPort;

    private String mSendIp;

    public NetworkConnector(String sendIp, int myPort, int sendPort) throws IOException {
        mSendIp = sendIp;
        mMySocket = new ServerSocket(myPort);
        mSendPort = sendPort;
    }


    public void send(Object obj){
        final Object objCopy = obj;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Socket sendSocket = new Socket(mSendIp,mSendPort);
                    OutputStream os = sendSocket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(objCopy);
                    oos.close();
                    os.close();
                    sendSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void close() throws IOException {
        mMySocket.close();
        this.interrupt();
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                Socket s = mMySocket.accept();
                InputStream is = s.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);

                Object obj = ois.readObject();
                if(obj!=null) handle(obj);

                is.close();
                s.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void handle(Object obj);
}
