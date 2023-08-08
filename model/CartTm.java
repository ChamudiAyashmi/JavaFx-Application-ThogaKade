package model;

public class CartTm {
    String itemCode;
    String description;
    int qty;
    double unitPrice;
    double total;

    public CartTm() {
    }

    public CartTm(String itemCode, String description, int qty, double unitPrice, double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
