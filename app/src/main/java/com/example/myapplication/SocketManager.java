package com.example.myapplication;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketManager<OnMessageReceived> {

    public static final String TAG = SocketManager.class.getSimpleName();
    public static final String SERVER_IP = "192.168.25.11"; //server IP address
    public static final int SERVER_PORT = 6112;
    // message to send to the server
    private String mServerMessage;
    // sends message received notifications
    private OnMessageReceived mMessageListener = null;
    // while this is true, the server will continue running
    private boolean mRun = false;
    // used to send messages
    private PrintWriter mBufferOut;
    // used to read messages from the server
    private BufferedReader mBufferIn;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public SocketManager(OnMessageReceived listener) {
        mMessageListener = listener;
    }
}