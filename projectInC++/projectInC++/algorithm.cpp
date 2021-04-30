// Online C++ compiler to run C++ program online
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <string>
#include <chrono>

using namespace std;
using namespace std::chrono;

const int MAX = 9;//max grid which is 9
const int aiRole = 4;// AI role is either 0 or 4 , O or X

struct myNode {
    int data[MAX]; //moves array
    int fs;//freespaces
    int numChild;//number of children the node has
    myNode* parent;
    //defeated the point of a linked list
    myNode* children[MAX]; //array set to max possible connected children (non-dynamic list??!)
    int turn; //the turn that should be played next
    int winX;//cost as number that can be addded on the parent
    int winO;//cost as number that can be addded on the parent


    myNode(int val[MAX], int val2, int val3)//(data, fs, turn(next turn))
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
        parent = NULL;
        //null children
        for (int i = 0; i < fs;i++)
            children[i] = NULL;
        //turn is opposite of parent's turn/ get it from the number of current moves (x always start first)
        if (val3 == 4)
            turn = 0;
        else turn = 4; //make = to AIrole
        //intialize cost 
        winX = 0;
        winO = 0;

    }

};

int totalCombinations(int freeSpaces) {
    if (freeSpaces == 1) return 1;
    else return freeSpaces + (freeSpaces * totalCombinations(freeSpaces - 1));
}

void fillOption(myNode* origin) {//lets not call it root but rather origin (current X O moves)
    
}

void printCombination(myNode* origin) {//print in readable way
    std::cout << "number of children: " << origin->numChild << std::endl;
    std::cout << "win costX: " << origin->winX << std::endl;
    std::cout << "win costO: " << origin->winO << std::endl;
    std::cout << "Free spaces of " << origin->fs << " : ";
    for (int i = 0; i < MAX;i++) {
        std::cout << origin->data[i] << " ";
    }
    //if(origin->numChild>0)
    int j = 0;
    while (j<origin->numChild) {
        std::cout << std::endl;
        myNode* temp = origin->children[j];
        for (int i = 0; i < MAX;i++) {
            std::cout << temp->data[i] << " ";
        }
        j++;
    }

    std::cout << std::endl<<std::endl;
}
void evaluateLeaf(myNode* cmb, int role) {//also add the AI role //evaluation done only for the last nodes
    bool win = false;
    if ((cmb->data[0] == role) && (cmb->data[1] == role) && (cmb->data[2] == role)) win = true;
    else if ((cmb->data[3] == role) && (cmb->data[4] == role) && (cmb->data[5] == role)) win = true;
    else if ((cmb->data[6] == role) && (cmb->data[7] == role) && (cmb->data[8] == role)) win = true;
    else if ((cmb->data[0] == role) && (cmb->data[4] == role) && (cmb->data[8] == role)) win = true;
    else if ((cmb->data[2] == role) && (cmb->data[4] == role) && (cmb->data[6] == role)) win = true;
    else if ((cmb->data[0] == role) && (cmb->data[3] == role) && (cmb->data[6] == role)) win = true;
    else if ((cmb->data[1] == role) && (cmb->data[4] == role) && (cmb->data[7] == role)) win = true;
    else if ((cmb->data[2] == role) && (cmb->data[5] == role) && (cmb->data[8] == role)) win = true;

    if (win) {
        if (role == 4)
            cmb->winX = 1;
        else
            cmb->winO = 1;
    }
}

void evaluate(myNode* cmb) {
    //search algorithm to traverse the tree if one branch has a false win skip the main node and go to next
}

void saveNode(myNode* node) {
    cout << "hello"<<endl;
    ofstream fout;
    string n = ",";
    string o = "|";
    fout.open("xoTree2.txt", ios::app);
    for (int i = 0;i < 9;i++)
        fout << node->data[i];
    fout <<n<< node->fs<<n<<node->numChild<<n<<node->turn<<n<<node->winO<<n<<node->winX;
    fout << o;
        fout.close();
        for (int i = 0;i < node->numChild;i++)
            saveNode(node->children[i]);
}

myNode* fillCombinations(myNode* origin) {
    //fill
    for (int i = 0; i < MAX;i++) {
        if (origin->data[i] == 1) {//if square is empty
            std::cout << "i is " << i << std::endl;
            //create child
            int child[MAX];
            for (int j = 0; j < MAX; j++) {
                if (j != i)
                    child[j] = origin->data[j];
                else
                    child[j] = origin->turn;
            }
            //connect child
            std::cout << "connecting child" << std::endl;
            origin->children[origin->numChild] = new myNode(child, (origin->fs) - 1, origin->turn);
            //evaluate child combination
            if (origin->children[origin->numChild]->numChild == 0) {
                evaluateLeaf(origin->children[origin->numChild], 4);
                evaluateLeaf(origin->children[origin->numChild], 0);
            }
            //repeat step for child
            fillCombinations(origin->children[origin->numChild]);
            //increment child numbers
            origin->numChild = (origin->numChild) + 1;


        }
    }
    //evaluate parent
    for (int i = 0;i < origin->numChild;i++) {
        //choose two chances to win over one chance to win(that way a cost is necessary)
        origin->winX = (origin->children[i]->winX) + origin->winX;
        origin->winO = (origin->children[i]->winO) + origin->winO;
    }

    printCombination(origin);
    //evaluate(origin);
    return origin;
}

int main() {
    auto start = high_resolution_clock::now();


    // Write C++ code here
    std::cout << "Hello world!" << std::endl;

    //int current[MAX] = { 0,0,4,4,1,0,1,1,4 }; //1 is empty, 0 is O , 4 is X
    //int current[MAX] = { 0,0,4,4,1,1,1,1,1 }; //1 is empty, 0 is O , 4 is X

      int current[MAX] = { 1,1,1,1,1,1,1,1,1 }; //all empty
      //int current[MAX] = { 1,1,1,1,1,1,1,0,4 };

    //int next[MAX] = { 0,0,4,4,4,0,1,1,4 };

    /*std::cout << current[2]<<std::endl;
    int moves=6;
    freeSpaces = 9-moves;
    std::cout << totalCombinations(freeSpaces)<<std::endl;*/
    myNode* root = new myNode(current, 9, 1); //his next turn is X
    //root->children[1] = new myNode(next, 2, root->turn); //his next turn is the opposite of the parent's
    root=fillCombinations(root);

    std::string result="xxxxxxxxx";
    std::cout << "the result is " << result << std::endl;

    std::cout << "testing " << std::endl;

    string c;
    for (int i = 0; i < 9; i++) {
        c = to_string(root->data[i]);
        result[i] = c[0];
       // std::cout << to_string(root->data[i]);
    }
    std::cout<<std::endl;
    std::cout << "the result is " << result << std::endl;

    auto stop = high_resolution_clock::now();

    auto duration = duration_cast<seconds>(stop - start);
    cout <<"duration" <<duration.count() << endl;

    start = high_resolution_clock::now();

    saveNode(root);

    stop = high_resolution_clock::now();

    duration = duration_cast<seconds>(stop - start);
    cout << "duration" << duration.count() << endl;
    

    
    //after tree is created, search current move then choose combimation based on cost
    //id there a way to find the current move quickly ... in the depth=fs read current state and choose the similar (breadth first) 

    return 0;
}