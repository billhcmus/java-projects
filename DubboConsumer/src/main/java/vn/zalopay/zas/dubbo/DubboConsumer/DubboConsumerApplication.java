package vn.zalopay.zas.dubbo.DubboConsumer;

import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.zalopay.zas.dubbo.model.transaction.*;
import vn.zalopay.zas.dubbo.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@SpringBootApplication
public class DubboConsumerApplication {

  @Reference(
      registry = "nacos://10.50.1.40:8848",
      version = "0.0.7",
      group = "zas-local",
      timeout = 3000,
      filter = "consumerFilter",
      retries = 0)
  private TransactionService transactionService;

  public static void main(String[] args) {
    SpringApplication.run(DubboConsumerApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner() {
    return transRecord();
  }

  ApplicationRunner transRecord() {
    return args -> {

      for (int i = 0; i < 5; i++) {
        List<Entry> entries = new ArrayList<>();
        entries.add(
            Entry.builder()
                .type(EntryType.DEBIT_CREDIT)
                .sourceId(2524920133255680L)
                .destinationId(16329077092843534L) // 16329077092843534L
                .amount(10000000)
                .currencyCode(704)
                .build());

        TransEnvironment env =
            TransEnvironment.builder()
                .accountingTime(System.currentTimeMillis())
                .globalTransId(System.currentTimeMillis() + "")
                .flowId("1") // Define by Accounting PO
                .build();

        TransRecordRequest request =
            TransRecordRequest.builder()
                .timestamp(System.currentTimeMillis())
                .transId(System.currentTimeMillis() + "")
                .entries(entries)
                .environment(env)
                .build();

        TransRecordResponse response = transactionService.transRecord(request);

        log.info(response.toString());
      }
    };
  }

  ApplicationRunner transQuery() {
    return args -> {
      TransQueryRequest request =
              TransQueryRequest.builder()
                      .queryByCase(TransQueryRequest.QueryByCase.TRANS_ID)
                      .transId("1587539495101")
                      .build();

      TransQueryResponse response = transactionService.transQuery(request);

      log.info(response);
    };
  }

  ApplicationRunner transRevert() {
    return args -> {
      TransEnvironment env =
          TransEnvironment.builder()
              .accountingTime(System.currentTimeMillis())
              .globalTransId("1584690983168")
              .flowId("0") // Define by Accounting PO
              .build();
      TransRevertRequest revertRequest =
          TransRevertRequest.builder()
              .revertedByCase(TransRevertRequest.RevertedByCase.TRANS_ID)
              .transId("1584690983168")
              .environment(env)
              .build();

      TransRevertResponse revertResponse = transactionService.transRevert(revertRequest);
      log.info(revertResponse);
    };
  }

  //  ApplicationRunner bankRoute() {
  //    return args -> {
  //      BankRouteRequest request =
  //          BankRouteRequest.builder()
  //              .bankConnectorCode("ZPCS")
  //              .mid("vngcorp")
  //              .channel(Channel.CHANNEL_GATEWAY)
  //              .subTransType(2101)
  //              .accountingCode("1010001001")
  //              .build();
  //
  //      BankRouteResponse response = zasBankMappingService.bankRoute(request);
  //      System.out.println(response.getData().getAccountingId());
  //    };
  //  }
}
