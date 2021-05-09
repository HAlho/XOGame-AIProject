package com.example.xo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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


        try {
            InputStream dataIs = getAssets().open("test.txt");
           // Byte by=(byte)dataIs.read();
            //Log.e("test", String.valueOf(by));
           // int size = is.available();

            // Create data input stream
            //DataInputStream dataIs = new DataInputStream(is);

            /*Log.e("test", String.valueOf(size));
            Log.e("test", String.valueOf(is.read()));
            Log.e("test", String.valueOf(is.read()));
            Log.e("test", String.valueOf(is.read()));*/

            // File myObj = new File(getAssets().open("filename.txt"));
           //string a=  getApplicationContext().getAssets().get
           // Scanner myReader = new Scanner(myObj);
            //System.out.println("An error occurred.");

           /* while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }*/
            Chronometer simpleChronometer=(Chronometer) findViewById(R.id.simpleChronometer);
            simpleChronometer.start(); // start a chronometer

            //int[] current = { 0,0,4,4,1,0,1,1,4 };
            int[] current = {0,0,0,0,0,0,0,0,0};//all empty
            int[] empty = {0,0,0,0,0,0,0,0,0};//all empty

            //myNode root=new myNode(current,3,1 );
            myNode root=new myNode(empty,3,1 );

            //read line to organize the work??
        //read file
            //read the root node
            root=readNode(dataIs);
            int count =root.fs;

            for(int i=0;i<count;i++){

                //root.children[i]=new myNode(empty,-1,-1);
                root.children[i]=readNode(dataIs);

                for(int j=0;(j<count-1) && (count-1!=0);j++){
                    root.children[i].children[j]=
                            readNode(dataIs);

                    for(int k=0;(k<count-2) && (count-2!=0);k++) {
                        root.children[i].children[j].children[k]=
                                readNode(dataIs);

                        for (int a = 0; (a < count - 3) && (count - 3 != 0); a++) {
                            root.children[i].children[j].children[k].children[a]=
                                    readNode(dataIs);

                            for (int b = 0; (b < count - 4) && (count - 4 != 0); b++) {
                                root.children[i].children[j].children[k].children[a].children[b]=
                                        readNode(dataIs);

                                for (int c = 0; (c < count - 5) && (count - 5 != 0); c++) {
                                    root.children[i].children[j].children[k].children[a].children[b].children[c]=
                                            readNode(dataIs);

                                    for (int l = 0; (l < count - 6) && (count - 6 != 0); l++) {
                                        root.children[i].children[j].children[k].children[a].children[b].children[c].children[l]=
                                                readNode(dataIs);

                                        for (int m = 0; (m < count - 7) && (count - 7 != 0); m++) {
                                            root.children[i].children[j].children[k].children[a].children[b].children[c].children[l].children[m]=
                                                    readNode(dataIs);

                                            for (int n = 0; (n < count - 8) && (count - 8 != 0); n++) {
                                                root.children[i].children[j].children[k].children[a].children[b].children[c].children[l].children[m].children[n]=
                                                        readNode(dataIs);

                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

            }




            simpleChronometer.stop();

            int[] mov = {0,0,4,4,4,4,1,1,0};//all empty
            int[] mov1 = {0,0,4,4,4,4,1,1,0};//all empty
            myNode move=new myNode(mov, 2,0);
            boolean same=false;
            for(int i=0; i<root.fs; i++) {
                Log.e("state", "try"+i);
                for(int j=0; j<9;j++)
                    if(root.children[i].data[j]!=move.data[j]) {
                        same=false;
                        break;
                    }else{
                        same=true;
                    }
                if (same) {
                    Log.e("state", "found is true");
                    move=root.children[i];
                }
            }
            printNode(move);


            //find best move
            int highest=-1;
            int index=-1;//this will cause error if loop is not excuted
            myNode newMove=new myNode(mov,2,0);
            for (int i=0; i<move.fs;i++){
                if(move.children[i].winX>highest){
                    highest =  move.children[i].winX;
                    index=i;
                }
            }
            newMove=move.children[index];
            printNode(newMove);


            //find move to make
            int newMoveIndex;
            for (newMoveIndex=0; newMoveIndex<9;newMoveIndex++) {
                if (move.data[newMoveIndex] != newMove.data[newMoveIndex])
                    break;
            }

            Log.e("test", "new move made in "+newMoveIndex);


            //choose best move
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "An error occurred.");

        }



    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

   /* public void readFile(myNode cmb){

    }*/
    public void printNode(myNode node){
            Log.e("print", ""+node.data[0]+node.data[1]+node.data[2]+node.data[3]+node.data[4]+
                    node.data[5]+node.data[6]+node.data[7]+node.data[8]);

    }
    public myNode readNode(InputStream dataIs){
        int[] empty = {1,0,0,0,0,0,0,0,0};
        myNode origin=new myNode(empty, -1, -1);
        try {

            for (int x = 0; x < 9; x++) {
                //byte t=dataIs.readByte();
                //char test= (char)t;
                origin.data[x] = Character.getNumericValue((char) dataIs.read());
                Log.e("test", "data"+x+ " is " + origin.data[x]);
            }
            //Log.e("test", "data5 is " + origin.data[4]);
            //Log.e("test", "data6 is " + origin.data[5]);


            origin.fs = Character.getNumericValue((char) dataIs.read());
            Log.e("test", "fs is " + origin.fs);

            origin.numChild = Character.getNumericValue((char) dataIs.read());
            Log.e("test", "numchild is " + origin.numChild);

            origin.turn = Character.getNumericValue((char) dataIs.read());
            Log.e("test", "turn is " + origin.turn);

            //read winO
            String cost;
            char[] temp=new char[6];
            char check;
            check = (char) dataIs.read();
            int c=0;
            while(check!='|') {
                temp[c]=check;
                check = (char) dataIs.read();
            }
            cost = String.valueOf(temp);
            cost=cost.trim();
            origin.winO=Integer.parseInt(cost);
            Log.e("test", "winO is " + origin.winO);

            String cost2;
            char[] temp2=new char[6];
            char check2;
            check2 = (char) dataIs.read();
            c=0;
            while(check2!='|') {
                temp2[c]=check2;
                check2 = (char) dataIs.read();
            }
            cost2 = String.valueOf(temp2);
            cost2=cost2.trim();
            origin.winX=Integer.parseInt(cost2);
            Log.e("test", "winX is " + origin.winX);


        } catch (IOException e) {
        e.printStackTrace();
        Log.e("test", "An error occurred.");

        }

        return origin;

    }
}

