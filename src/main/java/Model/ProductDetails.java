
package Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class ProductDetails {

    private String productName;
    private String productSize;
    private String productColour;
    private String productSubtotal;

    @Override
    public String toString() {
        return "ProductDetails{" +
                "productName='" + productName + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productColour='" + productColour + '\'' +
                ", productSubtotal='" + productSubtotal + '\'' +
                '}';
    }
}
