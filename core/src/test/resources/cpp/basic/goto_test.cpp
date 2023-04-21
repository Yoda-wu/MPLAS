#include <iostream>
using namespace std;
int main(int argc, const char * argv[]) {

    cout<<"1111111"<<endl;
    cout<<"2222222"<<endl;
    //GOTO 跳转到前置label
    LABEL2:
        cout<<"5555555"<<endl;
        //goto跳转到后置label
    goto EXIT;
    cout<<"3333333"<<endl;
    EXIT:
    cout<<"4444444"<<endl;
    goto LABEL2;

    return 0;
}