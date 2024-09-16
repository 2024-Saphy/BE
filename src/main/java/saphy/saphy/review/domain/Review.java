package saphy.saphy.review.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.image.domain.ReviewImage;
import saphy.saphy.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Star star;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImage> images = new ArrayList<>();
}