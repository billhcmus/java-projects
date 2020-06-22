package vn.zalopay.hack.reflection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/** Created by thuyenpt Date: 2020-03-30 */
public class Runner {
  private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

  public static void main(String[] args) throws ClassNotFoundException {
    getClassInfo();
  }

  private static void getClassInfo() throws ClassNotFoundException {
    Class<?> aClazz = Class.forName("vn.zalopay.hack.reflection.Cat");
    LOGGER.info("Class name: ");
    LOGGER.info(aClazz.getName());
    LOGGER.info("Simple name: {}", aClazz.getSimpleName());

    Package pkg = aClazz.getPackage();
    LOGGER.info("Package name: {}", pkg.getName());

    Class<?> superclass = aClazz.getSuperclass();
    LOGGER.info("Supper class name: {}", superclass.getName());

    LOGGER.info("Declared field: ");
    Field[] declaredFields = aClazz.getDeclaredFields();
    for (Field field : declaredFields) {
      LOGGER.info("{}", field.getName());
    }

    Method[] declaredMethods = aClazz.getDeclaredMethods();
    for (Method method : declaredMethods) {
      LOGGER.info("Method name: {}", method.getName());
    }
  }
}
