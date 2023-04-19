#include <iostream>
using namespace std;


void main() {
	for (int i = 1; i < 100; i++) {
//	int x =0;
//	x=x+1;
		if (i % 2 == 0) {
			continue;
		}
		else
		{
			std::cout << i << endl;
		}
	}
}