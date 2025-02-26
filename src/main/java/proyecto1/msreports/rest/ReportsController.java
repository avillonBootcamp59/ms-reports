package proyecto1.msreports.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import proyecto1.msreports.dto.BankProductReportDTO;
import proyecto1.msreports.service.ReportService;
import proyecto1.msreports.service.impl.ReportServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import proyecto1.msreports.entity.Transaction;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/v1.0/reports")
@RequiredArgsConstructor
@Tag(name = "Report API", description = "Gestión de reportes bancarias")
public class ReportsController {

    private final ReportServiceImpl reportService;

    @Operation(summary = "Generar reporte de productos bancarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/bank-products")
    public Flux<BankProductReportDTO> generateReport(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return reportService.generateBankProductReport(startDate, endDate);
    }


}
