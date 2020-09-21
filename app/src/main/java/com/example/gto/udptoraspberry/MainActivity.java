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
    //public static final String sIP = "10.0.0.26";   // Nano Pi ip address
    //public static final String sIP = "10.0.0.46";   // Orange Pi ip address
    //public static final String sIP = "10.0.0.32";   // Nano Pi ip address
    //public static final String sIP = "223.62.219.58";   // S9+ Pi ip address
    public static final String sIP = "10.0.0.26";   // Nano Pi ip address
    public static final int sPORT = 8011;           // Port
    public static String ip_address;
    public static final String sIP_test = ip_address;

    private String msg;
    private String return_msg;

    // Send data class
    public SendData mSendData = null;
    // display TextView
    public TextView txtView = null;

    public EditText sendMsg;

    /*ip address info*/
    public EditText ip_Num;
    public TextView ip_text;

    public static byte gto[];

    public static String write_Msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button event registration
        Button btnHello = (Button) findViewById(R.id.Hello);
        Button btn_ip = (Button) findViewById(R.id.ip_btn);
        // textView event registration
        txtView = (TextView) findViewById(R.id.textView);
        // editView event registration
        sendMsg = (EditText) findViewById(R.id.send_msg);

        ip_Num = (EditText) findViewById(R.id.send_ip);
        ip_text = (TextView) findViewById(R.id.text_ip);

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

        btn_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ip_text.setText(ip_Num.getText());
               System.out.println("ip address : " + (ip_Num.getText()));

               ip_address = ip_Num.getText().toString();

               System.out.println("return address : " + ip_address);


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
                InetAddress serverAddr = InetAddress.getByName(ip_address);

                // create send data
                byte[] buf = write_Msg.getBytes();

                // change datagram packet
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);
                Log.d("UDP", "sendpacket.... " + "< " +new String(buf) + " >");

                //System.out.print("Send data -> ");
                byte_to_ascii(buf);
                //System.out.println();

                // send packet
                socket.send(packet);
                Log.d("UDP", "send....");
                Log.d("UDP", "Done.");

                // wait receive data
                socket.receive(packet);
                Log.d("UDP", "Receive : " + new String(packet.getData()));

                // if receive data -> String change
                String msg = new String(packet.getData());

                // view textView
                txtView.setText(msg);


            }catch (Exception e){
                Log.d("UDP", "Client: Error", e);
            }
        }
    }

    public static void byte_to_ascii(byte[] b){
        System.out.println("Ascii format : ");
        for (int i=0; i<b.length; i++){
            System.out.print((int)b[i] + " ");
        }
        System.out.println();
    }
}
