package com.mcnew.labthead;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    int counter;
    TextView tvCounter;

    Thread thread;
    Handler handler;

    HandlerThread backgroundHandlerThread;
    Handler backgrounHandler;
    Handler mainHandler;

    SampleAsyncTask sampleAsyncTask;


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
        /*-
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
        -*/

        /**
         * Thread Method 5: AsyncTask
         **/
        //sampleAsyncTask = new SampleAsyncTask();
        //sampleAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0, 100);
//        sampleAsyncTask.execute(0, 100); // Not use


        /**
         * Thread Method 6: AsyncTaskLoader
         **/
        getSupportLoaderManager().initLoader(1, null, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //thread.interrupt();
        // backgroundHandlerThread.quit();

        // sampleAsyncTask.cancel(true);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        if (id == 1) {
            return new AdderAsyncTaskLoader(MainActivity.this, 5, 11);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        Log.d("LLLL", "onLoadFinished");
        if (loader.getId() == 1) {
            Integer resule = (Integer) data;
            tvCounter.setText(resule + "");
        }

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    // do restorestate(do with licycle. in activity)
    static class AdderAsyncTaskLoader extends AsyncTaskLoader<Object> {

        int a, b;

        Integer result;

        public AdderAsyncTaskLoader(Context context, int a, int b) {
            super(context);
            this.a = a;
            this.b = b;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            Log.d("LLLL", "onStartLoading");

            if(result != null){
                deliverResult(result);
            }
            forceLoad(); // called loadInBackground are working.

        }

        @Override
        public Integer loadInBackground() {

            Log.d("LLLL", "loadInBackground");
            // Background Thread
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = a + b;
            return result;
        }

        @Override
        protected void onStopLoading() {
            super.onStopLoading();
            Log.d("LLLL", "onStopLoading");


        }
    }

    class SampleAsyncTask extends AsyncTask<Integer, Float, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... integers) {
            // Run in BackgroundThread
            int start = integers[0]; // 0
            int end = integers[1]; // 100

            for (int i = start; i < end; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return false;
                }
                publishProgress(i + 0.0f);
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            // Run on Main Thread
            super.onProgressUpdate(values);
            float progress = values[0];
            tvCounter.setText(progress + "%");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            // Run on Main Thread
            super.onPostExecute(aBoolean);
            // Work with aBoolean

        }
    }
}
