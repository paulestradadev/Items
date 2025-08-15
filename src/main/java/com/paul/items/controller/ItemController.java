package com.paul.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.paul.items.dto.Response;
import com.paul.items.entity.Item;
import com.paul.items.service.ItemServiceImpl;

@RestController
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	ItemServiceImpl service;
	
	//https://localhost/items/listar
	@GetMapping("listar")
	public ResponseEntity<Response<List<Item>>> listar(){
		
		Response<List<Item>> rs = service.read();
		if(rs.isSuccess()) {
			return ResponseEntity.ok(rs);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
	}
	
	//https://localhost/items/crear
	@PostMapping("crear")
	public ResponseEntity<Response<Item>> crear(@RequestBody Item item){
		Response<Item> rs = service.create(item);
		if(rs.isSuccess()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(rs);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
	}
	
	//https://localhost/items/buscar/id
	@GetMapping("buscar/{id}")
	public ResponseEntity<Response<Item>> buscar(@PathVariable Long id) {
		Response<Item> rs = service.buscar(id);
		if(rs.isSuccess()) {
			return ResponseEntity.status(HttpStatus.OK).body(rs);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
	}
	
	//https://localhost/items/eliminar/id
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<Response<Boolean>> delete(@PathVariable Long id) {
		Response<Boolean> rs = service.delete(id);
		if(rs.isSuccess()) {
			return ResponseEntity.ok(rs);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
		
	}
}
