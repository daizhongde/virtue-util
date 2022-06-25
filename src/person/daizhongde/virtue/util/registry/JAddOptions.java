package person.daizhongde.virtue.util.registry;

/**
 * 
 * @Description: 添加选项
 * @author daizd
 * @date 2021年11月19日
 * 
 * 
 * 
 * 第一部分 REG
REG Operation [Parameter List]
  Operation  [ QUERY   | ADD    | DELETE  | COPY    |
               SAVE    | LOAD   | UNLOAD  | RESTORE |
               COMPARE | EXPORT | IMPORT  | FLAGS ]
返回代码: (除了 REG COMPARE)
  0 - 成功
  1 - 失败
要得到有关某个操作的帮助，请键入:
  REG Operation /?
例如:
  REG QUERY /?
  REG ADD /?
  REG DELETE /?
  REG COPY /?
  REG SAVE /?
  REG RESTORE /?
  REG LOAD /?
  REG UNLOAD /?
  REG COMPARE /?
  REG EXPORT /?
  REG IMPORT /?
  REG FLAGS /?
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
上述为reg所有的可用操作，接下来我们具体的看一下常用的一些操作的说明：

1.1 query 查询
REG QUERY KeyName [/v [ValueName] | /ve] [/s]
          [/f Data [/k] [/d] [/c] [/e]] [/t Type] [/z] [/se Separator]

  KeyName  [\\Machine\]FullKey
           Machine - 远程机器名称，省略当前机器的默认值。在远程机器上
                     只有 HKLM 和 HKU 可用。
           FullKey - 以 ROOTKEY\SubKey 名称形式
                ROOTKEY - [ HKLM | HKCU | HKCR | HKU | HKCC ]
                SubKey  - 在选择的 ROOTKEY 下的注册表项的全名

  /v       具体的注册表项值的查询。
           如果省略，会查询该项的所有值。

           只有与 /f 开关一起指定的情况下，此开关的参数才是可选的。它指定
           只在值名称中搜索。

  /ve      查询默认值或空值名称(默认)。

  /s       循环查询所有子项和值(如 dir /s)。

  /se      为 REG_MULTI_SZ 在数据字符串中指定分隔符(长度只为 1 个字符)。
           默认分隔符为 "\0"。

  /f       指定搜索的数据或模式。
           如果字符串包含空格，请使用双引号。默认为 "*"。

  /k       指定只在项名称中搜索。

  /d       指定只在数据中搜索。

  /c       指定搜索时区分大小写。
           默认搜索为不区分大小写。

  /e       指定只返回完全匹配。
           默认是返回所有匹配。

  /t       指定注册表值数据类型。
           有效的值是:
             REG_SZ, REG_MULTI_SZ, REG_EXPAND_SZ,
             REG_DWORD, REG_QWORD, REG_BINARY, REG_NONE
           默认为所有类型。

  /z       详细: 显示值名称类型的数字等值。

示例:

  REG QUERY HKLM\Software\Microsoft\ResKit /v Version
    显示注册表值版本的值

  REG QUERY \\ABC\HKLM\Software\Microsoft\ResKit\Nt\Setup /s
    显示远程机器 ABC 上的、在注册表项设置下的所有子项和值

  REG QUERY HKLM\Software\Microsoft\ResKit\Nt\Setup /se #
    用 "#" 作为分隔符，显示类型为 REG_MULTI_SZ 的所有值名称的所有
    子项和值。

  REG QUERY HKLM /f SYSTEM /t REG_SZ /c /e
    以区分大小写的形式显示项、值和数据和数据类型 REG_SZ
    的、在 HKLM 更目录下的、"SYSTEM" 出现的精确次数

  REG QUERY HKCU /f 0F /d /t REG_BINARY
    显示在 HKCU 根目录下、数据类型为 REG_BINARY 的数据的项、值和
    数据的 "0F" 出现的次数。

  REG QUERY HKLM\SOFTWARE /ve 
    显示在 HKLM\SOFTWARE 下的项、值和数据(默认)
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
1.2 add 添加
REG ADD KeyName [/v ValueName | /ve] [/t Type] [/s Separator] [/d Data] [/f]

  KeyName  [\\Machine\]FullKey
           Machine  远程机器名 - 忽略默认到当前机器。远程机器上
                    只有 HKLM 和 HKU。
           FullKey  ROOTKEY\SubKey
           ROOTKEY  [ HKLM | HKCU | HKCR | HKU | HKCC ]
           SubKey   所选 ROOTKEY 下注册表项的完整名。

  /v       所选项之下要添加的值名。

  /ve      为注册表项添加空白值名(默认)。

  /t       RegKey 数据类型
           [ REG_SZ    | REG_MULTI_SZ | REG_EXPAND_SZ |
             REG_DWORD | REG_QWORD    | REG_BINARY    | REG_NONE ]
           如果忽略，则采用 REG_SZ。

  /s       指定一个在 REG_MULTI_SZ 数据字符串中用作分隔符的字符
           如果忽略，则将 "\0" 用作分隔符。

  /d       要分配给添加的注册表 ValueName 的数据。

  /f       不用提示就强行覆盖现有注册表项。

例如:

  REG ADD \\ABC\HKLM\Software\MyCo
    添加远程机器 ABC 上的一个注册表项 HKLM\Software\MyCo

  REG ADD HKLM\Software\MyCo /v Data /t REG_BINARY /d fe340ead
    添加一个值(名称: Data，类型: REG_BINARY，数据: fe340ead)

  REG ADD HKLM\Software\MyCo /v MRU /t REG_MULTI_SZ /d fax\0mail
    添加一个值(名称: MRU，类型: REG_MUTLI_SZ，数据: fax\0mail\0\0)

  REG ADD HKLM\Software\MyCo /v Path /t REG_EXPAND_SZ /d ^%systemroot^%
    添加一个值(名称: Path，类型: REG_EXPAND_SZ，数据: %systemroot%)
    注意: 在扩充字符串中使用插入符号 ( ^ )
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
1.3 delete 删除
REG DELETE KeyName [/v ValueName | /ve | /va] [/f]

  KeyName    [\\Machine\]FullKey
    Machine  远程机器名 - 忽略当前机器的默认值。
             远程机器上只有 HKLM 和 HKU。
    FullKey  ROOTKEY\SubKey
    ROOTKEY  [ HKLM | HKCU | HKCR | HKU | HKCC ]
    SubKey   所选 ROOTKEY 下的注册表项的全名。
  ValueName  所选项下的要删除的值的名称。
             省略时，该项下的所有子项和值都会被删除。

  /ve        删除空白值名称的值(默认)。

  /va        删除该项下的所有值。

  /f         不用提示就强行删除。

例如:

  REG DELETE HKLM\Software\MyCo\MyApp\Timeout
    删除注册表项 Timeout 及其所有子项和值

  REG DELETE \\ZODIAC\HKLM\Software\MyCo /v MTU
    删除 ZODIAC 上 MyCo 下的注册表项 MTU
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
1.4 copy 复制
REG COPY KeyName1 KeyName2 [/s] [/f]

  KeyName    [\\Machine\]FullKey
    Machine  远程机器名 - 忽略当前机器的默认值。
             远程机器上只有 HKLM 和 HKU。
    FullKey  ROOTKEY\SubKey
    ROOTKEY  [ HKLM | HKCU | HKCR | HKU | HKCC ]
    SubKey   所选 ROOTKEY 下的注册表项的全名。
  /s         复制所有子项和值。

  /f         不用提示就强行复制。

例如:

  REG COPY HKLM\Software\MyCo\MyApp HKLM\Software\MyCo\SaveMyApp /s
    将注册表项 MyApp 下的所有子项和值复制到注册表项 SaveMyApp


  REG COPY \\ZODIAC\HKLM\Software\MyCo HKLM\Software\MyCo1
    将 ZODIAC 上注册表项 MyCo 下的所有值复制到当前机器上的
    注册表项 MyCo1
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
1.5 save 保存
REG SAVE KeyName FileName [/y]

  KeyName    ROOTKEY\SubKey
    ROOTKEY  [ HKLM | HKCU | HKCR | HKU | HKCC ]
    SubKey   所选 ROOTKEY 下的注册表项的全名。
  FileName   要保存的磁盘文件名。如果没有指定路径，文件会在调用进程的
             当前文件夹中得到创建。

  /y         不用提示就强行覆盖现有文件。

例如:

  REG SAVE HKLM\Software\MyCo\MyApp AppBkUp.hiv
    将配置单元 MyApp 保存到当前文件夹中的文件 AppBkUp.hiv
1
2
3
4
5
6
7
8
9
10
11
12
13
14
1.6 restore 恢复
REG RESTORE KeyName FileName

  KeyName    ROOTKEY\SubKey (只是本地机器)
    ROOTKEY  [ HKLM | HKCU | HKCR | HKU | HKCC ]
    SubKey   要将配置单元文件还原到的注册表项全名。
             覆盖现有项的值和子项。

  FileName   要还原的配置单元文件名。
             您必须使用 REG SAVE 来创建这个文件。

例如:

  REG RESTORE HKLM\Software\Microsoft\ResKit NTRKBkUp.hiv
    还原文件 NTRKBkUp.hiv，覆盖注册表项 ResKit
1
2
3
4
5
6
7
8
9
10
11
12
13
14
1.7 load 加载
REG LOAD KeyName FileName

  KeyName    ROOTKEY\SubKey (只是本地机器)
    ROOTKEY  [ HKLM | HKU ]
    SubKey   要将配置单元文件加载进的注册表项名称。创建一个新的注册表项。

  FileName   要加载的配置单元文件名。
             您必须使用 REG SAVE 来创建这个文件。

例如:

  REG LOAD HKLM\TempHive TempHive.hiv
    将文件 TempHive.hiv 加载到注册表项 HKLM\TempHive
1
2
3
4
5
6
7
8
9
10
11
12
13
1.8 unload 卸载
REG UNLOAD KeyName

  KeyName    ROOTKEY\SubKey (只是本地机器)
    ROOTKEY  [ HKLM | HKU ]
    SubKey   要卸载的配置单元的注册表项名称。

例如:

  REG UNLOAD HKLM\TempHive
    卸载 HKLM 中的配置单元 TempHive
1
2
3
4
5
6
7
8
9
10
1.9 compare 比较
REG COMPARE KeyName1 KeyName2 [/v ValueName | /ve] [Output] [/s]

  KeyName    [\\Machine\]FullKey
    Machine  远程机器名 - 省略当前机器的默认值。
             远程机器上只有 HKLM 和 HKU。
    FullKey  ROOTKEY\SubKey
             如果没有指定 FullKey2，FullKey2 则跟 FullKey1 相同。
    ROOTKEY  [ HKLM | HKCU | HKCR | HKU | HKCC ]
    SubKey   所选 ROOTKEY 下的注册表项的全名。

  ValueName  所选注册表项下的要比较的值的名称。
             省略时，该项下的所有值都会得到比较。

  /ve        比较空白值名称的值(默认)。

  /s         比较所有子项和值。

  Output     [/oa | /od | /os | /on]
             省略时，只显示不同的结果。
    /oa      显示所有不同和匹配结果。
    /od      只显示不同的结果。
    /os      只显示匹配结果。
    /on      不显示结果。

返回代码:

  0 - 成功，比较的结果相同
  1 - 失败
  2 - 成功，比较的结果不同

注意:
  每个输出行前面显示的符号定义为:
  = 表示 FullKey1 等于 FullKey2 数据
  < 指的是 FullKey1 数据，与 FullKey2 数据不同
  > 指的是 FullKey2 数据，与 Fullkey1 数据不同

例如:

  REG COMPARE HKLM\Software\MyCo\MyApp HKLM\Software\MyCo\SaveMyApp
    将注册表项 MyApp 下的所有值跟 SaveMyApp 比较

  REG COMPARE HKLM\Software\MyCo HKLM\Software\MyCo1 /v Version
    比较注册表项 MyCo 和 MyCo1 下的值 Version

  REG COMPARE \\ZODIAC\HKLM\Software\MyCo \\. /s
    将 ZODIAC 上 HKLM\Software\MyCo 下的所有子项和值和当前机器上
    的相同项比较
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
1.10 export 导出
REG IMPORT FileName

  FileName  要导入的磁盘文件名(只是本地机器)。

例如:

  REG IMPORT AppBkUp.reg
    从文件 AppBkUp.reg 导入注册表项
1
2
3
4
5
6
7
8
1.11 import 导入
REG IMPORT FileName

  FileName  要导入的磁盘文件名(只是本地机器)。

例如:

  REG IMPORT AppBkUp.reg
    从文件 AppBkUp.reg 导入注册表项 
 *
 */
