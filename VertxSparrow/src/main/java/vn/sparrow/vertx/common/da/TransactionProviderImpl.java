package vn.sparrow.vertx.common.da;

import io.vertx.ext.sql.SQLClient;

/** Created by thuyenpt Date: 2019-11-24 */
public class TransactionProviderImpl implements TransactionProvider {
  private final SQLClient sqlClient;

  public TransactionProviderImpl(SQLClient sqlClient) {
    this.sqlClient = sqlClient;
  }

  @Override
  public Transaction newTransaction() {
    return new TransactionImpl(sqlClient);
  }
}
