package com.mcnew.labthead;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int counter;
    TextView tvCounter;

    Thread thread;
    Handler handler;

    HandlerThread backgroundHandlerThread;
    Handler backgrounHandler;
    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter = 0;
        tvCounter = (TextView) findViewById(R.id.tvCounter);

        /**
         * Thread Method 1: Thread (Not Use this thread)
         **/
        /*-
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Run in background
                for (int i = 0; i < 100; i++) {
                    counter ++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // UI Thread a.k.a. Main Thread
                            tvCounter.setText(counter + "");
                        }
                    });
                }
            }
        });
        thread.start();
        -*/

        /**
         * Thread Method 2 : Thread with Handler (Use This Solution
         **/
        /*-
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // Run in Main Thread
                tvCounter.setText(msg.arg1 + "");
            }
        };

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                // Run in background
                for (int i = 0; i < 100; i++) {
                    counter ++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }

                    // update ui
                    Message msg = new Message();
                    msg.arg1 = counter; // arg1, arg2, arg3, obj
                    handler.sendMessage(msg); //send work to handle main thread
                }
            }
        });
        thread.start();
        -*/


        /**
         * Thread Method 3 : Handler Only (Work instand thread)
         **/
        /*-
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                counter++;
                tvCounter.setText(counter + "");
                if (counter < 100)
                    sendEmptyMessageDelayed(0, 1000);
            }
        };

        handler.sendEmptyMessageDelayed(0, 1000);
        -*/


        /**
         * Thread Method 4: HandlerThread (2 3 combined)
         **/
        backgroundHandlerThread = new HandlerThread("BackgrondHandlerThread");
        backgroundHandlerThread.start();

        backgrounHandler = new Handler(backgroundHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // Run with background

                Message msgMain = new Message();
                msgMain.arg1 = msg.arg1 + 1;
                mainHandler.sendMessage(msgMain);

            }
        };

        mainHandler = new Handler((Looper.getMainLooper())) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // Run with Main Thread
                tvCounter.setText(msg.arg1 + "");
                if (msg.arg1 < 100) {
                    Message msgBack = new Message();
                    msgBack.arg1 = msg.arg1;
                    backgrounHandler.sendMessageDelayed(msgBack, 1000);
                }
            }
        };

        // start here
        Message msgBack = new Message();
        msgBack.arg1 = 0;// Start count at 0
        backgrounHandler.sendMessageDelayed(msgBack, 1000);
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //thread.interrupt();
        backgroundHandlerThread.quit();
    }
}
