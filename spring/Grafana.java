package com.example.demo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Grafana {
    private @Id
    @GeneratedValue Long id;
    private int replicas;
    private int sampleDashboards;
    @Column(length = 32768)
    private String dashboardName;
    private int consoles;

    Grafana() {}

    Grafana(int replicas, int sampleDashboards, String dashboardName, int consoles) {
        this.replicas = replicas;
        this.sampleDashboards = sampleDashboards;
        this.dashboardName = dashboardName;
        this.consoles = consoles;
    }

    public Long getId() {
        return this.id;
    }

    public int getReplicas() {
        return this.replicas;
    }

    public int getSampleDashboards() {
        return this.sampleDashboards;
    }

    public String getDashboardName() {
        return this.dashboardName;
    }

    public int getConsoles() {
        return this.consoles;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public void setSampleDashboards(int sampleDashboards) {
        this.sampleDashboards = sampleDashboards;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public void setConsoles(int consoles) {
        this.consoles = consoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Grafana))
            return false;
        Grafana grafana = (Grafana) o;
        return Objects.equals(this.id, grafana.id) && Objects.equals(this.replicas, grafana.replicas)
            && Objects.equals(this.sampleDashboards, grafana.sampleDashboards) && Objects.equals(this.dashboardName, grafana.dashboardName)
            && Objects.equals(this.consoles, grafana.consoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.replicas, this.sampleDashboards, this.dashboardName, this.consoles);
    }

    @Override
    public String toString() {
        return "Grafana{" + "id=" + this.id + ", replicas='" + this.replicas + '\'' + ", sampleDashboards='" + this.sampleDashboards + '\'' + 
        ", dashboardName='" + this.dashboardName + '\'' + ", consoles='" + this.consoles + '\'' + '}';
    }
}
