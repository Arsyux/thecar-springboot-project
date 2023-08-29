package com.arsyux.thecar.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {

	// 비즈니스 컴포넌트를 작성하는 순서는 엔티티 -> 리포지터리 -> 서비스 순이다.
	// 이중에서 엔티티를 작성할 때 연관 매핑에 특히 신경을 써야한다.
	// 일반적으로 하나의 포스트를 검색하면 해당 포스트를 작성한 회원(User) 정보나 포스트에 등록된 댓글(Reply)도 같이 조회되어야한다.
	// 그래야 포스트에 대한 상세 정보를 구상할 수 있기 때문이다.
	// 이렇게 특정 엔티티가 다른 엔티티와 관계를 맺고 있는 경우 JPA 연관 매핑을 사용한다.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	private int cnt;
	
	// Post 클래스에서 가장 중요한 변수는 user이며 User 엔티티와의 연관 매핑을 담당한다.
	// JPA는 연관 매핑을 위해 다음과 같은 다양한 어노테이션을 제공한다.
	
	// 어노테이션      다중성(multiplicity)
	// OneToOne     1:1
	// ManyToOne    N:1
	// OneToMany    1:N
	// ManyToMany   N:M
	
	// Post 관점에서 User와의 관계는 N:1이기 때문에 User 타입의 user 변수에 @ManyToOne을 설정한다.
	
	// N:1 관계에서는 @ManyToOne의 fetch속성을 이용하여 연관 객체의 조회 시점을 결정할 수 있다.
	// FetchType.EAGER는 Post 엔티티를 검색할 때 연관된 User 엔티티를 JOIN을 통해 즉시(EAGER) 조회한다.
	// 반면, FetchType.LAZY는 처음부터 JOIN을 사용하는 것이 아니라 실제 User 엔티티를 사용하는 시점에 늦게(LAZY) 조회한다.
	
	// POST 테이블과 USERS 테이블이 연관되기 위해서는 연관 관계가 매핑된 Post 엔티티에서 @JoinColumn을 이용하여 외래키로 사용할 컬럼 이름을 지정해야한다.
	// 이때, @JoinColumn의 name 속성을 사용한다.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private User user;
	
}