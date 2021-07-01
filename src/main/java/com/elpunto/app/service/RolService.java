package com.elpunto.app.service;

import java.util.List;

import com.elpunto.app.dao.IRolDao;
import com.elpunto.app.interfaceService.IRolService;
import com.elpunto.app.model.Rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService implements IRolService {

    @Autowired
    IRolDao rolDao;

    @Override
    public List<Rol> listarRoles() {
        return (List<Rol>)rolDao.findAll();
    }

    @Override
    public Rol guardarRol(Rol r) {
        return rolDao.save(r);
    }

    @Override
    public void eliminarRol(int id) {
        rolDao.deleteById(id);
    }

    @Override
    public Rol buscarRol(int id) {
        return rolDao.findById(id).orElse(null);
    }

}
