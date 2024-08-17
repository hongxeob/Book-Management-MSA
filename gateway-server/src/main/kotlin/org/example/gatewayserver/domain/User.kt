package org.example.gatewayserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable

@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class User :
    AbstractAuditingEntity<Any>(),
    Serializable {
    // 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    // 로그인 아이디
    @NotNull
    @Pattern(regexp = "^[_.@A-Za-z0-9-]*$")
    @Size(min = 1, max = 50)
    @Column(name = "login_id", unique = true, nullable = false)
    var loginId: String = ""
        get() = loginId
        protected set

    // 패스워드
    @JsonIgnore
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "password_hash", nullable = false, length = 60)
    var password: String = ""
        get() = password
        protected set

    // 이름
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    var firstName: String = ""
        get() = firstName
        protected set

    // 이름
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    var lastName: String = ""
        get() = lastName
        protected set

    // 이메일
    @Size(min = 5, max = 254)
    @Email
    @Column(length = 254, unique = true)
    var email: String = ""
        get() = email
        protected set

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "user_authority",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "name")],
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    protected val mutableAuthority: MutableSet<Authority> = mutableSetOf()
    val authorities: Set<Authority> get() = mutableAuthority.toSet()

    override fun getId(): Long = this.id

    fun updateUserinfo()  {
        // todo
    }
}
