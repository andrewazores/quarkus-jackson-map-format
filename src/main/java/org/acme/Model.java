package org.acme;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Model extends PanacheEntity {

    @Column(unique = false, nullable = false, updatable = false)
    @NotBlank
    public String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @NotNull
    public Map<String, String> labels = Map.of("hello", "world");

}
