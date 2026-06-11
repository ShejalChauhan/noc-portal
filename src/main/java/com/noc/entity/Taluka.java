package com.noc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "taluka")
public class Taluka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String taluka;

    @Column(nullable = false)
    private String village;

    @Column(nullable = false)
    private String status;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTaluka() { return taluka; }
    public void setTaluka(String taluka) { this.taluka = taluka; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
