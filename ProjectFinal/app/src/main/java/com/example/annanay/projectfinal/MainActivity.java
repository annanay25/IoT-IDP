package com.example.annanay.projectfinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int index = 0;
    int strlength;
    TextView applianceStatus;
    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear, refreshBtn;

    String dstAddress;
    int dstPort;
    String response = "";
    EditText welcomeMsg;

    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonClear = (Button) findViewById(R.id.clear);
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        textResponse = (TextView) findViewById(R.id.response);
        applianceStatus = (TextView) findViewById(R.id.applianceStatus);

        welcomeMsg = (EditText) findViewById(R.id.welcomemsg);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        refreshBtn.setOnClickListener(buttonrefreshOnClickListener);

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }
        });
    }


    OnClickListener buttonrefreshOnClickListener = new OnClickListener() {
        String toMessage = "<status>";

        @Override
        public void onClick(View arg0) {
            MyClientTask1 myClientTask1 = new MyClientTask1(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), toMessage);
            myClientTask1.execute();
        }
    };

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            String tMsg = welcomeMsg.getText().toString();
            if (tMsg.equals("")) {
                tMsg = null;
                Toast.makeText(MainActivity.this, "No command sent", Toast.LENGTH_SHORT).show();
            }

            try {
                while (true) {
                    tMsg.charAt(index);
                    index++;
                }
            } catch (StringIndexOutOfBoundsException e) {
                strlength = index;
            }

            if (tMsg != null) {
                char[] oldChar = tMsg.toCharArray();
                char[] newChar = new char[oldChar.length + 2];
                newChar[0] = '<';
                for (int i = 1; i <= oldChar.length; i++) {
                    newChar[i] = oldChar[i - 1];
                }
                newChar[oldChar.length + 1] = '>';

                tMsg = String.valueOf(newChar);
            }


            MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), tMsg);
            myClientTask.execute();
        }
    };

    public class MyClientTask1 extends AsyncTask<Void, Void, Void> {


        MyClientTask1(String addr, int port, String s) {
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket sockets = null;

            BufferedReader br;
            BufferedWriter bw;
            try {
                sockets = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        sockets.getOutputStream());
                dataInputStream = new DataInputStream(sockets.getInputStream());
                br = new BufferedReader(new InputStreamReader(sockets.getInputStream()));
                bw = new BufferedWriter(new OutputStreamWriter(sockets.getOutputStream()));

                    dataOutputStream.writeUTF("<status>");
                    //bw.write(msgToServer);


                response = dataInputStream.readLine();
                //response = br.readLine();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (sockets != null) {
                    try {
                        sockets.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(response);
            try {
                sockets.close();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            applianceStatus.setText(response);
        }

    }

    class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        String msgToServer;

        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;

        MyClientTask(String addr, int port, String msgTo) {
            dstAddress = addr;
            dstPort = port;
            msgToServer = msgTo;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket sockets = null;

            BufferedReader br;
            BufferedWriter bw;
            try {
                sockets = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        sockets.getOutputStream());
                dataInputStream = new DataInputStream(sockets.getInputStream());
                br = new BufferedReader(new InputStreamReader(sockets.getInputStream()));
                bw = new BufferedWriter(new OutputStreamWriter(sockets.getOutputStream()));
                if (msgToServer != null) {
                    dataOutputStream.writeUTF(msgToServer);
                    //bw.write(msgToServer);
                }

                response = dataInputStream.readLine();
                //response = br.readLine();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (sockets != null) {
                    try {
                        sockets.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(response);
            try {
                sockets.close();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            textResponse.setText(response);
        }

    };

};


