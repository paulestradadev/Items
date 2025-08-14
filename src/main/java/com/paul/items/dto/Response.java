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
}
