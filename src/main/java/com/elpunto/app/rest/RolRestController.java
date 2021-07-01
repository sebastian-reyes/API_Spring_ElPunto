package com.elpunto.app.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elpunto.app.interfaceService.IRolService;
import com.elpunto.app.model.Rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/roles")
public class RolRestController {
    @Autowired
    IRolService rolService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> listarRoles(){
        List<Rol> listaRoles = rolService.listarRoles();
        Map<String, Object> response = new HashMap<>();
        response.put("roles", listaRoles);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
}
