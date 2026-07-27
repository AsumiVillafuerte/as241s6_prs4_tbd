package pe.edu.vallegrande.sigrc.product_sale.application.mappers;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.request.ProductItemRequest;
import pe.edu.vallegrande.sigrc.product_sale.application.dto.request.ProductSaleRequest;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSale;
import pe.edu.vallegrande.sigrc.product_sale.domain.models.ProductSaleItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductSaleMapper {

    public ProductSale toDomain(ProductSaleRequest request) {
        List<ProductSaleItem> items = request.getItems() == null ? Collections.emptyList() :
                request.getItems().stream()
                        .map(this::itemToDomain)
                        .collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(i -> {
                    BigDecimal price = i.getUnitPrice() != null ? i.getUnitPrice() : BigDecimal.ZERO;
                    int qty = i.getQuantity() != null ? i.getQuantity() : 0;
                    return price.multiply(BigDecimal.valueOf(qty));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ProductSale.builder()
                .patientId(request.getPatientId())
                .userId(request.getUserId())
                .saleType(normalizeSaleType(request.getSaleType()))
                .status("Consignado")
                .saleDate(LocalDate.now())
                .total(total)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .items(items)
                .build();
    }

    private String normalizeSaleType(String saleType) {
        if (saleType == null) return "Vendido";
        String lower = saleType.toLowerCase();
        if (lower.contains("donacion") || lower.contains("donación") || lower.contains("donada")) {
            return "Donacion";
        }
        return "Vendido";
    }

    public ProductSaleItem itemToDomain(ProductItemRequest request) {
        BigDecimal unitPrice = request.getUnitPrice() != null ? request.getUnitPrice() : BigDecimal.ZERO;
        int quantity = request.getQuantity() != null ? request.getQuantity() : 0;
        return ProductSaleItem.builder()
                .productId(request.getProductId())
                .quantity(quantity)
                .unitPrice(unitPrice)
                .subtotal(unitPrice.multiply(BigDecimal.valueOf(quantity)))
                .build();
    }
}
