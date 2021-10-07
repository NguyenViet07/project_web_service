package com.example.music.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {
  private String errorCode;
  private String message;
  private Object data;

  public ResponseResult(ResponseCode responseCode) {
    this.errorCode = responseCode.getErrorCode();
    this.message = responseCode.getMessage();
  }

  public static ResponseResult success(Object data) {
    ResponseResult resp = new ResponseResult();
    resp.errorCode = ResponseCode.SUCCESS.getErrorCode();
    resp.message = ResponseCode.SUCCESS.getMessage();
    resp.setData(data);
    return resp;
  }

}
