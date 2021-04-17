// Online C++ compiler to run C++ program online
#include <iostream>

const int MAX = 9;//max grid which is 9
int turn = 4;

struct myNode {
    int data[MAX]; //moves array
    int fs;//freespaces
    int numChild;//number of children the node has
    myNode* parent;
    myNode* children[MAX]; //array set to max possible connected children (non-dynamic list??!)

    myNode(int val[MAX], int val2)
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
    std::cout << "Free spaces of " << origin->fs << " : ";
    for (int i = 0; i < MAX;i++) {
        std::cout << origin->data[i] << " ";
    }
    //if(origin->numChild>0)
    int j = 0;
    while (origin->children[j] != NULL) {
        std::cout << std::endl;
        myNode* temp = origin->children[j];
        for (int i = 0; i < MAX;i++) {
            std::cout << temp->data[i] << " ";
        }
        j++;
    }
}
void fillCombinations(myNode* origin) {
    //fill

    for (int i = 0; i < MAX;i++) {
        if (origin->data[i] == 1) {
            std::cout << "i is " << i << std::endl;
            //create child
            int child[MAX];
            for (int j = 0; j < MAX; j++) {
                if (j != i)
                    child[j] = origin->data[j];
                else
                    child[j] = turn;
            }
            //connect child
            std::cout << "connecting child" << std::endl;
            origin->children[origin->numChild] = new myNode(child, (origin->fs) - 1);
            origin->numChild = (origin->numChild) + 1;
        }
    }
    printCombination(origin);

}

int main() {
    // Write C++ code here
    std::cout << "Hello world!" << std::endl;

    int current[MAX] = { 0,0,4,4,1,0,1,1,4 }; //1 is empty, 0 is O , 4 is X
    int next[MAX] = { 0,0,4,4,4,0,1,1,4 };

    /*std::cout << current[2]<<std::endl;
    int moves=6;
    freeSpaces = 9-moves;
    std::cout << totalCombinations(freeSpaces)<<std::endl;*/
    myNode* root = new myNode(current, 3);
    root->children[1] = new myNode(next, 2);
    fillCombinations(root);

    return 0;
}