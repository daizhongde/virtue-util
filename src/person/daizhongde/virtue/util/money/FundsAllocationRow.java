package person.daizhongde.virtue.util.money;

import java.math.BigDecimal;

/**
 * <br>类描述：
 * 用于采购明细生成入库单明细时的结算金额/税额分摊 构造用返回对象
 *
 * @author dzd daizhongde@copote.com	2020/2/26 1:00
 */
public class FundsAllocationRow {

    private Long nSl;
    private BigDecimal price;

    public FundsAllocationRow(Long nSl, BigDecimal price) {
        this.nSl = nSl;
        this.price = price;
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
}
