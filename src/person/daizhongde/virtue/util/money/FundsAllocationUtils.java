package person.daizhongde.virtue.util.money;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类描述：
 * 用于采购明细生成入库单明细时的结算金额、税额分摊
 * 及其它类似业务
 * <p>
 * 注释备用符号：
 * 向上取整, 运算称为 Ceiling，用数学符号 ⌈⌉  （上有起止，开口向下）表示,。
 * 向下取整, 运算称为 Floor，用数学符号 ⌊⌋ （下有起止，开口向上）表示。
 *
 * @author dzd daizhongde@copote.com	2020/2/25 19:26
 */
public class FundsAllocationUtils {
	private static final Logger log = LoggerFactory
			.getLogger(FundsAllocationUtils.class);
	
    /**
     * <br>功能描述:
     * 金额单价分摊，保留四位小数，如果分摊价格差=0.0001
     *
     * <p>处理逻辑steps:
     * 1、算较小的单价price1 =  ⌊ total/n ⌋
     * 2、通过price1算price2 = price1+0.0001
     * 3、通过较小价格price1算较大价格的数量 nSl2   ( total -  price1*n)/0.0001
     * 4、通过nSl2算较小价格的数量 nSl1 的= n - nSl2
     * 已知：
     * 总金额 total， 数量 n
     * 计算单价 price = total/n
     * <p>
     * 1、除尽余数 m=0,不需要分摊；
     * 2、除不尽（n!=1 && m != 0），要求分摊，且分摊后价格差=0.0001
     * 除后余数 m = (total - price*n) = 10 - 0.0003*30000 = 1 元
     * 分摊公式：
     * 价格1：price+0.0001个数： m/0.0001 =     |(total - price*n)|/0.0001(个)
     * 价格2：price 个数：   n - m/0.0001 = n - |(total - price*n)|/0.0001(个)
     * 或用   n
     * <p>
     * 示例1：
     * total=10(元),n=30000(个)
     * 套公式：
     * price = total/n = 0.0003(四舍五入/向下取整)
     * 价格1：price+0.0001=(0.0004)数量：|(10 - 0.0003*30000)|/0.0001 = 10000(个)
     * 价格2：price        (0.0003)数量: 30000 - 10000                = 20000(个)
     * <p>
     * 示例2：
     * total=11(元),n=30000(个)
     * 套公式：
     * price = total/n = 0.0003(向下取整)
     * 价格1：price+0.0001=(0.0004)数量：|(11 - 0.0003*30000)|/0.0001 = 20000(个)
     * 价格2：price        (0.0003)数量: 30000 - 10000                = 10000(个)
     * <p>
     * price = total/n = 0.0004(四舍五入/向上取整)
     * 价格1：price-0.0001 (0.0003)数量：|(10 - 0.0004*30000)|/0.0001 = 20000(个)
     * 价格2：price       =(0.0004)数量: 30000 - 10000                = 10000(个)
     * <p>
     * 总则：
     * 用price算个数，如果price入了，算出的就是低价格的个数；
     * 否则（舍了），算出就是高价格的个数。
     * 关键在于上面的总则，与采用什么取整方式无关（四舍五入、向上取整，向下取整）
     * <p>
     * 即：入参n-->30000, total-->10
     * 返回：[
     * {price:0.0004, nSl:10000},
     * {price:0.0003, nSl:20000},
     * ]
     * <p>
     * 示例2：
     * 入参n-->2, total-->10
     * 返回：[
     * {price:5, nSl:10}
     * ]
     *
     * @param n     数量  大于等于1
     * @param total 总金额 最多4位小数(单位元), 不支持负数
     *              total>=0.0001
     * @return 较小价格  price1  数量 nSl1  程序中 price1=price
     * 较大价格  price2  数量 nSl2
     * 纯分摊金额和数量，程序不用比较就知道哪个价格大
     * 这里返回值按数量从小到大排序，方便进一步与税额一起分摊
     * @throws
     * @author 2020/2/25 21:07
     * @deprecated
     */
    public FundsAllocationRow[] allocate(Long n, Double total) {
        /* n>=1 ;  total 最多4位小数 */
        if (n < 1) {
            throw new RuntimeException("参数n不合法！");
        }
        if (getNumberDecimalDigits(total) > 4
                || total < 0.0001D) {
            throw new RuntimeException("参数 total 不合法！");
        }

        if (Double.compare(total / n, 0.0001d) < 0) {
            throw new RuntimeException("参数不合法！单价不能低于0.0001");
        }

        /* step1:
         *       算较小的单价price1 =  ⌊ total/n ⌋
         *    这里算向下取整的单价是使用字符串构造的，所以没有BigDecimal那种问题，也就不用特殊分两步处理
         *
         * */
        double price = total / n;

        //  #.0000表示保留后四位，它的处理方式是直接截掉不要的尾数，不四舍五入
        DecimalFormat df = new DecimalFormat("#.0000");
        price = Double.parseDouble(df.format(price));
        if (total < price * n) {
            price = price - 0.0001d;
            price = (double) Math.round(price * 10000) / 10000;
        }

        boolean flag = total.compareTo(price * n) == 0;
        if (flag) {
            // 返回值
            FundsAllocationRow[] ret = new FundsAllocationRow[1];
            FundsAllocationRow r = new FundsAllocationRow(n, new BigDecimal(price).setScale(4, BigDecimal.ROUND_DOWN));
            ret[0] = r;
            return ret;
        } else {

            /* step2:
                    通过price1算price2 = price1+0.0001
                  price2为较大的那个金额
                  price1 = price 是较小的那个金额
             */
            double price2 = Double.parseDouble(df.format(price + 0.0001d));

            /* step3:
                    通过较小价格price1算较大价格的数量 nSl2   ( total -  price1*n)/0.0001
                    */
            Long nSl2 = new Double((total - price * n) / 0.0001).longValue();
             /* step4:
                    通过nSl2算较小价格的数量 nSl1 的= n - nSl2
             */
            long nSl1 = n - nSl2;
            // 返回值
            FundsAllocationRow[] ret = new FundsAllocationRow[2];
            FundsAllocationRow r1 = new FundsAllocationRow(nSl1,
                    new BigDecimal(price).setScale(4, BigDecimal.ROUND_HALF_UP));
            FundsAllocationRow r2 = new FundsAllocationRow(nSl2,
                    new BigDecimal(price2).setScale(4, BigDecimal.ROUND_HALF_UP));

            /* 返回值按数量从小到大排序，方便进一步与税额一起分摊  */
            if (nSl1 <= nSl2) {
                ret[0] = r1;
                ret[1] = r2;
            } else {
                ret[0] = r2;
                ret[1] = r1;
            }

            // 返回前校验
            Double temp = price * nSl1 + price2 * nSl2;
            if (temp.compareTo(total) != 0) {
                throw new RuntimeException("ERROR!分摊逻辑存在bug!!!");
            }
            return ret;
        }
    }

