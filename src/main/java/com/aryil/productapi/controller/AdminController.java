package com.aryil.productapi.controller;

import com.aryil.productapi.dto.RoleDTO;
import com.aryil.productapi.entity.Permission;
import com.aryil.productapi.entity.Role;
import com.aryil.productapi.entity.User;
import com.aryil.productapi.service.PermissionService;
import com.aryil.productapi.service.RoleService;
import com.aryil.productapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @PostMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO dto) {
        Role role = roleService.createRole(dto.getName());
        return ResponseEntity.ok(new RoleDTO(role.getId(), role.getName()));
    }

    @PostMapping("/users/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> assignRoleToUser(
            @PathVariable UUID userId,
            @PathVariable UUID roleId) {
        return ResponseEntity.ok(userService.assignRoleToUser(userId, roleId));
    }

    @PostMapping("/roles/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> assignPermissionToRole(
            @PathVariable UUID roleId,
            @PathVariable UUID permissionId) {
        return ResponseEntity.ok(roleService.assignPermissionToRole(roleId, permissionId));
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }
}
