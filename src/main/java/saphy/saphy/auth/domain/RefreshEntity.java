package saphy.saphy.auth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshEntity {

    @Id
    @Column(name = "refresh_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refresh;
    private String loginId;

    public RefreshEntity(String refresh, String loginId) {
        this.refresh = refresh;
        this.loginId = loginId;
    }
}