    /**
     * <br>功能描述:  资金分摊
     * <br>处理逻辑:
     * 1、算较小的单价price1 =
     * 2、通过price1算price2 = price1+0.0001
     * 3、通过price1算较大的数量 nSl1
     * 4、通过nSl1算较小数量 nSl2 的= n - nSl1
     *
     * @param n     数量 大于等于1
     * @param total 总金额 total>=1 必须是整数
     * @return com.copote.nstamp.common.util.FundsAllocationRow[]
     * @throws
     * @author daizd 2020/2/26 9:56
     * @deprecated
     */
    public FundsAllocationRow[] allocate4Stamp(Long n, Double total) {
        //  加法：add  减法：subtract   乘法：multiply  除法：divide
        FundsAllocationRow[] ret = allocate(n, total / 10000);
        for (FundsAllocationRow r : ret) {
            r.setPrice(r.getPrice().multiply(new BigDecimal(10000D)).setScale(0, BigDecimal.ROUND_HALF_UP));
        }
        return ret;
    }

    /**
     * <br>功能描述:
     * 金额单价分摊，保留四位小数，如果分摊价格差=0.0001
     * <br>处理逻辑steps:
     * 1、算较小的单价price1 =  ⌊ total/n ⌋
     * 2、通过price1算price2 = price1+0.0001
     * 3、通过较小价格price1算较大较小价格的数量 nSl2   ( total -  price1*n)/0.0001
     * 4、通过nSl2算较小价格的数量 nSl1 的= n - nSl2
     *
     * @param n     数量  大于等于1
     * @param total 总金额 total>=0.0001 最多4位小数(单位元), 不支持负数
     *              (单位毫) 元、角、分、厘、毫
     *              BigDecimal统一传入时就.setScale(0, BigDecimal.ROUND_HALF_UP)
     * @return 较小价格  price1  数量 nSl1  程序中 price1=price
     * 较大价格  price2  数量 nSl2
     * 纯分摊金额和数量，程序不用比较就知道哪个价格大
     * 这里返回值按数量从小到大排序，方便进一步与税额一起分摊
     * @throws
     * @author daizd 2020/2/26 11:29
     * @see com.copote.nstamp.common.util.FundsAllocationUtils#allocate(Long, Double);
     * 参考的方法注释中有详细的逻辑说明，此方法与本方法逻辑一致
     */
    public List<FundsAllocationRow> allocate(Long n, BigDecimal total) {
        /* n>=1 ;  total 最多4位小数 */
        if (n < 1) {
            throw new RuntimeException("参数n不合法！");
        }

        if (getNumberOfDecimalPlaces(total) > 4) {
            throw new RuntimeException("参数 total 不合法！");
        }
        /*
         *
         *  公理：需要进行二次计算的 BigDecimal，如果要进行精确位调整，只能通过BigDecimal.ROUND_HALF_UP。
         *
         * */
        total = total.setScale(4, BigDecimal.ROUND_HALF_UP);
        log.debug("total.multiply(new BigDecimal(n)):{}", total.multiply(new BigDecimal(n)));
        /* 注意：这里只能用ROUND_DOWN  */
//        if (total.divide(new BigDecimal(n), 4, BigDecimal.ROUND_DOWN)
//                .setScale(4)
//                .compareTo(new BigDecimal("0.0001")) < 0) {
//        long ttt = total.divide(new BigDecimal(n), 4, BigDecimal.ROUND_DOWN)
////                .setScale(4).multiply(new BigDecimal(10000))
////                .longValue();
     /*   if (total.divide(new BigDecimal(n), 4, BigDecimal.ROUND_DOWN)
                .setScale(4).multiply(new BigDecimal(10000))
                .longValue()
                < 1) {
            throw new RuntimeException("参数不合法！单价不能低于0.0001");
        }*/
        List<FundsAllocationRow> ret = new ArrayList<FundsAllocationRow>();

/* 加法：add  减法：subtract   乘法：multiply  除法：divide
         *
1、ROUND_UP
舍入远离零的舍入模式。

2、ROUND_DOWN
接近零的舍入模式。

5、ROUND_HALF_UP
向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。

6、ROUND_HALF_DOWN
向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式。
          * */
        /* step1:
         *       算较小的单价price1 =  ⌊ total/n ⌋
         *
         * 虽然是获取 ⌊ total/n ⌋ 的值，但这里一定不能使用 BigDecimal.ROUND_DOWN
         *  如果使用 BigDecimal.ROUND_DOWN 直接向下取整，后面的计算用到total的地方都会有问题
         * 这里向下取整的步骤如下：
         *     1、将计算结果四舍五入
         *     2、如果是远离0就减 0.0001，否则不变
         * */
        BigDecimal price = total.divide(new BigDecimal(n), 4, BigDecimal.ROUND_HALF_UP);

        // 比较总金额与通过单价计算出的金额
        if (total.compareTo(price.multiply(new BigDecimal(n))) < 0) {
            // 总金额比计算金额小就将单价-0.0001，保证price为相对较小的那个价钱(price1)
            price = price.subtract(new BigDecimal("0.0001")).setScale(4, BigDecimal.ROUND_HALF_UP);
            // 保留四位小数，因上面price已处理，所以这里省略
///            price =  (double) Math.round(price * 10000) / 10000;
        }
        // 加法：add  减法：subtract   乘法：multiply  除法：divide
        boolean flag = total.compareTo(price.multiply(new BigDecimal(n)).setScale(4, BigDecimal.ROUND_HALF_UP)) == 0;
        if (flag) {
            // 能整除
            FundsAllocationRow r = new FundsAllocationRow(n, price);
            ret.add(r);
            return ret;
        } else {
            // 不能整除
            /// Double price2 = Double.parseDouble(df.format(price+0.0001D));
            /* step2:
                    通过price1算price2 = price1+0.0001
                  price2为较大的那个金额
                  price1 = price 是较小的那个金额
               加法：add  减法：subtract   乘法：multiply  除法：divide */
            BigDecimal price2 = price.add(new BigDecimal("0.0001")).setScale(4, BigDecimal.ROUND_HALF_UP);

            /* step3:
                    通过较小价格price1算较大的数量 nSl2   ( total -  price1*n)/0.0001
                    */
            /// Long nSl2 =  (int)(((double)total - 较小的price*n)/0.0001);
            Long nSl2 = total.subtract(price.multiply(new BigDecimal(n)))
                    .divide(new BigDecimal("0.0001"), 0, BigDecimal.ROUND_HALF_UP).longValue();

            /* step4:
                    通过nSl2算较小价格的数量 nSl1 的= n - nSl2
             */
            long nSl1 = n - nSl2;

            ///FundsAllocationRow[] ret = new FundsAllocationRow[2];
            FundsAllocationRow r1 = new FundsAllocationRow(nSl1, price);
            FundsAllocationRow r2 = new FundsAllocationRow(nSl2, price2);
            /* 返回值按数量从小到大排序，方便进一步与税额一起分摊  */
            if (nSl1 <= nSl2) {
                ret.add(r1);
                ret.add(r2);
            } else {
                ret.add(r2);
                ret.add(r1);
            }
            // 返回前校验   加法：add  减法：subtract   乘法：multiply  除法：divide
            ///Double temp = price*nSl1 + price2*nSl2;
            BigDecimal temp = price2.multiply(new BigDecimal(nSl2))
                    .add(price.multiply(new BigDecimal(nSl1))).setScale(4, BigDecimal.ROUND_HALF_UP);
            if (temp.compareTo(total) != 0) {
                throw new RuntimeException("ERROR!单额分摊逻辑存在bug!!!");
            }
            return ret;
        }
    }

