package stu.napls.boostimnode.core.dictionary;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
    int SUCCESS = HttpStatus.OK.value();
    int FAILURE = -1;
}
