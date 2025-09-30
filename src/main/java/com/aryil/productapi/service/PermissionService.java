package com.aryil.productapi.service;

import com.aryil.productapi.entity.Permission;
import com.aryil.productapi.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @PostMapping("/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public Permission createPermission(String name) {
        Permission permission = new Permission();
        permission.setName(name);
        return permissionRepository.save(permission);
    }

    @GetMapping("/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> getPermissionByName(String name) {
        return permissionRepository.findByName(name);
    }
}