    /**
     * 金额、税额、数量共同分摊不产生金额丢失
     * <p>
     * Step1:
     * 金额、税额分别分摊数量
     * Step2:
     * 通过step1的结果进行二次分摊
     *
     * @param n     数量 n>=1
     * @param total 总金额 total>=0.0001
     *              最多4位小数(单位元)
     *              BigDecimal统一传入时就.setScale(4, BigDecimal.ROUND_HALF_UP)
     *              如果小数位数没有超过4位可不用处理
     * @param nTax  税额 nTax>=0.0001
     *              最多4位小数(单位元)
     *              BigDecimal统一传入时就.setScale(4, BigDecimal.ROUND_HALF_UP)
     *              如果小数位数没有超过4位可不用处理
     * @return 金额、税额、数量三者的分摊结果
     * @see com.copote.nstamp.common.util.FundsAllocationUtils#allocate(Long, BigDecimal);
     * <p/>
     */
    public List<FundsAllocationRow2> allocate(Long n, BigDecimal total, BigDecimal nTax) {
        return allocateTheSecondTime(
                allocate4Stamp(n, total),
                allocate4Stamp(n, nTax)
        );
    }

    /**
     * <br>功能描述:  资金分摊
     * <br>处理逻辑:
     *
     * @param n     数量 大于等于1
     * @param total 总金额 total>=1 必须是整数
     * @return 较小价格  price1  数量 nSl1  程序中 price1=price
     * 较大价格  price2  数量 nSl2
     * @throws
     * @author daizd 2020/2/26 9:56
     * @see com.copote.nstamp.common.util.FundsAllocationUtils#allocate(Long, BigDecimal);
     */
    public List<FundsAllocationRow> allocate4Stamp(Long n, BigDecimal total) {
        //  加法：add  减法：subtract   乘法：multiply  除法：divide
        List<FundsAllocationRow> ret = allocate(n, total.divide(new BigDecimal(10000), 4, BigDecimal.ROUND_HALF_UP));
        for (FundsAllocationRow r : ret) {
            r.setPrice(r.getPrice().multiply(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_HALF_UP));
        }
        return ret;
    }