public class JAddOptions implements JOptions {
   private boolean v = false;
   private String valueName;

   private boolean ve = false;

   private JValueType type;
   private boolean t = false;

   private Character separator;
   private boolean s = false;

   private boolean d = false;
   private String data;

   private boolean f = false;

   /**
    * 所选项之下要添加的值名
    * 
    * @param valueName
    */
   public JAddOptions useV(String valueName) {
      this.v = true;
      this.valueName = valueName;
      this.ve = false;
      return this;
   }

   /**
    * 为注册表项添加空白值名(默认)
    */
   public JAddOptions useVE() {
      this.ve = true;
      this.v = false;
      return this;
   }

   /**
    * RegKey 数据类型，如果忽略，则采用 REG_SZ
    * 
    * @param type
    * @return
    */
   public JAddOptions useT(JValueType type) {
      this.t = true;
      this.type = type;
      return this;
   }

   /**
    * 指定一个在 REG_MULTI_SZ 数据字符串中用作分隔符的字符 如果忽略，则将 "\0" 用作分隔符
    * 
    * @param separator
    * @return
    */
   public JAddOptions useS(Character separator) {
      this.separator = separator;
      this.s = true;
      return this;
   }

   /**
    * 要分配给添加的注册表 ValueName 的数据
    * 
    * @param data
    * @return
    */
   public JAddOptions useD(String data) {
      this.d = true;
      this.data = data;
      return this;
   }

   /**
    * 不用提示就强行覆盖现有注册表项
    * 
    * @return
    */
   public JAddOptions useF() {
      this.f = true;
      return this;
   }

   @Override
   public String toOptions() {
      StringBuilder builder = new StringBuilder();
      if (this.v && this.valueName != null && this.valueName.length() > 0) {
         builder.append("/v ").append(this.valueName);
      }
      if (this.ve) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/ve");
      }

      if (this.t && this.type != null) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         return "/t " + type.name();
      }

      if (this.s && this.separator != null) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         return "/s " + separator;
      }

      if (this.d && this.data != null && this.data.length() > 0) {
         builder.append("/d ").append(this.data);
      }
      if (this.f) {
         if (builder.length() > 0) {
            builder.append(" ");
         }
         builder.append("/f");
      }

      return builder.toString();
   }

}