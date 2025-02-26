package proyecto1.msreports.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import proyecto1.msreports.client.AccountClient;
import proyecto1.msreports.client.CreditClient;
import proyecto1.msreports.dto.AccountDTO;
import proyecto1.msreports.dto.BankProductReportDTO;
import proyecto1.msreports.dto.CreditDTO;
import proyecto1.msreports.dto.TransactionDTO;
import proyecto1.msreports.entity.Transaction;
import proyecto1.msreports.repository.TransactionRepository;
import proyecto1.msreports.service.ReportService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final WebClient.Builder webClientBuilder;

    public Flux<BankProductReportDTO> generateBankProductReport(LocalDateTime startDate, LocalDateTime endDate) {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8083").build();

        Flux<TransactionDTO> transactions = webClient.get()
                .uri("/v1.0/transactions/report?startDate=" + startDate + "&endDate=" + endDate)
                .retrieve()
                .bodyToFlux(TransactionDTO.class);

        WebClient accountClient = webClientBuilder.baseUrl("http://localhost:8082").build();
        Flux<BankProductReportDTO> accounts = accountClient.get()
                .uri("/v1.0/accounts")
                .retrieve()
                .bodyToFlux(BankProductReportDTO.class);

        WebClient creditClient = webClientBuilder.baseUrl("http://localhost:8083").build();
        Flux<BankProductReportDTO> credits = creditClient.get()
                .uri("/v1.0/credits")
                .retrieve()
                .bodyToFlux(BankProductReportDTO.class);

        return Flux.merge(accounts, credits)
                .flatMap(product -> transactions
                        .filter(tx -> tx.getTransactionDate().isAfter(startDate) && tx.getTransactionDate().isBefore(endDate))
                        .collectList()
                        .map(txList -> {
                            product.setTransactions(txList);
                            return product;
                        })
                );
    }


}