    /**
     * 金额、税额、数量共同分摊不产生金额丢失
     * <p>
     * Step1:
     * 金额、税额分别分摊数量
     * Step2:
     * 通过step1的结果进行二次分摊
     * <p>
     * 注意：
     * 4Stamp的底层方法一般不能调用4Stamp后缀的方法只能调用allocate方法（带小数点入参的方法除外，
     * 带小数点入参的方法只为调整精确位去剩除10进制），
     * 因为4Stamp后缀的方法乘过1万再运行的，调两次4Stamp后缀的方法就会剩两次一万
     *
     * @param n     数量 n>=1
     * @param total 总金额 total>=1 必须是整数
     * @param nTax  税额 nTax>=1 必须是整数
     *              nTax<=total
     * @return 金额、税额、数量三者的分摊结果
     * @see com.copote.nstamp.common.util.FundsAllocationUtils#allocate(Long, BigDecimal);
     * <p/>
     */
    public List<FundsAllocationRow2> allocate4Stamp(
            Long n,
            BigDecimal total,
            BigDecimal nTax
    ) {
        /*  nTax<=total */
        if (nTax.compareTo(total) > 0) {
            throw new RuntimeException("税不可以大于金额！");
        }
        /*
         * step1:金额与税额分别与数量做分摊
         * step2:使用两者的分摊结果做分配
         *   注意：
         *     分摊前乘一万，分摊后除一万
         * step3:校验明细值
         * step4:校验分摊结果，金额合计、税额合计、数量合计
         * */
        List<FundsAllocationRow2> ret = allocateTheSecondTime(
                allocate(n, total.divide(new BigDecimal(10000), 4,
                        BigDecimal.ROUND_HALF_UP)
                ),
                allocate(n, nTax.divide(new BigDecimal(10000), 4,
                        BigDecimal.ROUND_HALF_UP))
        );
        /* 合计金额  用于校验 */
        BigDecimal sumAmount = new BigDecimal(0);
        /* 合计税额  用于校验 */
        BigDecimal sumTax = new BigDecimal(0);
        /* 合计数量  用于校验 */
        Long sumSl = 0L;
        /*
         *   注意：
         *     分摊后除一万，同时校验明细值
         */
        for (FundsAllocationRow2 r : ret) {
            r.setPrice(r.getPrice().multiply(new BigDecimal(10000))
                    .setScale(0, BigDecimal.ROUND_HALF_UP)
            );
            r.setTax(r.getTax().multiply(new BigDecimal(10000))
                    .setScale(0, BigDecimal.ROUND_HALF_UP)
            );

            sumAmount = sumAmount.add(
                    r.getPrice().multiply(new BigDecimal(r.getnSl()))
                            .setScale(0, BigDecimal.ROUND_HALF_UP)
            );

            sumTax = sumTax.add(
                    r.getTax().multiply(new BigDecimal(r.getnSl()))
                            .setScale(0, BigDecimal.ROUND_HALF_UP)
            );

            sumSl = sumSl + r.getnSl();

            /* 校验结果明细中的三个值是否合法
             prie>0
             tax>0
             nSl>0
             */
            if (r.getPrice().compareTo(new BigDecimal(0)) < 0
                    || r.getTax().compareTo(new BigDecimal(0)) < 0
                    || r.getnSl() < 0
                    ) {
                log.info(">>>>>ret={}",
                        ret
                );
                throw new RuntimeException("ERROR!双额分摊逻辑存在bug<明细数值不合法>!!!");
            }
        }

        /* 校验分摊结果，金额合计：
         *  total1 + total2 = total
         *  */
        if (sumAmount.compareTo(total) != 0) {
            log.info(">>>>>ret={}",
                    ret
            );
            throw new RuntimeException("ERROR!双额分摊逻辑存在bug<金额合计校验不通过>!!!");
        }
        /* 校验分摊结果（税额合计）
         *  nTax1 + nTax2 = nTax
         * */
        if (sumTax.compareTo(nTax) != 0) {
            log.info(">>>>>ret={}",
                    ret
            );
            throw new RuntimeException("ERROR!双额分摊逻辑存在bug!!!<税额合计校验不通过>");
        }
        /* 校验分摊结果（数量合计）
         *  n1 + n2 = n
         * */
        if (!sumSl.equals(n)) {
            log.info(">>>>>ret={}",
                    ret
            );
            throw new RuntimeException("ERROR!双额分摊逻辑存在bug!!!<数量合计校验不通过>");
        }

        return ret;
    }

