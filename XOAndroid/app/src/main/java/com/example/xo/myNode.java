package com.example.xo;

public class myNode{
    final int MAX = 9;
    int[] data=new int[MAX]; //moves array
    int fs;//freespaces
    int numChild;//number of children the node has

    //myNode* parent=new my;

    //defeated the point of a linked list
    myNode[] children=new myNode[MAX]; //array set to max possible connected children (non-dynamic list??!)
    int turn; //the turn that should be played next
    int winX;//cost as number that can be addded on the parent
    int winO;//cost as number that can be addded on the parent

    public myNode(int[] val, int val2, int val3)//(data, fs, turn(next turn))
    {
        //data
        for (int i = 0; i < MAX; i++) {
            data[i] = val[i];
        }
        //freeSpaces
        fs = val2;
        //numChild
        numChild = 0;
        //parent
        //parent = NULL;
        //null children
        for (int i = 0; i < fs;i++)
            children[i] = null;
        //turn is opposite of parent's turn/ get it from the number of current moves (x always start first)
        if (val3 == 4)
            turn = 0;
        else turn = 4; //make = to AIrole
        //intialize cost
        winX = 0;
        winO = 0;

    }
}


