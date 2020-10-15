package shit.code.cloud.spring.response;

import lombok.experimental.Accessors;

/**
 * @author anthonychen
 * @date 2020/10/15
 **/
public interface HttpResponse {
    int getCode();

    String getMsg();
}
