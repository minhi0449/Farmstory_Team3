package com.farmstory.entity;

import com.farmstory.enums.PayMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo;

    private int point;
    @Column(length = 45)
    private String receiver;
    @Column(columnDefinition = "char(13)")
    private String receiverHp;
    @Column(length = 20)
    private String zip;
    @Column(length = 100)
    private String addr1;
    @Column(length = 200)
    private String addr2;

    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Column(columnDefinition = "TEXT")
    private String etc;

    @CreationTimestamp
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


}