    /**
     * @param n     数量 n>=1
     * @param total 总金额 total>=1 必须是整数  传入前 setScale(0)
     * @param nTax  税额 nTax>=1 必须是整数  传入前 setScale(0)
     *              nTax<=total
     * @param point 小数点位数（保留2位传2）
     *              枚举支持列表： 0, 1, 2, 3, 4
     * @return 金额、税额、数量三者的分摊结果
     */
    public List<FundsAllocationRow2> allocate4StampWithPoint(
            Long n,
            BigDecimal total,
            BigDecimal nTax,
            int point
    ) {

        int backZeroCount1 = zeroCount(total);
        int backZeroCount2 = zeroCount(nTax);
        switch (point) {
            case 0:
                /* 是否有小数，不能有小数
                   必须能被1万整除
                 */
                if (getNumberOfDecimalPlaces(total) > 0
                        || getNumberOfDecimalPlaces(nTax) > 0
                        || backZeroCount1 < 4
                        || backZeroCount2 < 4
                        ) {
                    throw new RuntimeException("参数 total 不合法！");
                }

                return moveRetPoint(
                        allocate4Stamp(
                                n,
                                total.divide(new BigDecimal(10000), 0,
                                        BigDecimal.ROUND_HALF_UP),
                                nTax.divide(new BigDecimal(10000), 0,
                                        BigDecimal.ROUND_HALF_UP)
                        ),
                        Direction.RIGHT,
                        4,
                        0);
            case 1:
                /* 是否有小数，不能有小数
                   必须能被1千整除
                 */
                if (getNumberOfDecimalPlaces(total) > 0
                        || getNumberOfDecimalPlaces(nTax) > 0
                        || backZeroCount1 < 3
                        || backZeroCount2 < 3
                        ) {
                    throw new RuntimeException("参数 total 不合法！");
                }
                return moveRetPoint(
                        allocate4Stamp(
                                n,
                                total.divide(new BigDecimal(1000), 0,
                                        BigDecimal.ROUND_HALF_UP),
                                nTax.divide(new BigDecimal(1000), 0,
                                        BigDecimal.ROUND_HALF_UP)
                        ),
                        Direction.RIGHT,
                        3,
                        0);
            case 2:
                /* 是否有小数，不能有小数
                   必须能被1百整除
                 */
                if (getNumberOfDecimalPlaces(total) > 0
                        || getNumberOfDecimalPlaces(nTax) > 0
                        || backZeroCount1 < 2
                        || backZeroCount2 < 2
                        ) {
                    throw new RuntimeException("参数 total 不合法！");
                }
                return moveRetPoint(
                        allocate4Stamp(
                                n,
                                total.divide(new BigDecimal(100), 0,
                                        BigDecimal.ROUND_HALF_UP),
                                nTax.divide(new BigDecimal(100), 0,
                                        BigDecimal.ROUND_HALF_UP)
                        ),
                        Direction.RIGHT,
                        2,
                        0);
            case 3:
                /* 是否有小数，不能有小数
                   必须能被10整除
                 */
                if (getNumberOfDecimalPlaces(total) > 0
                        || getNumberOfDecimalPlaces(nTax) > 0
                        || backZeroCount1 < 1
                        || backZeroCount2 < 1
                        ) {
                    throw new RuntimeException("参数 total 不合法！");
                }
                return moveRetPoint(
                        allocate4Stamp(
                                n,
                                total.divide(new BigDecimal(10), 0,
                                        BigDecimal.ROUND_HALF_UP),
                                nTax.divide(new BigDecimal(10), 0,
                                        BigDecimal.ROUND_HALF_UP)
                        ),
                        Direction.RIGHT,
                        1,
                        0);
            case 4:
                return allocate4Stamp(
                        n,
                        total,
                        nTax
                );
            default:
                throw new RuntimeException("ERROR!小数位数<" + point + ">不支持，待完善...");
        }
    }

    /**
     * <br>功能描述:
     * 组合金额、税额的分摊结果，从而实现联合分摊
     * <br>处理逻辑:
     * <p>
     * <p>
     * 已知：
     * 价格  数量
     * p1    n1
     * p2    n2
     * 税额  数量
     * t1    m1
     * t2    m2
     * <p>
     * 公理：
     * 1、n1+n2 = m1+m2
     * 2、如果max(m)<max(n),分摊数量小的那个税额
     * max(m)=max(n) 被包含在第二级的场景一里面了。
     * 否则max(m)>max(n),分摊数量多的那个税额
     * <p>
     * 场景：
     * 第一级：
     * 1、 金额一条 税一条V
     * 返回一条：
     * p  t    n1=m1    n2=m2=0(所以只返回一条)
     * 2、 金额一条 税两条V
     * 返回两条：
     * p  t1   n1=m1
     * p  t2   n2=m2
     * <p>
     * <p>
     * 3、 金额两条  税一条V
     * 返回两条：
     * p1  t   n1=m1
     * p2  t   n2=m2
     * <p>
     * 4、 金额两条  税两条
     * 第二级：
     * 场景1：
     * 数量一致（n1=m1）  V
     * 返回两条：
     * p1  t1   n1=m1
     * p2  t2   n2=m2
     * <p>
     * 场景2：
     * 数量不一致（n1<>m1） n1+n2 = m1+m2
     * （这里分别按价格排序： p1<=p2,  t1<=t2，保证大税优先分摊到大价格上）
     * 价格  数量
     * p1    n1
     * p2    n2
     * 税额  数量
     * t1    m1
     * t2    m2
     * <p>
     * ############################   重点 begin #######################################################
     * 第三级：
     * 场景1：max(m)<max(n),分摊数量小的那个税额，min(n)、max(m)分摊满
     * n1<m1 =>  n2>m2
     * n1<m1 -- 不一定m1>m2   *V  1、9   2、8   1<9
     * <p>
     * 单价数量n   税额数量m
     * 1           2
     * 9           8
     * (场景1)公式：
     * 价格  税额  数量
     * P(min(n))   T(min(m))    1  = min(n)
     * P(max(n))   T(min(m))    1  = min(m)-min(n)  分摊数量小的那个税额
     * P(max(n))   T(max(m))    8  = max(m)
     * <p>
     * <p>
     * 场景2：max(m)>max(n),分摊数量多的那个税额  min(m)、max(n)分摊满
     * n1>m1  =>  n2<m2
     * n1>m1 -- 不一定m1<m2  *V
     * <p>
     * n    m
     * 2    1
     * 8    9
     * (场景2)公式：
     * 价格  税额  数量
     * P(min(n))   T(min(m))    1  = min(m)
     * P(min(n))   T(max(m))    1  = min(n)-min(m) 分摊数量多的那个税额
     * P(max(n))   T(max(m))    8  = max(n)
     * <p>
     * ############################   重点 end #######################################################
     *
     * @param pRow 金额的分摊记录 元素必须按数量升序排列
     * @param tRow 税额的分摊记录 元素必须按数量升序排列
     * @return java.util.List<com.copote.nstamp.common.util.FundsAllocationRow>
     * @throws
     * @author daizd 2020/2/27 16:20
     * <br>修改记录: {修改人 修改原因 修改时间}
     */
    private List<FundsAllocationRow2> allocateTheSecondTime(List<FundsAllocationRow> pRow,
                                                            List<FundsAllocationRow> tRow) {
        /* 入参校验
         * 这里的入参来自 allocate4Stamp(Long, BigDecimal)的返回，所以不做严格校验
         * */
        if (null == pRow || pRow.size() == 0 || pRow.size() >= 3
                || null == tRow || tRow.size() == 0 || tRow.size() >= 3) {
            throw new RuntimeException("参数n不合法！");
        }
        // 金额分摊结果数量
        int pRet = pRow.size();
        // 税额分摊结果数量
        int tRet = tRow.size();

        // 1、 金额一条 税一条V
        if (pRet == 1 && tRet == 1) {
            return oneAndOne(pRow, tRow);
        }
        // 2、 金额一条 税两条V
        else if (pRet == 1 && tRet == 2) {
            return oneAndTwo(pRow, tRow);
        }
        // 3、 金额两条  税一条V
        else if (pRet == 2 && tRet == 1) {
            return twoAndOne(pRow, tRow);
        }
        // 4、 金额两条  税两条
        else if (pRet == 2 && tRet == 2) {
            return twoAndTwo(pRow, tRow);
        } else {
            throw new RuntimeException("参数不合法，永远不会被执行");
        }
    }

