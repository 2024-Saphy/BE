package saphy.saphy.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import saphy.saphy.item.domain.enumeration.Brand;
import saphy.saphy.item.domain.enumeration.Color;
import saphy.saphy.item.domain.enumeration.Grade;
import saphy.saphy.item.domain.enumeration.Storage;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("TABLET")
@PrimaryKeyJoinColumn(name = "tablet_id")
@Table(name = "tablets")
public class Tablet extends Item {
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
    @Column(name = "grade", nullable = false)
    private Grade grade;
}
