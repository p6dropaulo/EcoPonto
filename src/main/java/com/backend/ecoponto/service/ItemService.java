package com.backend.ecoponto.service;

import com.backend.ecoponto.model.Item;
import com.backend.ecoponto.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item salvar(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> buscarPorId(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public Item atualizar(Item item) {
        if (itemRepository.existsById(item.getId())) {
            return itemRepository.save(item);
        }
        return null;
    }

    public void deletar(Long id) {
        itemRepository.deleteById(id);
    }
}