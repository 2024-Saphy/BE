package saphy.saphy.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import saphy.saphy.item.domain.enumeration.Brand;
import saphy.saphy.item.domain.enumeration.Color;
import saphy.saphy.item.domain.enumeration.Grade;
import saphy.saphy.item.domain.enumeration.Graphics;
import saphy.saphy.item.domain.enumeration.Memory;
import saphy.saphy.item.domain.enumeration.Processor;
import saphy.saphy.item.domain.enumeration.Storage;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("LAPTOP")
@Table(name = "labtops")
public class Laptop extends Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laptop_id")
    private Long id;

    @Column(insertable = false, updatable = false)
    private String deviceType;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    private Color color;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage", nullable = false)
    private Storage storage;

    @Enumerated(EnumType.STRING)
    @Column(name = "processor", nullable = false)
    private Processor processor;

    @Enumerated(EnumType.STRING)
    @Column(name = "memory", nullable = false)
    private Memory memory;

    @Enumerated(EnumType.STRING)
    @Column(name = "graphics", nullable = false)
    private Graphics graphics;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private Grade grade;
}
