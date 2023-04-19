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
			cout << "三" << endl;
			break;
		case 4:
			cout << "四" << endl;
			break;
		case 5:
			cout << "五" << endl;
			break;
		case 6:
			cout << "六" << endl;
			break;
		case 7:
			cout << "七" << endl;
			break;
		case 8:
			cout << "八" << endl;
			break;
		case 9:
			cout << "九" << endl;
			break;
		default :
			cout << "数字不在0-9之间！" << endl;
			break;
	}
	return 0 ;
}

