package saphy.saphy.repairCenter.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.repairCenter.domain.RepairCenter;
import saphy.saphy.member.domain.Member;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "repair_center_bookmark")
public class RepairCenterBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_center_id")
    private RepairCenter repairCenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