    /**
     * 1、 金额一条 税一条V
     * 返回一条：
     * p  t    n1=m1    n2=m2=0(所以只返回一条)
     *
     * @param pRow 金额的分摊记录 集合元素(如果有多个)必须按数量升序排列
     * @param tRow 金额的分摊记录 集合元素(如果有多个)必须按数量升序排列
     * @return
     */
    private List<FundsAllocationRow2> oneAndOne(List<FundsAllocationRow> pRow,
                                                List<FundsAllocationRow> tRow) {
        List<FundsAllocationRow2> ret
                = new ArrayList<FundsAllocationRow2>(1);
        if (!pRow.get(0).getnSl().equals(tRow.get(0).getnSl())) {
            throw new RuntimeException("分摊异常：入参校验不通过<两者数量不两等1>！");
        }
        FundsAllocationRow2 r = new FundsAllocationRow2(
                pRow.get(0).getnSl(),
                pRow.get(0).getPrice(),
                tRow.get(0).getPrice());
        ret.add(r);

        return ret;
    }

    /**
     * 2、 金额一条 税两条V
     * 返回两条：
     * p  t1   n1=m1
     * p  t2   n2=m2
     *
     * @param pRow 集合元素(如果有多个)必须按数量升序排列
     * @param tRow 集合元素(如果有多个)必须按数量升序排列
     * @return
     */
    private List<FundsAllocationRow2> oneAndTwo(List<FundsAllocationRow> pRow,
                                                List<FundsAllocationRow> tRow) {
        List<FundsAllocationRow2> ret
                = new ArrayList<FundsAllocationRow2>(1);
        if (pRow.get(0).getnSl()
                != tRow.get(0).getnSl() + tRow.get(1).getnSl()) {
            throw new RuntimeException("分摊异常：入参校验不通过<两者数量合计不相等2>！");
        }
        for (FundsAllocationRow el : tRow) {
            FundsAllocationRow2 r = new FundsAllocationRow2(
                    el.getnSl(),
                    pRow.get(0).getPrice(),
                    el.getPrice());
            ret.add(r);
        }

        return ret;
    }

    /**
     * 3、 金额两条  税一条V
     * 返回两条：
     * p1  t   n1=m1
     * p2  t   n2=m2
     *
     * @param pRow 集合元素(如果有多个)必须按数量升序排列
     * @param tRow 集合元素(如果有多个)必须按数量升序排列
     * @return
     */
    private List<FundsAllocationRow2> twoAndOne(List<FundsAllocationRow> pRow,
                                                List<FundsAllocationRow> tRow) {
        List<FundsAllocationRow2> ret
                = new ArrayList<FundsAllocationRow2>(1);
        if (pRow.get(0).getnSl() + pRow.get(1).getnSl()
                != tRow.get(0).getnSl()) {
            throw new RuntimeException("分摊异常：入参校验不通过<两者数量合计不相等3>！");
        }
        for (FundsAllocationRow el : pRow) {
            FundsAllocationRow2 r = new FundsAllocationRow2(
                    el.getnSl(),
                    el.getPrice(),
                    tRow.get(0).getPrice());
            ret.add(r);
        }

        return ret;
    }

