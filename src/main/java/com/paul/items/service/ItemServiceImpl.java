package com.paul.items.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paul.items.dto.Response;
import com.paul.items.entity.Item;
import com.paul.items.repository.ItemRepository;
import jakarta.transaction.Transactional;

@Service
public class ItemServiceImpl  implements ItemService{

	@Autowired
	ItemRepository dao;

	@Override
	public Response<List<Item>> read() {
		try {
			List<Item> itemsList = dao.findAll();
			
			if(itemsList.isEmpty()) {
				return Response.error("No se han encontrado items en la base de datos");
			}
			return Response.success(itemsList, "Items encontrados.");
		}catch(Exception e) {
			return Response.error("Error inesperado al listar items: " + e);
		}
		
	}

	@Override
	@Transactional
	public Response<Item> create(Item item) {

		try {
			if(item == null) {
				return Response.error("Error item nulo.");
			}
			
			if(item.getNombre().isBlank()) {
				return Response.error("Nombre invalido o vacio favor de ingresar un nombre.");
			}
			
			if(item.getDescripcion().isBlank()) {
				return Response.error("Descripcion vacia o invalida favor de ingresar una descripcion");
			}
			
			if(item.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
			    return Response.error("Precio inválido. Debe ser mayor a cero.");
			}
			
			if(item.getStock() <= 0) {
				return Response.error("El stock no puede ser 0 o menor a 0");
			}
			
			//Ya se autoincrementa en Entity
			item.setId(null);
			Item nuevoItem = dao.save(item);
			return Response.success(nuevoItem, "Nuevo item guardado.");
		}catch (Exception e) {
			return Response.error("Error inesperado al crear un nuevo item: " + e);
		}
		
	}

	@Override
	public Response<Item> buscar(Long id) {
		try {
			Optional<Item> itemOpc = dao.findById(id);
			
			if(!itemOpc.isPresent()) {
				return Response.error("Item no encontrado.");
			}
			Item item = itemOpc.get();
			return Response.success(item, "Item encontrado.");
		}catch (Exception e) {
			return Response.error("Error inesperado al buscar un item: " + e);
		}
		
	}

	@Override
	@Transactional
	public Response<Item> update(Item item) {
		try {
			if(item == null) {
				return Response.error("Item nulo.");
			}
			
			Item itemActualizado = dao.save(item);
			
			if(itemActualizado == null) {
				return Response.error("Error al actualizar item.");
			}
			return Response.success(itemActualizado, "Item actualizado.");

		}catch (Exception e) {
			return Response.error("Error inesperado al actuallizar un item: " + e);
		}
		
	}

	@Override
	@Transactional
	public Response<Boolean> delete(Long id) {
		try {
			if(id == null) {
				return Response.error("ID nulo.");
			}
			
			if(!dao.existsById(id)) {
				return Response.error("No se encontro un registro con ese ID: " + id);
			}
			dao.deleteById(id);
			
			if(!dao.existsById(id)) {
				return Response.success(true, "Se elimino el registro correctamente.");
			}else {
				return Response.error("No se pudo eliminar el registro con el id: " + id);
			}
		}catch(Exception e) {
			return Response.error("Eror inesperado al eliminar un item: " + e);
		}		
	}	
	
}
