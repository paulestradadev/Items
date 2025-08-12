package com.paul.items.service;

import java.util.List;

import com.paul.items.dto.Response;
import com.paul.items.entity.Item;

public interface ItemService {
	
	public Response <List<Item>> list();
	public Response<Item> create(Item item);
	public Response<Item> read(Long id);
	public Response<Item> update(Item item);
	public Response<Void> delete(Long id);
	
}
