#include <iostream>
using namespace std;

int main ()
{
   // 局部变量声明
   int a = 10;

   // while 循环执行
   while( a < 20 )
   {
       cout << "a 的值：" << a << endl;
       a++;
   }

   for( int a = 10; a < 20; a = a + 1 )
  {
      cout << "a 的值：" << a << endl;
  }

  do
  {
      cout << "a 的值：" << a << endl;
      a = a + 1;
  }while( a < 20 );

   return 0;
}