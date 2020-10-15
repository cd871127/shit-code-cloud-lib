package shit.code.cloud.spring.response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author anthonychen
 * @date 2020/10/15
 **/
@Slf4j
public class CommonHttpResponse extends ShitCodeHttpResponse<Object> {
    public CommonHttpResponse() {
        super();
    }

    public CommonHttpResponse(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public CommonHttpResponse(Object data) {
        super(data);
    }
}
