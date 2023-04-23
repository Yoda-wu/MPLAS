#include <iostream>
using namespace std;


void main() {
	for (int i = 1; i < 100; i++) {
//	int x =0;
//	x=x+1;
		if (i % 2 == 0) {
			break;
		}
		else
		{
			std::cout << i << endl;
		}
	}

    //goto语句

    	goto here ;
    	cout << "本应该输出这句。" << endl;
    here:
    	cout << "现在打印这句。" <<  endl;
    	return 0;



}