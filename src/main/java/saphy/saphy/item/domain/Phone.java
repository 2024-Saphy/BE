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
@DiscriminatorValue("PHONE")
@PrimaryKeyJoinColumn(name = "phone_id")
@Table(name = "phones")
public class Phone extends Item {
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

    public void update(Phone updatedPhone) {
        super.update(updatedPhone);
        this.brand = updatedPhone.getBrand();
        this.color = updatedPhone.getColor();
        this.storage = updatedPhone.getStorage();
        this.grade = updatedPhone.getGrade();
    }
}
