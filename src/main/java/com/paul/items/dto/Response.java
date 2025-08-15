package com.paul.items.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timestamp;
	
	public static <T> Response<T> success(T data, String message) {
		Response<T> rs = new Response<>();
		rs.setSuccess(true);
		rs.setMessage(message);
		rs.setData(data);
		rs.setTimestamp(LocalDateTime.now());
		return rs;
	}
	
	public static <T> Response<T> error(String message){
		Response<T> rs = new Response<>();
		rs.setSuccess(false);
		rs.setMessage(message);
		rs.setData(null);
		rs.setTimestamp(LocalDateTime.now());
		return rs;
	}
}
