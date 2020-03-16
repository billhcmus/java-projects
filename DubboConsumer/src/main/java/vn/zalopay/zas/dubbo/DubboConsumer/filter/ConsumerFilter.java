package vn.zalopay.zas.dubbo.DubboConsumer.filter;

import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.rpc.*;

/** Created by thuyenpt Date: 2020-02-17 */
public class ConsumerFilter implements Filter {
  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    RpcContext.getContext()
        .setInvoker(invoker)
        .setInvocation(invocation)
        .setLocalAddress(NetUtils.getLocalHost(), 0)
        .setRemoteAddress(invoker.getUrl().getHost(), invoker.getUrl().getPort())
        .setAttachment("Authorization", "Bearer FA_BACKOFFICE_TOKEN");
    if (invocation instanceof RpcInvocation) {
      ((RpcInvocation) invocation).setInvoker(invoker);
    }
    Result result;
    try {
      result = invoker.invoke(invocation);
    } finally {
      RpcContext.getContext().clearAttachments();
    }

    return result;
  }
}