    /**
     * 4、 金额两条  税两条
     * 场景1：
     * 数量一致（n1=m1）  V
     * 返回两条：
     * p1  t1   n1=m1
     * p2  t2   n2=m2
     * <p>
     * 场景2：
     * 场景2-1：max(m)<max(n),分摊数量小的那个税额，min(n)、max(m)分摊满
     * 场景2-2：max(m)>max(n),分摊数量多的那个税额  min(m)、max(n)分摊满
     *
     * @param pRow 金额的分摊记录 元素必须按数量升序排列
     * @param tRow 税额的分摊记录 元素必须按数量升序排列
     * @return 金额、税额的数量分摊结果
     */
    private List<FundsAllocationRow2> twoAndTwo(List<FundsAllocationRow> pRow,
                                                List<FundsAllocationRow> tRow) {

        long n1 = pRow.get(0).getnSl();
        long n2 = pRow.get(1).getnSl();

        long m1 = tRow.get(0).getnSl();
        long m2 = tRow.get(1).getnSl();

        if (n1 + n2
                != m1 + m2) {
            throw new RuntimeException("分摊异常：入参校验不通过<两者数量合计不相等4>！");
        }
        if (n1 > n2 || m1 > m2) {
            throw new RuntimeException("分摊异常：入参校验不通过<集合中的元素没有按数量升序排列>！");
        }
        /* 场景1
         *
         * 数量一致（n1=m1） */
        if (n1 == m1) {
            List<FundsAllocationRow2> ret = new ArrayList<FundsAllocationRow2>();
            FundsAllocationRow2 r1 = new FundsAllocationRow2(
                    pRow.get(0).getPrice(), tRow.get(0).getPrice(), n1
            );
            FundsAllocationRow2 r2 = new FundsAllocationRow2(
                    pRow.get(1).getPrice(), tRow.get(1).getPrice(), n2
            );
            /* 顺序： n asc, m asc  */
            ret.add(r1);
            ret.add(r2);

            return ret;
        }
        /* 场景2-1
                max(m)<max(n),分摊数量小的那个税额，min(n)、max(m)分摊满
         */
        if (Math.max(m1, m2) < Math.max(n1, n2)) {
            return maxMltMaxN(pRow, tRow);
        }
        /* 场景2-2
                max(m)>max(n),分摊数量多的那个税额  min(m)、max(n)分摊满
        */
        else if (Math.max(m1, m2) > Math.max(n1, n2)) {
            return maxMgtMaxN(pRow, tRow);
        } else if (Math.max(m1, m2) == Math.max(n1, n2)) {
            // 理论上永远不会被执行
            throw new RuntimeException("分摊异常：场景并不在本方法范围内<两者数量合计相等>！");
        }
        return null;
    }

    /**
     * 1、max(m)<max(n),分摊数量小的那个税额，min(n)、max(m)分摊满
     * <p>
     * <p>
     * (场景1)公式：
     * 价格          税额        数量
     * P(min(n))   T(min(m))    1  = min(n)
     * P(max(n))   T(min(m))    1  = min(m)-min(n)  分摊数量小的那个税额
     * P(max(n))   T(max(m))    8  = max(m)
     * <p/>
     *
     * @param pRow 集合元素(如果有多个)必须按数量升序排列
     * @param tRow 集合元素(如果有多个)必须按数量升序排列
     * @return
     */
    private List<FundsAllocationRow2> maxMltMaxN(List<FundsAllocationRow> pRow,
                                                 List<FundsAllocationRow> tRow) {
        /* 数量少的价格条数 n1  */
        Long minN = pRow.get(0).getnSl();
        /* 数量少的价格条数 n2  */
//        Long maxN = pRow.get(1).getnSl();

        /* 数量少的税额条数 m1 */
        Long minM = tRow.get(0).getnSl();
        /* 数量多的税额条数 m2 */
        Long maxM = tRow.get(1).getnSl();

        /* 价格 p1  */
        BigDecimal Pmin_n = pRow.get(0).getPrice();
        /* 价格 p2  */
        BigDecimal Pmax_n = pRow.get(1).getPrice();

        /* 税额 t1 */
        BigDecimal Tmin_m = tRow.get(0).getPrice();
        /* 税额 t2 */
        BigDecimal Tmax_m = tRow.get(1).getPrice();

        /*   价格          税额        数量
         *  P(min(n))   T(min(m))    1  = min(n)
         *  P(max(n))   T(min(m))    1  = min(m)-min(n)  分摊数量小的那个税额
         *  P(max(n))   T(max(m))    8  = max(m)
         */
        List<FundsAllocationRow2> ret = new ArrayList<FundsAllocationRow2>();
        FundsAllocationRow2 r1 = new FundsAllocationRow2(Pmin_n, Tmin_m, minN);
        FundsAllocationRow2 r2 = new FundsAllocationRow2(Pmax_n, Tmin_m, minM - minN);
        FundsAllocationRow2 r3 = new FundsAllocationRow2(Pmax_n, Tmax_m, maxM);
        /* 顺序： n asc, m asc  */
        ret.add(r1);
        ret.add(r2);
        ret.add(r3);

        return ret;
    }

