```mermaid
classDiagram
    direction LR

    class User {
        - Long id
        - String username
        - String email
        - String password
        - Role role
        - boolean enabled
        - LocalDateTime createdAt
        - Set~Site~ sites
        + getAuthorities() Collection~? extends GrantedAuthority~
        + isAccountNonExpired() boolean
        + isAccountNonLocked() boolean
        + isCredentialsNonExpired() boolean
        + isEnabled() boolean
        # onCreate() void
    }

    class Site {
        - Long id
        - String siteHost
        - Integer unique_views
        - boolean verified
        - Set~User~ users
    }

    class Analytics {
        - Long id
        - Site site
        - String visitorId
        - String path
        - String referrer
        - String eventName
        - String description
        - String browser
        - String os
        - String device
        - Long durationSeconds
        - LocalDateTime createdAt
        - LocalDateTime updatedAt
    }

    class Role {
        <<enumeration>>
        USER
        ADMIN
    }

    %% Relationships and Cardinalities
    User "0..*" <--> "0..*" Site : "user_sites (Join Table)"
    User --> "1" Role : "has"
    Analytics "0..*" --> "1" Site : "belongs to (site_id)"