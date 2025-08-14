package com.paul.items.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paul.items.dto.Response;
import com.paul.items.entity.Item;
import com.paul.items.repository.ItemRepository;

@Service
public class ItemServiceImpl  implements ItemService{

	@Autowired
	ItemRepository dao;

	@Override
	public Response<List<Item>> read() {
		Response<List<Item>> rs = new Response<List<Item>>();
		List<Item> itemsList = dao.findAll();
		
		if(itemsList.isEmpty()) {
			rs.setSuccess(false);	
			rs.setMessage("No se han encontrado items en la base de datos");
			rs.setData(itemsList);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		
		rs.setSuccess(true);
		rs.setMessage("Items encontrados: ");
		rs.setData(itemsList);
		rs.setTimestamp(LocalDateTime.now());
		return rs;
	}

	@Override
	public Response<Item> create(Item item) {
		Response<Item> rs = new Response<Item>();
		if(item == null) {
			rs.setSuccess(false);
			rs.setMessage("Error item a crear nulo.");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		
		if(item.getNombre().isBlank()) {
			rs.setSuccess(false);
			rs.setMessage("Nombre invalido o vacio favor de ingresar un nombre.");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		
		if(item.getDescripcion().isBlank()) {
			rs.setSuccess(false);
			rs.setMessage("Descripcion vacia o invalida favor de ingresar una descripcion");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		
		if(item.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
			rs.setSuccess(false);
		    rs.setMessage("Precio inválido. Debe ser mayor a cero.");
		    rs.setData(null);
		    rs.setTimestamp(LocalDateTime.now());
		    return rs;
		}
		
		if(item.getStock() <= 0) {
			rs.setSuccess(false);
			rs.setMessage("El stock no puede ser 0 o menor a 0");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		
		//Ya se autoincrementa en Entity
		item.setId(null);
		Item nuevoItem = dao.save(item);
		rs.setSuccess(true);
		rs.setMessage("Nuevo item guardado: " + item);
		rs.setData(nuevoItem);
		rs.setTimestamp(LocalDateTime.now());
		return rs;
	}

	@Override
	public Response<Item> buscar(Long id) {
		
		Response<Item> rs = new Response<>();
		
		Optional<Item> itemOpc = dao.findById(id);
		
		if(itemOpc.isPresent()) {
			Item item = itemOpc.get();
			rs.setSuccess(true);
			rs.setMessage("Item encontrado.");
			rs.setData(item);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		rs.setSuccess(false);
		rs.setMessage("Item no encontrado.");
		rs.setData(null);
		rs.setTimestamp(LocalDateTime.now());
		return rs;
	}

	@Override
	public Response<Item> update(Item item) {
		Response<Item> rs = new Response<Item>();
		
		if(item == null) {
			rs.setSuccess(false);
			rs.setMessage("Item nulo.");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		
		Item itemActualizado = dao.save(item);
		
		if(itemActualizado == null) {
			rs.setSuccess(false);
			rs.setMessage("Error al actualizar item.");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		rs.setSuccess(true);
		rs.setMessage("Item actualizado.");
		rs.setData(itemActualizado);
		rs.setTimestamp(LocalDateTime.now());
		return rs;

	}

	@Override
	public Response<Void> delete(Long id) {
		
		Response<Void> rs = new Response<>();
		
		if(id == null) {
			rs.setSuccess(false);
			rs.setMessage("ID nulo.");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		
		if(!dao.existsById(id)) {
			rs.setSuccess(false);
			rs.setMessage("No se encontro un registro con ese ID.");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		dao.deleteById(id);
		
		if(!dao.existsById(id)) {
			rs.setSuccess(true);
			rs.setMessage("Se elimino el registro correctamente");
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}else {
			rs.setSuccess(false);
			rs.setMessage("No se pudo eliminar el registro con el id: " + id);
			rs.setData(null);
			rs.setTimestamp(LocalDateTime.now());
			return rs;
		}
		
	}
	

}
