package com.fabricaescuela.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import com.fabricaescuela.models.entity.Usuario;
import com.fabricaescuela.models.entity.Role;

@Getter
@Setter
@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado", nullable = false)
    private Integer idEmpleado;

    @Size(max = 30)
    @Column(name = "tipo_documento", length = 30)
    private String tipoDocumento;

    @Size(max = 20)
    @Column(name = "numero_documento", length = 20)
    private String numeroDocumento;

    @Size(max = 100)
    @Column(name = "nombre_empleado", length = 100)
    private String nombreEmpleado;

    @Size(max = 100)
    @Column(name = "correo", length = 100)
    private String correo;

    @Size(max = 15)
    @Column(name = "telefono", length = 15)
    private String telefono;

    // Relación con usuario (opcional)
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Relación con rol (opcional)
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Role rol;

    // Compatibility methods (mantener API anterior getId/setId)
    public Integer getId() {
        return this.idEmpleado;
    }

    public void setId(Integer id) {
        this.idEmpleado = id;
    }
}