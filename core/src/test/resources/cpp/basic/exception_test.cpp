#include <iostream>
#include <exception>
using namespace std;

struct MyException : public exception
{
  const char * what () const throw ()
  {
    return "C++ Exception";
  }
};

struct test
{
    int i;
    test(int iii)
    :i(iii){}

    test(int ii)
    try:i(ii)
    {
        i=1;
    }
    catch(std::exception& e)
    {
    }
};

int main()
{
  try
  {
    int i=1;
    if(i==1)
        throw MyException();
    i=2;
    switch(i)
    {
        case 1:i=1;
            break;
        case 2:i=2;
            break;
        default:i=0;
    }
  }
  catch(MyException& e)
  {
    std::cout << "MyException caught" << std::endl;
    std::cout << e.what() << std::endl;
  }
  catch(std::exception& e)
  {
    //其他的错误
  }
  return 0;
}