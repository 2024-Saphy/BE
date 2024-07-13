package saphy.saphy.repairCenter.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saphy.saphy.global.BaseEntity;
import saphy.saphy.global.common.Star;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "repair_center")
public class RepairCenter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Star star;

    @Column(name = "opening_time")
    private String openingTime;  // "09:00"과 같은 형식으로 저장

    @Column(name = "closing_time")
    private String closingTime;

}
