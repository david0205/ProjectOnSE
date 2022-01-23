package com.gearz.common.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_tracking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTracking extends BaseEntity {

    @Column(length = 100)
    private String statusDetail;

    private Date updatedTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Transient
    public String getUpdatedTimeOnForm() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        return formatter.format(this.updatedTime);
    }

    public void setUpdatedTimeOnForm(String date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

        try {
            this.updatedTime = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
