package ru.nikita.labs.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "code")
@Builder
@Entity
@Table(name = "permissions")
@FilterDef(name = "activePermissionFilter")
@Filter(name = "activePermissionFilter", condition = "deleted_at IS NULL")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<RolePermission> rolePermissions = new ArrayList<>();

    public void addRolePermission(RolePermission rolePermission) {
        rolePermissions.add(rolePermission);
        rolePermission.setPermission(this);
    }
}
