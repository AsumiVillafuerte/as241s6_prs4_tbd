package pe.edu.vallegrande.sigrc.shopping.application.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.sigrc.shopping.application.dto.request.CreateCompraRequest;
import pe.edu.vallegrande.sigrc.shopping.application.dto.response.CompraListItemResponse;
import pe.edu.vallegrande.sigrc.shopping.application.dto.response.CompraResponse;
import pe.edu.vallegrande.sigrc.shopping.application.dto.response.ProveedorDTO;
import pe.edu.vallegrande.sigrc.shopping.application.dto.response.UsuarioDTO;
import pe.edu.vallegrande.sigrc.shopping.domain.model.Compra;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompraMapper {

    private final DetalleCompraMapper detalleMapper;

    public Compra toDomain(CreateCompraRequest request, String usuarioId) {
        return Compra.builder()
                .numeroComprobante(request.numeroComprobante())
                .proveedorId(request.proveedorId())
                .usuarioId(usuarioId)
                .detalles(request.detalles().stream()
                        .map(detalleMapper::toDomain)
                        .toList())
                .build();
    }

    public CompraResponse toResponse(Compra compra) {
        ProveedorDTO proveedor = compra.getProveedorInfo() != null
                ? new ProveedorDTO(
                        compra.getProveedorInfo().supplierId(),
                        compra.getProveedorInfo().businessName(),
                        compra.getProveedorInfo().documentNumber())
                : null;

        UsuarioDTO usuario = compra.getUsuarioInfo() != null
                ? new UsuarioDTO(
                        compra.getUsuarioInfo().userId(),
                        compra.getUsuarioInfo().nombreCompleto(),
                        compra.getUsuarioInfo().role())
                : null;

        List<pe.edu.vallegrande.sigrc.shopping.application.dto.response.DetalleCompraResponse> detalles =
                compra.getDetalles() != null
                        ? compra.getDetalles().stream().map(detalleMapper::toResponse).toList()
                        : List.of();

        return new CompraResponse(
                compra.getId(),
                compra.getNumeroComprobante(),
                proveedor,
                usuario,
                compra.getTipo() != null ? compra.getTipo().name() : null,
                compra.getEstado() != null ? compra.getEstado().name() : null,
                compra.getPrecioCompraTotal(),
                compra.getPrecioVentaTotal(),
                compra.getFechaCompra(),
                compra.getCreatedAt(),
                detalles
        );
    }

    public CompraListItemResponse toListItem(Compra compra) {
        return new CompraListItemResponse(
                compra.getId(),
                compra.getNumeroComprobante(),
                compra.getProveedorInfo() != null ? compra.getProveedorInfo().businessName() : "",
                compra.getUsuarioInfo() != null ? compra.getUsuarioInfo().nombreCompleto() : "",
                compra.getTipo() != null ? compra.getTipo().name() : null,
                compra.getEstado() != null ? compra.getEstado().name() : null,
                compra.getPrecioCompraTotal(),
                compra.getPrecioVentaTotal(),
                compra.getFechaCompra()
        );
    }
}
