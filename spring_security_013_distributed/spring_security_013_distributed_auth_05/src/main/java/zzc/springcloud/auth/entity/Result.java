package zzc.springcloud.auth.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

	private boolean flag; // 是否成功
	private Integer code; // 返回码
	private String message; // 返回信息
	private String transactionId; // 请求事务Id
	private Object data; // 返回数据

	public Result() {
	}

	public static Result success(Object object) {
		Result result = new Result();
		result.setFlag(true);
		result.setCode(StatusCode.OK);
		result.setMessage("处理成功");
		result.setData(object);
		return result;
	}
	
	public static Result success() {
		return success(null);
	}
	
	public static Result error(Integer code, String message) {
		Result result = new Result();
		result.setFlag(true);
		result.setCode(code);
		result.setMessage(message);
		return result;
	}
	
}
