package numberfact.codeham.com.farmdelite;

/**
 * Created by Abhishek on 29/10/2017.
 */

public class CartItem {

    private String brand;
    private String detail;

    public CartItem() {
    }

    public CartItem(String brand, String detail, String mrp, String name, String price, String product_url, String stock) {
        this.brand = brand;
        this.detail = detail;
        this.mrp = mrp;
        this.name = name;
        this.price = price;
        this.product_url = product_url;
        this.stock = stock;
    }

    private String mrp;
    private String name;
    private String price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    private String product_url;
    private String stock;

}
