package com.example.gto.udptoraspberry;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//for UDP
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {

    //public static final String sIP = "10.0.0.26";   // RaspBerry Pi ip address
    //public static final String sIP = "10.0.0.21";   // Orange Pi ip address
    public static final String sIP = "10.0.0.28";   // Orange Pi ip address
    //public static final String sIP = "223.62.219.58";   // S9+ Pi ip address
    public static final int sPORT = 8011;           // Port

    private String msg;
    private String return_msg;

    // Send data class
    public SendData mSendData = null;

    // display TextView
    public TextView txtView = null;

    public EditText sendMsg;

    public static String write_Msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button event registration
        Button btnHello = (Button) findViewById(R.id.Hello);
        // textView event registration
        txtView = (TextView) findViewById(R.id.textView);
        // editView event registration
        sendMsg = (EditText) findViewById(R.id.send_msg);

        // button clicked
        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create SendData class
                mSendData = new SendData();
                // send data
                mSendData.start();

                System.out.println("clicked Button");

                write_Msg = sendMsg.getText().toString();
            }
        });

    }

    // Thread class for Data send
    class SendData extends Thread{
        public void run(){
            System.out.println("this is Thread class");
            try{
                // create UDP communication socket
                DatagramSocket socket = new DatagramSocket();
                // sever address variable
                InetAddress serverAddr = InetAddress.getByName(sIP);

                // create send data
                byte[] buf = write_Msg.getBytes();

                // change datagram packet
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);
                Log.d("UDP", "sendpacket.... " + "< " +new String(buf) + " >");

                // send packet
                socket.send(packet);
                Log.d("UDP", "send....");
                Log.d("UDP", "Done.");

                // wait receive data
                socket.receive(packet);
                Log.d("UDP", "Receive" + new String(packet.getData()));
                // if receive data -> String change
                String msg = new String(packet.getData());

                // view textView
                txtView.setText(msg);


            }catch (Exception e){
                Log.d("UDP", "Client: Error", e);
            }
        }
    }
}
