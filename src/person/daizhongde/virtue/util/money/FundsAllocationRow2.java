package person.daizhongde.virtue.util.money;

import java.math.BigDecimal;

/**
 * <br>类描述：
 * 用于采购明细生成入库单明细时的结算金额 和 税额分摊 构造用返回对象
 *
 * @author dzd daizhongde@copote.com	2020/2/26 1:00
 */
public class FundsAllocationRow2 {

    private Long nSl;
    private BigDecimal price;
    private BigDecimal tax;

    public FundsAllocationRow2(Long nSl, BigDecimal price, BigDecimal tax) {
        this.nSl = nSl;
        this.price = price;
        this.tax = tax;
    }

    /**
     * 这个构造函数纯粹是为了好看，对照公式好看，与公式表格中的列顺序一致
     *
     * @param price
     * @param tax
     * @param nSl
     */
    public FundsAllocationRow2(BigDecimal price, BigDecimal tax, Long nSl) {
        this.price = price;
        this.tax = tax;
        this.nSl = nSl;
    }

    public Long getnSl() {
        return nSl;
    }

    public void setnSl(Long nSl) {
        this.nSl = nSl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
