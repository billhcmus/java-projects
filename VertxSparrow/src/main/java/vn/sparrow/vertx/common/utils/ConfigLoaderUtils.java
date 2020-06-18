package vn.sparrow.vertx.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import vn.sparrow.vertx.common.exception.InvalidParameterException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

/** Created by thuyenpt Date: 2019-11-15 */
public class ConfigLoaderUtils {
  private static final Logger LOGGER =
      LogManager.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  private ConfigLoaderUtils() {}

  public static <T> T load(Class<T> tClass, String property)
      throws FileNotFoundException, InvalidParameterException {
    String fileName = System.getProperty(property);
    if (fileName == null) {
      throw new InvalidParameterException("Not found server config file.");
    }
    InputStream input = new FileInputStream(fileName);
    Yaml yaml = new Yaml(new Constructor(tClass));
    return yaml.load(input);
  }

  public static <T> void printValue(T configObject) throws InvalidParameterException {
    Field[] declaredFields = configObject.getClass().getDeclaredFields();
    LOGGER.info("{}", configObject.getClass().getSimpleName());
    for (Field field : declaredFields) {
      field.setAccessible(true);
      try {
        String valueConfig = JsonProtoUtils.printGson(field.get(configObject));
        LOGGER.info("     > {} > {}", field.getName(), valueConfig);
      } catch (IllegalAccessException e) {
        throw new InvalidParameterException(e);
      }
    }
  }
}
