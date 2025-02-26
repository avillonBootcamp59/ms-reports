package proyecto1.msreports.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BankProductReportDTO {
    private String productId;
    private String productType; // ACCOUNT o CREDIT
    private String customerId;
    private String customerName;
    private Double balanceOrDebt;
    private LocalDateTime createdAt;
    private List<TransactionDTO> transactions;

}
