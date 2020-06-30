package person.daizhongde.virtue.util.money;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * <br>功能描述:
 * 资金分摊单元测试类
 * <br>处理逻辑:
 *
 * @author daizd   2020/2/26 10:23
 */
public class FundsAllocationUtilsTest  {
	private static final Logger log = LoggerFactory
			.getLogger(FundsAllocationUtilsTest.class);
    /**
     * <br>功能描述:   除尽
     * 测BigDecimal实现
     */
    @Test
    public void testAllocate_divisibled() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 30000L;
        BigDecimal total = new BigDecimal(30);
        /* 金额没有乘1万 */
        List<FundsAllocationRow> list = util.allocate(n, total);
        log.info(">>>>>除尽={}", JSON.toJSONString(list));
        Assert.assertNotNull(list);
        Assert.assertFalse(CollectionUtils.isEmpty(list));
        Assert.assertEquals(new BigDecimal("0.0010"), list.get(0).getPrice());
    }

    /**
     * <br>功能描述:   金额没乘1万
     * 余得少
     * 测BigDecimal实现
     */
    @Test
    public void testAllocate_remainderSmall() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 30000L;
        BigDecimal total = new BigDecimal(10);
        /* 金额没有乘1万 */
        List<FundsAllocationRow> list = util.allocate(n, total);
        log.info(">>>>>余得少={}", JSON.toJSONString(list));
        Assert.assertNotNull(list);
        Assert.assertFalse(CollectionUtils.isEmpty(list));
    }

    /**
     * <br>功能描述:   金额没乘1万
     * 余得多
     * 测BigDecimal实现
     */
    @Test
    public void testAllocate_remainderBig() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 30000L;
        BigDecimal total = new BigDecimal(11);
        /* 金额没有乘1万 */
        log.info(">>>>>余得多={}", JSON.toJSONString(util.allocate(n, total)));

    }

    /**
     * <br>功能描述:   金额没乘1万
     * 数量为1
     * 测BigDecimal实现
     */
    @Test
    public void testAllocate_nEqualOne() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 1L;
        BigDecimal total = new BigDecimal(11);
        /* 金额没有乘1万 */
        log.info(">>>>>余得多={}", JSON.toJSONString(util.allocate(n, total)));

    }
    /**
     * <br>功能描述:   金额没乘1万
     * 单价少于0.0001
     * 测BigDecimal实现
     */
    @Test
    public void testAllocate_priceltmin() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 3L;
        BigDecimal total = new BigDecimal("0.0001").setScale(4, BigDecimal.ROUND_HALF_UP);
        String msg = "";
        /* 金额没有乘1万 */
        try {
            log.info(">>>>>单价少于0.0001={}", JSON.toJSONString(util.allocate(n, total)));
        } catch (Exception e) {
            log.error("", e);
            msg = e.getLocalizedMessage();
//            Assert.assertEquals(e.getLocalizedMessage(),"参数不合法！单价不能低于0.0001");
        }
        Assert.assertEquals(msg, "参数不合法！单价不能低于0.0001");
    }

    /**
     * <br>功能描述:   金额乘了1万
     * 测BigDecimal实现
     * 只分摊金额
     */
    @Test
    public void testAllocate4Stamp() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 30000L;
        BigDecimal longtotal2 = new BigDecimal(100000);
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现={}", JSON.toJSONString(util.allocate4Stamp(n, longtotal2)));
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * 1、 金额一条 税一条V
     * 测试覆盖 oneAndOne
     * <p>
     * 金额 100， 税额 15, 数量 5  -->金额剩1万
     * 金额 1000000， 税额 150000, 数量 5
     */
    @Test
    public void testAllocate4Stamp2_1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 5L;
        BigDecimal amount = new BigDecimal(1000000);
        BigDecimal nTax = new BigDecimal(150000);
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount, nTax))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * <p>
     * 2、 金额一条 税两条V
     * 测试覆盖 oneAndTwo
     * <p>
     * 金额 0.0100， 税额 0.0014, 数量 10  -->金额剩1万
     * 金额 100， 税额 14, 数量 10
     */
    @Test
    public void testAllocate4Stamp2_2() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 10L;
        BigDecimal amount = new BigDecimal(100);
        BigDecimal nTax = new BigDecimal(14);
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount, nTax))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * <p>
     * 2、 金额一条 税两条V
     * 测试覆盖 oneAndTwo
     * <p>
     * 金额 100， 税额 14, 数量 10  -->金额剩1万
     * 金额 1000000， 税额 140000, 数量 10
     */
    @Test
    public void testAllocate4Stamp2_2_valid() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 10L;
        BigDecimal amount = new BigDecimal(1000000);
        BigDecimal nTax = new BigDecimal(140000);
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, nTax))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * <p>
     * 3、 金额两条  税一条V
     * 测试覆盖 TwoAndOne
     * <p>
     * 金额 100， 税额 11, 数量 11  -->金额剩1万
     * 金额 1000000， 税额 110000, 数量 11
     */
    @Test
    public void testAllocate4Stamp2_3() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 11L;
        BigDecimal amount = new BigDecimal(1000000);
        BigDecimal nTax = new BigDecimal(110000);
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount, nTax))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * <p>
     * 4、 金额两条  税两条
     * 第二级：
     * 场景1：
     * 数量一致（n1=m1），n1<n2
     * 返回两条：
     * p1  t1   n1=m1
     * p2  t2   n2=m2
     * <p>
     * 金额 22， 税额 11, 数量 10  -->金额剩1万  ?
     * 金额 2210000， 税额 110000, 数量 10
     */
    @Test
    public void testAllocate4Stamp2_4_1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 10L;
        BigDecimal amount = new BigDecimal(2210000);
        BigDecimal nTax = new BigDecimal(110000);
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount, nTax))
        );
    }


    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * <p>
     * 4、 金额两条  税两条
     * 第二级：
     * <p>
     * 子场景4-1：
     * 数量不一致（n1<>m1） n1+n2 = m1+m2 ，n1<n2
     * （这里分别按价格排序： p1<=p2,  t1<=t2，保证大税优先分摊到大价格上）
     * 价格  数量
     * p1    n1
     * p2    n2
     * 税额  数量
     * t1    m1
     * t2    m2
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
     * 金额 0.1999， 税额 0.0222, 数量 10  -->金额剩1万  ?
     * 金额 1999， 税额 222, 数量 10
     */
    @Test
    public void testAllocate4Stamp2_4_2_1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 10L;
        BigDecimal amount = new BigDecimal(1999);
        BigDecimal nTax = new BigDecimal(222);

        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现1={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现2={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, nTax))
        );


        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现3={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount, nTax))
        );
    }


    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * <p>
     * 4、 金额两条  税两条
     * 第二级：
     * <p>
     * 子场景4-2：
     * max(m)>max(n),分摊数量多的那个税额  min(m)、max(n)分摊满
     * n1>m1  =>  n2<m2
     * n1>m1 -- 不一定m1<m2  *V
     * <p>
     * n    m
     * 2    1
     * 8    9
     * (场景2)公式：
     * 价格  税额  数量
     * P(min(n))   T(max(m))    1  = max(n)
     * P(max(n))   T(max(m))    1  = max(m)-max(n) 分摊数量多的那个税额
     * P(max(n))   T(min(m))    8  = min(m)
     * <p>
     * <p>
     * <p>
     * 金额 0.0222， 税额 0.0019, 数量 10  -->金额剩1万  ?
     * 金额 222， 税额 19, 数量 10
     */
    @Test
    public void testAllocate4Stamp2_4_2_2() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 10L;
        BigDecimal amount = new BigDecimal(222);
        BigDecimal nTax = new BigDecimal(19);
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount, nTax))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * <p>
     * 数量为1
     * <p>
     * 金额 100， 税额 14, 数量 1  -->金额剩1万
     * 金额 1000000， 税额 140000, 数量 1
     */
    @Test
    public void testAllocate4Stamp2_nEqualOne() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 1L;
        BigDecimal amount = new BigDecimal(1000000);
        BigDecimal nTax = new BigDecimal(140000);
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现={}",
                JSON.toJSONString(util.allocate4Stamp(n, amount, nTax))
        );
    }

    /**
     * <br>功能描述:   除尽
     * 测第一版Double实现
     *
     * @deprecated
     */
    @Test
    public void testAllocate_divisibled1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 30000L;
        Double total = 30D;
        /* 金额没有乘1万 */
        log.info(">>>>>测第一版Double实现={}", JSON.toJSONString(util.allocate(n, total)));
    }

    /**
     * <br>功能描述:   金额没乘1万
     * 余得少
     * 测第一版Double实现
     *
     * @deprecated
     */
    @Test
    public void testAllocate_remainderSmall1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 30000L;
        Double total = 10D;
        /* 金额没有乘1万 */
        log.info(">>>>>测第一版Double实现 余得少 ={}", JSON.toJSONString(util.allocate(n, total)));
    }

    /**
     * <br>功能描述:   金额没乘1万
     * 余得多
     * 测第一版Double实现
     *
     * @deprecated
     */
    @Test
    public void testAllocate_remainderBig1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 30000L;
        Double total = 11D;
        /* 金额没有乘1万 */
        log.info(">>>>>测第一版Double实现 余得多={}", JSON.toJSONString(util.allocate(n, total)));
    }

    /**
     * <br>功能描述:   金额没乘1万
     * 数量为1
     * 测第一版Double实现
     *
     * @deprecated
     */
    @Test
    public void testAllocate_nEqualOne1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 1L;
        Double total = 0.0001D;

        log.info(">>>>>测第一版Double实现 数量为1={}",
                JSON.toJSONString(util.allocate(n, total)));
    }
    /**
     * <br>功能描述:   金额没乘1万
     * 单价少于0.0001
     * 测第一版Double实现
     *
     * @deprecated
     */
    @Test
    public void testAllocate_priceltmin1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();
        Long n = 3L;
        Double total = 0.0001D;

        String msg = "";
        /* 金额没有乘1万 */
        try {
            log.info(">>>>>测第一版Double实现 单价少于0.0001={}",
                    JSON.toJSONString(util.allocate(n, total)));
        } catch (Exception e) {
            log.error("", e);
            msg = e.getLocalizedMessage();
//            Assert.assertEquals(e.getLocalizedMessage(),"参数不合法！单价不能低于0.0001");
        }
        Assert.assertEquals(msg, "参数不合法！单价不能低于0.0001");
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * 1、 金额一条 税一条V
     * 测试覆盖 保留0位小数(取整）
     * <p>
     * 金额 10， 税额 3, 数量 10  -->金额剩1万  ?
     * 金额 100000， 税额 30000, 数量 10
     * <p>
     * 此用例用一个用例测四类场景
     */
    @Test
    public void testAllocate4StampWithPoint_0() {
    	FundsAllocationUtils util = new FundsAllocationUtils();

        Long n = 11L;
        BigDecimal amount = new BigDecimal(1000000);
        BigDecimal nTax = new BigDecimal(300000);

        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留0位小数(取整）0={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 0))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留1位小数(取整）1={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 1))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留2位小数(取整）2={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 2))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留3位小数(取整）3={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 3))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留4位小数(取整）4={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 4))
        );

        Long n2 = 11L;
        BigDecimal amount2 = new BigDecimal(1000000);
        BigDecimal nTax2 = new BigDecimal(0);
        log.info(">>>>>测BigDecimal税为0  保留4位小数(取整）4={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n2, amount2, nTax2, 4))
        );
        Long n3 = 11L;
        BigDecimal amount3 = new BigDecimal(0);
        BigDecimal nTax3 = new BigDecimal(0);
        log.info(">>>>>测BigDecimal价格为0   保留4位小数(取整）4={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n3, amount3, nTax3, 4))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * 1、 金额一条 税一条V
     * 测试覆盖 保留1位小数
     * <p>
     * 金额 199.9， 税额 22.2, 数量 10  -->金额剩1万  ?
     * 金额 1999000， 税额 222000, 数量 10
     */
    @Test
    public void testAllocate4StampWithPoint_1() {
    	FundsAllocationUtils util = new FundsAllocationUtils();


        Long n = 10L;
        BigDecimal amount = new BigDecimal(1999000);
        BigDecimal nTax = new BigDecimal(222000);

        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留1位小数1={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount.divide(new BigDecimal(1000), 0,
                                BigDecimal.ROUND_HALF_UP)
                        ))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留1位小数2={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, nTax.divide(new BigDecimal(1000), 0,
                                BigDecimal.ROUND_HALF_UP)
                        ))
        );


        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留1位小数3={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 1))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * 1、 金额一条 税一条V
     * 测试覆盖 保留2位小数
     * <p>
     * 金额 19.99， 税额 2.22, 数量 10  -->金额剩1万  ?
     * 金额 199900， 税额 22200, 数量 10
     */
    @Test
    public void testAllocate4StampWithPoint_2() {
    	FundsAllocationUtils util = new FundsAllocationUtils();


        Long n = 10L;
        BigDecimal amount = new BigDecimal(199900);
        BigDecimal nTax = new BigDecimal(22200);

        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留2位小数1={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount.divide(new BigDecimal(100), 0,
                                BigDecimal.ROUND_HALF_UP)
                        ))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留2位小数2={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, nTax.divide(new BigDecimal(100), 0,
                                BigDecimal.ROUND_HALF_UP)))
        );


        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留2位小数3={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 2))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * 1、 金额一条 税一条V
     * 测试覆盖 保留3位小数
     * <p>
     * 金额 1.999， 税额 0.222, 数量 10  -->金额剩1万  ?
     * 金额 19990， 税额 2220, 数量 10
     */
    @Test
    public void testAllocate4StampWithPoint_3() {
    	FundsAllocationUtils util = new FundsAllocationUtils();


        Long n = 10L;
//        BigDecimal amount = new BigDecimal(19990);
//        BigDecimal nTax = new BigDecimal(2220);
        BigDecimal amount = new BigDecimal(19990);
        BigDecimal nTax = new BigDecimal(2220);

        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留3位小数1={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount.divide(new BigDecimal(10), 0,
                                BigDecimal.ROUND_HALF_UP)
                        ))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留3位小数2={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, nTax.divide(new BigDecimal(10), 0,
                                BigDecimal.ROUND_HALF_UP)
                        ))
        );


        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留3位小数3={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 3))
        );
    }

    /**
     * <br>功能描述:   额、税额两者同时做分摊
     * 集邮用（金额乘了1万)BigDecimal实现
     * 1、 金额一条 税一条V
     * 测试覆盖 保留4位小数
     * <p>
     * 金额 0.1999， 税额 0.0222, 数量 10  -->金额剩1万  ?
     * 金额 1999， 税额 222, 数量 10
     */
    @Test
    public void testAllocate4StampWithPoint_4() {
    	FundsAllocationUtils util = new FundsAllocationUtils();


        Long n = 10L;
        BigDecimal amount = new BigDecimal(1999);
        BigDecimal nTax = new BigDecimal(222);

        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留4位小数1={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, amount
                        ))
        );
        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留4位小数2={}",
                JSON.toJSONString(util
                        .allocate4Stamp(n, nTax
                        ))
        );

        /* 金额有乘1万 */
        log.info(">>>>>测BigDecimal实现 保留4位小数3={}",
                JSON.toJSONString(util
                        .allocate4StampWithPoint(n, amount, nTax, 4))
        );
    }


}
