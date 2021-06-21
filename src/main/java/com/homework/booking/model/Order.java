package com.homework.booking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_table")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String name;
    private String surName;
    private String email;
    private String cellNumber;

    private PhotoType photoType;

    private String title;
    @Lob
    private String logisticInfo;
    @Basic
    private LocalDateTime dateTime;

    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "photographer_id")
    private Photographer photographer;

    private String photos;

}
