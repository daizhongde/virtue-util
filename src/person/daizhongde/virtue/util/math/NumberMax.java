package person.daizhongde.virtue.util.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Vector;
/**
2^1  16	 	2^2  32	 	2^3  16 	2^4 16		2^5  32	
2^6  64	 	2^7  128	2^8  256 	2^9 512   	2^10 1024  
2^11 2048	2^12 4096	2^13 8192	2^14 16384  2^15 32768

2^16 65536	 	2^17 131072	 	2^18 262144 	2^19 524288		2^20 1048576	
2^21 2097152	2^22 4194304	2^23 8388608 	2^24 16777216   2^25 33554432  
2^26 67108864	2^27 134217728	2^28 268435456	2^29 536870912  2^30 1073741824

2^31 2147483648		2^32 4294967296		2^33 8589934592		2^34 17179869184  	2^35 34359738368
2^36 68719476736	2^37 137438953472	2^38 274877906944	2^39 549755813888	2^40 1099511627776
2^41 2199023255552	2^42 4398046511104	2^43 8796093022208	2^44 17592186044416	2^45 35184372088832

2^46 70368744177664		2^47 140737488355328	2^48 281474976710656	2^49 562949953421312  	2^50 1125899906842624
2^51 2251799813685248	2^52 4503599627370496	2^53 9007199254740992	2^54 18014398509481984  2^55 36028797018963968
2^56 72057594037927936	2^57 144115188075855872	2^58 288230376151711744	2^59 576460752303423488	2^60 1152921504606846976

2^61 2305843009213693952	
2^62 4611686018427387904	
2^63 9223372036854775808	 9,223,372,036,854,775,808
2^64 18446744073709551616	18,446,744,073,709,551,616
2^65 36893488147419103232

数据类型          			 大小   	 范围                                            										 默认值 
byte(字节)		8	-128 - 127											0
shot(短整型)		16	-32768 - 32768										0
int(整型)			32	-2147483648-2147483648                              0
long(长整型)		64	-9233372036854477808-9233372036854477808            0        
float(浮点型)		32  -3.40292347E搜索+38-3.40292347E+38                   	0.0f
double(双精度)		64  -1.79769313486231570E+308-1.79769313486231570E+308  0.0d
char(字符型)		16	‘ \u0000 - u\ffff ’                             	‘\u0000 ’
boolean(布尔型)	1	true/false                                         	false



char -128 ~ +127 (1 Byte)
short -32767 ~ + 32768 (2 Bytes)
unsigned short 0 ~ 65535 (2 Bytes)
int -2147483648 ~ +2147483647 (4 Bytes)
unsigned int 0 ~ 4294967295 (4 Bytes)
long == int
long long -9223372036854775808 ~ +9223372036854775807 (8 Bytes)
double 1.7 * 10^308 (8 Bytes)

unsigned int 0～4294967295
long long的最大值：9223372036854775807
long long的最小值：-9223372036854775808
unsigned long long的最大值：18446744073709551615

__int64的最大值：9223372036854775807
__int64的最小值：-9223372036854775808
unsigned __int64的最大值：18446744073709551615

详细教程：

====================

符号属性 长度属性 基本型 所占位数 取值范围 输入符举例 输出符举例 
-- -- char 8 -2^7 ~ 2^7-1 %c %c 、 %d 、 %u
signed -- char 8 -2^7 ~ 2^7-1 %c %c 、 %d 、 %u
unsigned -- char 8 0 ~ 2^8-1 %c %c 、 %d 、 %u
[signed] short [int] 16 -2^15 ~ 2^15-1 %hd
unsigned short [int] 16 0 ~ 2^16-1 %hu 、 %ho 、 %hx
[signed] -- int 32 -2^31 ~ 2^31-1 %d
unsigned -- [int] 32 0 ~ 2^32-1 %u 、 %o 、 %x
[signed] long [int] 32 -2^31 ~ 2^31-1 %ld
unsigned long [int] 32 0 ~ 2^32-1 %lu 、 %lo 、 %lx
[signed] long long [int] 64 -2^63 ~ 2^63-1 %I64d
unsigned long long [int] 64 0 ~ 2^64-1 %I64u 、 %I64o 、 %I64x
-- -- float 32 +/- 3.40282e+038 %f 、 %e 、 %g
-- -- double 64 +/- 1.79769e+308 %lf 、 %le 、 %lg %f 、 %e 、 %g
-- long double 96 +/- 1.79769e+308 %Lf 、 %Le 、 %Lg

几点说明： 

1. 注意 ! 表中的每一行，代表一种基本类型。 “[]” 代表可省略。 
例如： char 、 signed char 、 unsigned char 是三种互不相同的类型； 
int 、 short 、 long 也是三种互不相同的类型。 

2. char/signed char/unsigned char 型数据长度为 1 字节；
char 为有符号型，但与 signed char 是不同的类型。 
注意 ! 并不是所有编译器都这样处理， char 型数据长度不一定为 1 字节， char 也不一定为有符号型。 

3. 将 char/signed char 转换为 int 时，会对最高符号位 1 进行扩展，从而造成运算问题。 
所以 , 如果要处理的数据中存在字节值大于 127 的情况，使用 unsigned char 较为妥当。 
程序中若涉及位运算，也应该使用 unsigned 型变量。 

4. char/signed char/unsigned char 输出时，使用格式符 %c （按字符方式）； 或使用 %d 、 %u 、 %x/%X 、 %o ，按整数方式输出； 输入时，应使用 %c ，若使用整数方式， Dev-C++ 会给出警告，不建议这样使用。 

5. int 的长度，是 16 位还是 32 位，与编译器字长有关。 
16 位编译器（如 TC 使用的编译器）下， int 为 16 位； 32 位编译器（如 VC 使用的编译器 cl.exe ）下， int 为 32位。 

6. 整型数据可以使用 %d （有符号 10 进制）、 %o （无符号 8 进制）或 %x/%X （无符号 16 进制）方式输入输出。 而格式符 %u ，表示 unsigned ，即无符号 10 进制方式。 

7. 整型前缀 h 表示 short ， l 表示 long 。 
输入输出 short/unsigned short 时，不建议直接使用 int 的格式符 %d/%u 等，要加前缀 h 。这个习惯性错误，来源于 TC 。 TC 下， int 的长度和默认符号属性，都与 short 一致，于是就把这两种类型当成是相同的，都用 int 方式进行输入输出。 

8. 关于 long long 类型的输入输出： 
"%lld" 和 "%llu" 是 linux 下 gcc/g++ 用于 long long int 类型 (64 bits) 输入输出的格式符。 
而 "%I64d" 和 "%I64u" 则是 Microsoft VC++ 库里用于输入输出 __int64 类型的格式说明。 

Dev-C++ 使用的编译器是 Mingw32 ， Mingw32 是 x86-win32 gcc 子项目之一，编译器核心还是 linux 下的 gcc 。
进行函数参数类型检查的是在编译阶段， gcc 编译器对格式字符串进行检查，显然它不认得 "%I64d" ， 
所以将给出警告 “unknown conversion type character `I' in format” 。对于 "%lld" 和 "%llu" ， gcc 理所当然地接受了。 

Mingw32 在编译期间使用 gcc 的规则检查语法，在连接和运行时使用的却是 Microsoft 库。 
这个库里的 printf 和 scanf 函数当然不认识 linux gcc 下 "%lld" 和 "%llu" ，但对 "%I64d" 和 "%I64u" ，它则是 乐意接受，并能正常工作的。 

9. 浮点型数据输入时可使用 %f 、 %e/%E 或 %g/%G ， scanf 会根据输入数据形式，自动处理。 
输出时可使用 %f （普通方式）、 %e/%E （指数方式）或 %g/%G （自动选择）。 

10. 浮点参数压栈的规则： float(4 字节 ) 类型扩展成 double(8 字节 ) 入栈。 
所以在输入时，需要区分 float(%f) 与 double(%lf) ，而在输出时，用 %f 即可。 
printf 函数将按照 double 型的规则对压入堆栈的 float( 已扩展成 double) 和 double 型数据进行输出。 
如果在输出时指定 %lf 格式符， gcc/mingw32 编译器将给出一个警告。 

11. Dev-C++(gcc/mingw32) 可以选择 float 的长度，是否与 double 一致。 

12. 前缀 L 表示 long （ double ）。 
虽然 long double 比 double 长 4 个字节，但是表示的数值范围却是一样的。 
long double 类型的长度、精度及表示范围与所使用的编译器、操作系统等有关。


@author dzd

*/
public class NumberMax {
	public static void main(String args[]){

		short  s =      32767;//2^16 -1  5位

		int    i = 2147483647;//2^31 -1  10位
		
		long  l = 2147483647;//2^63-1  19位
		
		long l3 = 2147483647;//2^63-1  19位
		
		float  f = 12345678903.40292347000000000000000000000000000000000000000000000000000f;
		double d = 12345678901234567890123456.797693134862315700000000000d;
		double d2 = 12345678901234567890123456.797693134862315700000000000;
		
		Integer ii=  new Integer("2147483647");//max : 2147483647
		Long l2=  new Long("9223372036854775807");//max : 9223372036854775807
		l = l2.longValue();
		Long.valueOf("l2");
		
		

		BigInteger bi = new BigInteger("123456789012");
		new Long("ff");
		Double dd = new Double("1234567890.12345");
		new Float("fff");
		new BigDecimal("fff");
		new java.lang.Integer("f");
		new Double(false?"":"0");
		
		System.out.println("Integer="+ii);
		System.out.println("Long2="+l);
		System.out.println("BigInteger="+bi);
		String fpath = "sfsdf.xls";
		fpath = fpath.replaceAll(".xls", ".zip");
		System.out.println("fpath;"+fpath);
	}
}
