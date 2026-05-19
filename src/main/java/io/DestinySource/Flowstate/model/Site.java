    package io.DestinySource.Flowstate.model;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

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
    }
