package com.univaq.project.auleweb.data.dao;

import com.univaq.project.auleweb.data.model.Categoria;
import com.univaq.project.framework.data.DataException;
import java.util.List;

public interface CategorieDAO {

    List<Categoria> getAllCategorie() throws DataException;

    Categoria getCategoriaById(int id) throws DataException;

    Categoria importCategoria();

    Integer insertCategoria(String nome) throws DataException;
    
    void deleteCategoria(int categoriaId, long versione) throws DataException;
}
