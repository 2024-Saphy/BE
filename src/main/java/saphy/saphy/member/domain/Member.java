package saphy.saphy.member.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import saphy.saphy.global.entity.BaseEntity;
import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.image.domain.ProfileImage;
import saphy.saphy.itemWish.domain.ItemWish;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column
    private String name;

    @Column
    private String nickName;

    @Column
    @Embedded
    private Address address;

    @Column
    @Embedded
    private Account account;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAdmin = Boolean.FALSE;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemWish> itemWishes = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    /**
     * 주소
     */
    public void addAddress(Address address) {
        if (this.address != null) {
            throw SaphyException.from(ErrorCode.ADDRESS_ALREADY_EXIST);
        }
        this.address = address;
    }

    public void updateAddress(Address address) {
        if (this.address.equals(address)) {
            throw SaphyException.from(ErrorCode.DUPLICATE_ADDRESS);
        }
        this.address = address;
    }

    public void removeAddress() {
        if (this.address == null) {
            throw SaphyException.from(ErrorCode.ADDRESS_NOT_FOUND);
        }
        this.address = null;
    }
}
