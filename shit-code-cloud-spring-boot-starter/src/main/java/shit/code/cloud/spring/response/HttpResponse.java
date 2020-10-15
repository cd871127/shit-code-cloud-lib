package shit.code.cloud.spring.response;

/**
 * @author anthonychen
 * @date 2020/10/15
 **/
public interface HttpResponse {
    /**
     * 0 请求成功
     *
     * @return
     */
    int getCode();

    /**
     * 失败才有这个消息
     *
     * @return
     */
    String getMsg();
}
