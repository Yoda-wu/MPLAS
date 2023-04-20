#include<iostream>
using namespace std;
//使用switch语句打印一位数中文数字

int main()
{
	int num = 6 ;
	switch (num)
	{
		case 0:
			cout << "零" << endl;
			break;
		case 1:
			cout << "一" << endl;
			break;
		case 2:
			cout << "二" << endl;
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			cout << "七" << endl;
			break;
		case 8:
		case 9:
		default :
			cout << "数字不在0-9之间！" << endl;
			break;
	}
	return 0 ;
}

