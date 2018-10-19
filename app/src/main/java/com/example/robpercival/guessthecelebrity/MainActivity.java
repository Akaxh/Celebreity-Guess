package com.example.robpercival.guessthecelebrity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity {

    public static final String NUMBER = "acdc";
    int score =0;
    ArrayList<String> celebURLs = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    int chosenCeleb = 0;
    int locationOfCorrectAnswer = 0;
    String[] answers = new String[4];
    int counter;
   LinearLayout sc;
   TextView sd;
    ImageView imageView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
GridLayout qq;
    public void celebChosen(View view) {

        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score ++;
            Toast.makeText(getApplicationContext(), "EXCELLENT! Keep Going.... ", Toast.LENGTH_LONG).show();

        }
        else {

            Toast.makeText(getApplicationContext(), "OOPS ! It was " + celebNames.get(chosenCeleb), Toast.LENGTH_LONG).show();

        }
        if(counter<20)
        createNewQuestion();
        else
            scoredisp();

     }

    public void scoredisp() {

        Intent intent=new Intent(this,Score.class);

        startActivity(intent);



    }
    public void replay(View view){


    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... urls) {

            try {

                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;


            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return null;
        }
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;

            }
            catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    counter=0;
        imageView = (ImageView) findViewById(R.id.imageView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        qq=findViewById(R.id.ques);


        DownloadTask task = new DownloadTask();
        String result = null;

        try {

            result = task.execute("http://www.posh24.se/kandisar").get();

            String[] splitResult = result.split("<div class=\"sidebarContainer\">");

            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {

                celebURLs.add(m.group(1));

            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while (m.find()) {

                celebNames.add(m.group(1));

            }


        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }

        createNewQuestion();

    }

    public void createNewQuestion() {

        Random random = new Random();
        chosenCeleb = random.nextInt(celebURLs.size());

        ImageDownloader imageTask = new ImageDownloader();

        Bitmap celebImage;

        try {


                celebImage = imageTask.execute(celebURLs.get(chosenCeleb)).get();

                imageView.setImageBitmap(celebImage);

                locationOfCorrectAnswer = random.nextInt(4);

                int incorrectAnswerLocation;

                for (int i = 0; i < 4; i++) {

                    if (i == locationOfCorrectAnswer) {

                        answers[i] = celebNames.get(chosenCeleb);

                    } else {

                        incorrectAnswerLocation = random.nextInt(celebURLs.size());

                        while (incorrectAnswerLocation == chosenCeleb) {

                            incorrectAnswerLocation = random.nextInt(celebURLs.size());

                        }

                        answers[i] = celebNames.get(incorrectAnswerLocation);


                    }


                }

                button0.setText(answers[0]);
                button1.setText(answers[1]);
                button2.setText(answers[2]);
                button3.setText(answers[3]);
                counter++;


        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
boolean twice;
    @Override
    public void onBackPressed() {

        if (twice == true) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        twice = true;

        Toast.makeText(MainActivity.this, "Thank You For Playing \n Please press BACK again to EXIT", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                twice = false;
                Log.d(TAG, "Twice:" + twice);

            }
        }, 3000);
    }

}
