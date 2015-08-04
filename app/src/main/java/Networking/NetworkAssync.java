package Networking;

import android.os.AsyncTask;

import com.shometeam.ao.shome.CustomGuiElements.GraphicViews.GraphicView;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by ao on 6/21/15.
 */
public class NetworkAssync extends AsyncTask<Void, Void, ArrayList<Float>> {

    String mSendIp;
    int mPort;
    int mSendPort;
    GraphicView mGraphicView;
    Message mMessage;

    public NetworkAssync(String sendIp, int myPort, int sendPort, GraphicView renewGraphic,Message msg){
        mSendIp = sendIp;
        mPort = myPort;
        mSendPort = sendPort;
        mGraphicView = renewGraphic;
        mMessage = msg;
    }

    @Override
    protected ArrayList<Float> doInBackground(Void... noargs) {
        ArrayList<Float> newValues = new ArrayList<>(2);
        newValues.add(2f);
        newValues.add(20f);

        try {
            Socket sendSocket = new Socket(mSendIp,mSendPort);
            OutputStream os = sendSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(mMessage);
            oos.close();
            os.close();
            sendSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            ServerSocket mySocket = new ServerSocket(mPort);
            Socket s = mySocket.accept();
            InputStream is = s.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            newValues = (ArrayList<Float>) ois.readObject();

            ois.close();
            mySocket.close();
            is.close();
            s.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return newValues;
    }

    @Override
    protected void onPostExecute(ArrayList<Float> newValues) {
        mGraphicView = mGraphicView.withValues(newValues);
        mGraphicView.invalidate();
    }
}
