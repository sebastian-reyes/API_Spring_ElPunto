package com.elpunto.app.interfaceService;

import java.util.List;

import com.elpunto.app.model.Rol;

public interface IRolService {
    public List<Rol> listarRoles();
    public Rol guardarRol(Rol r);
    public void eliminarRol(int id);
    public Rol buscarRol(int id);
}
