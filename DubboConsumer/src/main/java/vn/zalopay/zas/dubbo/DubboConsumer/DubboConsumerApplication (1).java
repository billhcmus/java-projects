package vn.zalopay.zas.dubbo.DubboConsumer;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.zalopay.zas.dubbo.model.transaction.*;
import vn.zalopay.zas.dubbo.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
public class DubboConsumerApplication {

  @Reference(version = "0.0.3", group = "zas-dev", filter = "consumerFilter")
  private TransactionService transactionService;

  public static void main(String[] args) {
    SpringApplication.run(DubboConsumerApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner() {
    return transQuery();
  }

  ApplicationRunner transQuery() {
    return args -> {
      TransQueryRequest request =
          TransQueryRequest.builder()
              .queryByCase(TransQueryRequest.QueryByCase.TRANS_ID)
              .transId("1584094281248")
              .build();

      TransQueryResponse response = transactionService.transQuery(request);

      System.out.println(response.toString());
    };
  }

  ApplicationRunner transRecord() {
    return args -> {
      List<Entry> entries = new ArrayList<>();
      entries.add(
          Entry.builder()
              .type(EntryType.DEBIT_CREDIT)
              .sourceId(16329077721989135L)
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

      System.out.println(response.toString());
    };
  }
}
