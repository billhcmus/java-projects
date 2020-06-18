package vn.sparrow.vertx.common.server;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

/** Created by thuyenpt Date: 2020-06-07 */
public class SparrowServerInterceptor implements ServerInterceptor {
  private static final Logger LOGGER =
      LogManager.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> serverCall,
      Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {
    // Do something here
    String fullMethodName = serverCall.getMethodDescriptor().getFullMethodName();
    LOGGER.info("Intercept request... {}", fullMethodName);
    return serverCallHandler.startCall(serverCall, metadata);
  }
}
