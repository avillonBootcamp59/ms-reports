package proyecto1.msreports.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private String transactionId;
    private String type;
    private Double amount;
    private LocalDateTime transactionDate;

}
