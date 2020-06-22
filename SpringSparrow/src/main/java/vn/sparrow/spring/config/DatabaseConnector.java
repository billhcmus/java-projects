package vn.sparrow.spring.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * Created by thuyenpt Date: 2020-05-25
 */
@Log4j2
@Getter
@Setter
public abstract class DatabaseConnector {
  protected String url;
  public abstract void connect();
}
