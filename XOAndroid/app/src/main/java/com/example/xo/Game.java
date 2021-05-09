package com.example.xo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class Game extends AppCompatActivity {
    int[] empty = {1,1,1,1,1,1,1,1,1};
    myNode root =new myNode(empty, 9,1);
    myNode current =new myNode(empty, 9,1);

    boolean gameActive = true;

    // Player representation
    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // State meanings:
    //    0 - X
    //    1 - O
    //    2 - Null
    // put all win positions in a 2D array
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    // this function will be called every time a 
    // players tap in an empty box of the grid
    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // game reset function will be called 
        // if someone wins or the boxes are full
        if (!gameActive) {
            gameReset(view);
        }

        // if the tapped image is empty
        if (gameState[tappedImage] == 2) {
            //convert board
            int[] state = new int[9];
            for(int i=0; i<9;i++){
                if(gameState[i]==0)//if X
                    state[i]=4;
                else if(gameState[i]==1)//if 0
                    state[i]=0;
                else if(gameState[i]==2)//if X
                    state[i]=1;
            }

            //find Node
            boolean same=false;
            for(int i=0; i<root.fs; i++) {
                Log.e("state", "try"+i);
                for(int j=0; j<9;j++)
                    if(current.children[i].data[j]!=state[j]) {
                        same=false;
                        break;
                    }else{
                        same=true;
                    }
                if (same)//root.children[i].data==move.data
                    current=current.children[i];
            }

            //choose best move
            //find best move
            int highest=-1;
            int index=-1;//this will cause error if loop is not excuted
            myNode newMove=new myNode(empty,2,0);
            for (int i=0; i<current.fs;i++){
                if(current.children[i].winX>highest){
                    highest =  current.children[i].winX;
                    index=i;
                }
            }
            newMove=current.children[index];

            //find move to make
            int newMoveIndex;
            for (newMoveIndex=0; newMoveIndex<9;newMoveIndex++) {
                if (current.data[newMoveIndex] != newMove.data[newMoveIndex])
                    break;
            }

            // increase the counter 
            // after every tap
            counter++;

            // check if its the last box
            if (counter == 9) {
                // reset the game
                gameActive = false;
            }

            // mark this position
            gameState[tappedImage] = activePlayer;

            // this will give a motion 
            // effect to the image
            img.setTranslationY(-1000f);

            // change the active player 
            // from 0 to 1 or 1 to 0
            if (activePlayer == 0) {
                // set the image of x
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);

                // change the status
                status.setText("O's Turn - Tap to play");
            } else {
                // set the image of o
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

                // change the status
                status.setText("X's Turn - Tap to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        // Check if any player has won
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                // Somebody has won! - Find out who!
                String winnerStr;

                // game reset function be called
                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    winnerStr = "X has won";
                } else {
                    winnerStr = "O has won";
                }
                // Update the status bar for winner announcement
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);
            }
        }
        // set the status if the match draw
        if (counter == 9 && flag == 0) {
            TextView status = findViewById(R.id.status);
            status.setText("Match Draw");
        }
    }

    // reset the game
    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        // remove all the images from the boxes inside the grid
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("X's Turn - Tap to play");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);

        try {
            InputStream dataIs = getAssets().open("xoTreeSeperate.txt");


          /*  //int[] current = { 0,0,4,4,1,0,1,1,4 };
            int[] current = {0,0,0,0,0,0,0,0,0};//all empty
            int[] empty = {0,0,0,0,0,0,0,0,0};//all empty

            //myNode root=new myNode(current,3,1 );
            myNode root=new myNode(empty,9,1 );*/

            //read file
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

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "An error occurred.");

        }

        //keep root state
        current=root;
    }

    public myNode readNode(InputStream dataIs){
        int[] empty = {1,0,0,0,0,0,0,0,0};
        myNode origin=new myNode(empty, -1, -1);
        try {

            for (int x = 0; x < 9; x++) {

                origin.data[x] = Character.getNumericValue((char) dataIs.read());
                Log.e("test", "data"+x+ " is " + origin.data[x]);
            }

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