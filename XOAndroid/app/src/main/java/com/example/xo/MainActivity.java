package com.example.xo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        //Log.e("test", "I am first");
        //read file and store into structure
        int[] current = { 0,0,4,4,1,0,1,1,4 }; //all empty

        myNode root=new myNode(current,9,1 );

        try {
            File myObj = new File("xoTree.txt");
            Scanner myReader = new Scanner(myObj);
           /* while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }*/


        //read file
        int count =root.fs + 1;
        for(int i=0;i<count;i++){
            for(int j=0;(j<count-1) && (count-1!=0);j++){
                for(int x=0; x<9;x++){
                    root.data[x]=myReader.nextInt();
                    Log.e("test", "data is "+root.data[x]);
                }
                for(int k=0;(k<count-2) && (count-1!=0);k++) {
                    for (int a = 0; (a < count - 3) && (count - 1 != 0); a++) {
                        for (int b = 0; (b < count - 4) && (count - 1 != 0); b++) {
                            for (int c = 0; (c < count - 5 && (count - 1 != 0); c++) {
                                for (int l = 0; (l < count - 6) && (count - 1 != 0); l++) {
                                    for (int m = 0; (m < count - 7) && (count - 1 != 0); m++) {
                                        for (int n = 0; (n < count - 8) && (count - 1 != 0); n++) {

                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

   /* public void readFile(myNode cmb){

    }*/
    public void readNode(myNode cmb){


    }
}

