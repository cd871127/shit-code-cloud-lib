package shit.code.cloud.spring.response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author anthonychen
 * @date 2020/10/15
 **/
@Slf4j
public class StringHttpResponse extends ShitCodeHttpResponse<String> {
    public StringHttpResponse() {
        super();
    }

    public StringHttpResponse(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public StringHttpResponse(String data) {
        super(data);
    }
}
