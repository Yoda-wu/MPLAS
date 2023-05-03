#include<cstring>
#include<cstdio>

using namespace std;

class CMyString
{
public:
	CMyString(const char* pData = nullptr);
	CMyString(const CMyString& str);
	~CMyString(void);

	CMyString& operator = (const CMyString& str);

	void Print();

private:
	char* m_pData;
};

CMyString::CMyString(const CMyString &str)
{
	int length = strlen(str.m_pData);
	m_pData = new char[length + 1];
	strcpy_s(m_pData,length+1, str.m_pData);
}