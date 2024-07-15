package saphy.saphy.item.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saphy.saphy.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "device")
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Long capacity;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String grade;

    @OneToOne(mappedBy = "device", fetch = FetchType.LAZY)
    private Item item;
}