    /**
     * 2、 max(m)>max(n),分摊数量多的那个税额  min(m)、max(n)分摊满
     * <p>
     * <p>
     * (场景2)公式：
     * 价格          税额      数量
     * P(min(n))   T(min(m))    1  = min(m)
     * P(min(n))   T(max(m))    1  = min(n)-min(m) 分摊数量多的那个税额
     * P(max(n))   T(max(m))    8  = max(n)
     * <p/>
     *
     * @param pRow 集合元素(如果有多个)必须按数量升序排列
     * @param tRow 集合元素(如果有多个)必须按数量升序排列
     * @return
     */
    private List<FundsAllocationRow2> maxMgtMaxN(List<FundsAllocationRow> pRow,
                                                 List<FundsAllocationRow> tRow) {
        /* 数量少的价格条数 n1  */
        Long minN = pRow.get(0).getnSl();
        /* 数量少的价格条数 n2  */
        Long maxN = pRow.get(1).getnSl();

        /* 数量少的税额条数  m1 */
        Long minM = tRow.get(0).getnSl();
        /* 数量多的税额条数  m2 */
//        Long maxM = tRow.get(1).getnSl();

        /* 价格   p1  */
        BigDecimal Pmin_n = pRow.get(0).getPrice();
        /*  价格  p2  */
        BigDecimal Pmax_n = pRow.get(1).getPrice();

        /* 税额 t1 */
        BigDecimal Tmin_m = tRow.get(0).getPrice();
        /* 税额 t2 */
        BigDecimal Tmax_m = tRow.get(1).getPrice();

        /*
         *  价格          税额      数量
         *  P(min(n))   T(min(m))    1  = min(m)
         *  P(min(n))   T(max(m))    1  = min(n)-min(m) 分摊数量多的那个税额
         *  P(max(n))   T(max(m))    8  = max(n)
         */
        List<FundsAllocationRow2> ret = new ArrayList<FundsAllocationRow2>();
        FundsAllocationRow2 r1 = new FundsAllocationRow2(Pmin_n, Tmin_m, minM);
        FundsAllocationRow2 r2 = new FundsAllocationRow2(Pmin_n, Tmax_m, minN - minM);
        FundsAllocationRow2 r3 = new FundsAllocationRow2(Pmax_n, Tmax_m, maxN);
        /* 顺序： n asc, m asc  */
        ret.add(r1);
        ret.add(r2);
        ret.add(r3);

        return ret;
    }

    /**
     * <br>功能描述: 获取小数位数
     * <br>处理逻辑:
     *
     * @param number
     * @return int
     * @throws
     * @author daizd   2020/2/26 10:28
     */
    public int getNumberDecimalDigits(double number) {
        String moneyStr = String.valueOf(number);
        String[] num = moneyStr.split("\\.");
        if (num.length == 2) {
            for (; ; ) {
                if (num[1].endsWith("0")) {
                    num[1] = num[1].substring(0, num[1].length() - 1);
                } else {
                    break;
                }
            }
            return num[1].length();
        } else {
            return 0;
        }
    }

    public int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

    /**
     * @param ret       入参--需要转换的值
     * @param direction
     * @param num       移动的位数
     * @param scale     保留的有效位数
     * @return
     * @see com.copote.nstamp.common.constant.Direction;
     */
    public List<FundsAllocationRow2> moveRetPoint(
            List<FundsAllocationRow2> ret,
            int direction,
            int num,
            int scale
    ) {
        //  加法：add  减法：subtract   乘法：multiply  除法：divide
        for (FundsAllocationRow2 r : ret) {
            switch (direction) {
                // 小数点左移，除
                case Direction.LEFT:
                    r.setPrice(r.getPrice()
                            // 左移一位除10
                            .divide(new BigDecimal(simpleCircle(num)))
                            .setScale(scale, BigDecimal.ROUND_HALF_UP)
                    );
                    r.setTax(r.getTax()
                            .divide(new BigDecimal(simpleCircle(num)))
                            .setScale(scale, BigDecimal.ROUND_HALF_UP)
                    );
                    break;

                // 小数点右移，乘
                case Direction.RIGHT:
                    // 右移一位乘10
                    r.setPrice(r.getPrice()
                            .multiply(new BigDecimal(simpleCircle(num)))
                            .setScale(scale, BigDecimal.ROUND_HALF_UP)
                    );
                    r.setTax(r.getTax()
                            .multiply(new BigDecimal(simpleCircle(num)))
                            .setScale(scale, BigDecimal.ROUND_HALF_UP)
                    );
                    break;

                default:
                    throw new RuntimeException("小数点移动的方向参数不正确direction<" + direction + ">");
            }
        }
        return ret;
    }

    /**
     * 算10的几次方
     * 简单的循环计算
     *
     * @param num
     * @return
     */
    public int simpleCircle(int num) {
        int sum = 1;
        //判断传入数是否为负数
        if (num < 1) {
            //抛出不合理参数异常
            throw new IllegalArgumentException("必须为正整数!");
        }
        //循环num
        for (int i = 1; i <= num; i++) {
            //每循环一次进行乘法运算
            sum *= 10;
        }
        //返回阶乘的值
        return sum;
    }

    /**
     * 计算数值整数位最后有几个零
     * 简单的循环计算
     *
     * @param bigDecimal
     * @return
     */
    public int zeroCount(BigDecimal bigDecimal) {
        bigDecimal.setScale(0);
        String str = bigDecimal.toPlainString();
        String zs = "0";
        for (int i = 1; i <= 13; i++) {
            if (!str.endsWith(zs)) {
                return i - 1;
            }
            zs += "0";
        }
        // 如果整数以12个0结尾就报错
        throw new IllegalArgumentException("数字太大超过支持范围!");
    }


    public static void main(String args[]) {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        int i = util.simpleCircle(3);
        int j = util.zeroCount(new BigDecimal(1));
        System.out.println("j:" + j);
        j = util.zeroCount(new BigDecimal(10));
        System.out.println("j:" + j);
        j = util.zeroCount(new BigDecimal(100));
        System.out.println("j:" + j);
        j = util.zeroCount(new BigDecimal(1000));
        System.out.println("j:" + j);
        j = util.zeroCount(new BigDecimal(10000));
        System.out.println("j:" + j);
        j = util.zeroCount(new BigDecimal(1000000000));
        System.out.println("j:" + j);
        j = util.zeroCount(new BigDecimal(10000000000L));
        System.out.println("j:" + j);
        long b = new BigDecimal(1.8).longValue();
        System.out.println("b:" + b);
    }
}
