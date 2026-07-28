package pe.edu.vallegrande.sigrc.medicine.sale.application.mappers;

import pe.edu.vallegrande.sigrc.medicine.sale.application.dto.request.MedicineSaleRequest;
import pe.edu.vallegrande.sigrc.medicine.sale.application.dto.request.SaleItemRequest;
import pe.edu.vallegrande.sigrc.medicine.sale.application.dto.response.MedicineSaleResponse;
import pe.edu.vallegrande.sigrc.medicine.sale.application.dto.response.SaleItemResponse;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.MedicineSale;
import pe.edu.vallegrande.sigrc.medicine.sale.domain.model.SaleItem;

import java.util.List;
import java.util.stream.Collectors;

public class MedicineSaleMapper {

    private MedicineSaleMapper() {}

    // ── Request → Domain ────────────────────────────────────────────────────

    public static MedicineSale toDomain(MedicineSaleRequest request) {
        return MedicineSale.builder()
                .patientId(request.getPatientId())
                .cashierId(request.getCashierId())
                .type(request.getType())
                .status(request.getStatus())
                .items(toItemDomainList(request.getItems()))
                .build();
    }

    // ── Domain → Response ────────────────────────────────────────────────────

    public static MedicineSaleResponse toResponse(MedicineSale sale) {
        return MedicineSaleResponse.builder()
                .id(sale.getId())
                .ticket(sale.getTicket())
                .saleDate(sale.getSaleDate())
                .patientId(sale.getPatientId())
                .patientName(sale.getPatientName())
                .dni(sale.getDni())
                .cashierId(sale.getCashierId())
                .cashierName(sale.getCashierName())
                .total(sale.getTotal())
                .type(sale.getType())
                .status(sale.getStatus())
                .items(toItemResponseList(sale.getItems()))
                .build();
    }

    // ── Items ────────────────────────────────────────────────────────────────

    private static List<SaleItem> toItemDomainList(List<SaleItemRequest> requests) {
        if (requests == null) return List.of();
        return requests.stream()
                .map(r -> SaleItem.builder()
                        .medicationId(r.getMedicationId())
                        .medicationName(r.getMedicationName())
                        .quantity(r.getQuantity())
                        .unitPrice(r.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<SaleItemResponse> toItemResponseList(List<SaleItem> items) {
        if (items == null) return List.of();
        return items.stream()
                .map(item -> SaleItemResponse.builder()
                        .medicationId(item.getMedicationId())
                        .medicationName(item.getMedicationName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
