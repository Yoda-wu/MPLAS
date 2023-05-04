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

CMyString::CMyString(const  char *pData)
{
	if (pData == nullptr)
	{
		m_pData = new char[1];
		m_pData[0] = '\0';
	}
	else
	{
		int length = strlen(pData);
		m_pData = new char[length + 1];
		strcpy_s(m_pData,length+1, pData);
	}
}

CMyString::CMyString(const CMyString &str)
{
	int length = strlen(str.m_pData);
	m_pData = new char[length + 1];
	strcpy_s(m_pData,length+1, str.m_pData);
}

CMyString::~CMyString()
{
	delete[] m_pData;
}

CMyString& CMyString::operator = (const CMyString& str)
{
	if (&str != this)
	{
		CMyString strTemp(str);
		char* pTemp = strTemp.m_pData;
		strTemp.m_pData = m_pData;
		m_pData = pTemp;
	}

	return *this;
}