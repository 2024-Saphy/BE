// package saphy.saphy.item.domain;
//
// import jakarta.persistence.Column;
// import jakarta.persistence.DiscriminatorValue;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.experimental.SuperBuilder;
// import saphy.saphy.global.entity.BaseEntity;
// import saphy.saphy.item.domain.enumeration.Color;
// import saphy.saphy.item.domain.enumeration.Storage;
//
// @Entity
// @Getter
// @SuperBuilder
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @DiscriminatorValue("PHONE")
// @Table(name = "phones")
// public class Phone extends Item {
//
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "phone_id")
//     private Long id;
//
//     @Column(name = "name", nullable = false)
//     private String name;
//
//     @Column(name = "brand", nullable = false)
//     private String brand;
//
//     @Column(name = "screen_size", nullable = false)
//     private String screenSize;
//
//     @Enumerated(EnumType.STRING)
//     @Column(name = "color", nullable = false)
//     private Color color;
//
//     @Enumerated(EnumType.STRING)
//     @Column(name = "storage", nullable = false)
//     private Storage storage;
//
//     @Column(name = "grade", nullable = false)
//     private String grade;
// }
