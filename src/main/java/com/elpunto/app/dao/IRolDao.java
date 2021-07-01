package com.elpunto.app.dao;

import com.elpunto.app.model.Rol;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolDao extends JpaRepository<Rol, Integer>{
    
}
