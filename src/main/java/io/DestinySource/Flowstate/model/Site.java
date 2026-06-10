    package io.DestinySource.Flowstate.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Table(name = "site")
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public class Site {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "site_host", nullable = false)
        private String siteHost;

        @Column(name = "unique_views")
        private Integer unique_views;

        @Column(name = "verified")
        private boolean verified;

        @ManyToMany(mappedBy = "sites", fetch = FetchType.LAZY)
        @JsonIgnore
        private Set<User> users = new HashSet<>();
    }
