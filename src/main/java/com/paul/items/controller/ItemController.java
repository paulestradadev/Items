package com.paul.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paul.items.dto.Response;
import com.paul.items.entity.Item;
import com.paul.items.service.ItemServiceImpl;

@RestController
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	ItemServiceImpl service;
	
	@GetMapping("listar")
	public ResponseEntity<Response<List<Item>>> listar(){
		
		Response<List<Item>> rs = service.list();
		if(rs.isSuccess()) {
			return ResponseEntity.ok(rs);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
	}
	
	@PostMapping("crear")
	public ResponseEntity<Response<Item>> crear(@RequestBody Item item){
		Response<Item> rs = service.create(item);
		if(rs.isSuccess()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(rs);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
	}
	
}
