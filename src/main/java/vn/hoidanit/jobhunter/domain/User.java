package vn.hoidanit.jobhunter.domain;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.AuthProviderEnum;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username không được để trống")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "email không được để trống")
    @Column(unique = true)
    private String email;

    private String passwordHash;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private AuthProviderEnum authProvider;

    private Instant createdAt;

    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Friendship> sentFriendRequests;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Friendship> receivedFriendRequests;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ChatMember> chatMembers;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Message> messages;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
    }
}
