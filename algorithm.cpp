// Online C++ compiler to run C++ program online
#include <iostream>



struct myNode {
    int data;
    int fs;//freespaces
    myNode* left;
    myNode* parent;
    myNode *children[9]; //an array of nodes of number based on freespaces
    
    myNode(int val,int val2)
    {
        data =val;
        fs=val2;
        parent = NULL;
        for(int i=0; i<fs;i++)
        children[i] =NULL;
        
    }
    
};

int totalCombinations(int freeSpaces){
    if(freeSpaces == 1) return 1;
    else return freeSpaces +(freeSpaces * totalCombinations(freeSpaces-1));
}

int main() {
    // Write C++ code here
    std::cout << "Hello world!"<<std::endl;
    int current[9]= {0,0,4,4,1,0,1,1,4}; //1 is empty, 0 is O , 4 is X
    /*std::cout << current[2]<<std::endl;
    int moves=6;
    freeSpaces = 9-moves;
    std::cout << totalCombinations(freeSpaces)<<std::endl;*/
        myNode* root = new myNode(1,3);
        root->children[1] = new myNode(2,2);
        

    
    return 0;
}