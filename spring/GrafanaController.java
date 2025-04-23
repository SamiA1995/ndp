package com.example.demo;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;

@CrossOrigin(origins = "*")
@RestController
class GrafanaController {

  private final GrafanaRepository repository;

  GrafanaController(GrafanaRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/grafana")
  List<Grafana> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/grafana")
  Grafana newGrafana(@RequestBody Grafana newGrafana) {
    return repository.save(newGrafana);
  }

  @PostMapping("/k8sDeployment")
  Grafana k8sDeployment(@RequestBody Grafana newGrafana) throws IOException, ApiException {
    String k8sDeployment = DemoApplication.createDeployment(newGrafana.getReplicas());
    newGrafana.setDashboardName(k8sDeployment);
    return repository.save(newGrafana);
  }

  @PostMapping("/grafanaDeployment")
  Grafana grafanaDeployment(@RequestBody Grafana newGrafana) throws IOException, ApiException {
    String grafanaLink = DemoApplication.createGrafana(newGrafana.getDashboardName(), newGrafana.getSampleDashboards(), newGrafana.getConsoles());
    newGrafana.setDashboardName(grafanaLink);
    return repository.save(newGrafana);
  }

  // Single item
  
  @GetMapping("/grafana/{id}")
  Grafana one(@PathVariable Long id) {
    
    return repository.findById(id)
      .orElseThrow(() -> new GrafanaNotFoundException(id));
  }

  @PutMapping("/grafana/{id}")
  Grafana replaceGrafana(@RequestBody Grafana newGrafana, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(grafana -> {
        grafana.setReplicas(newGrafana.getReplicas());
        grafana.setSampleDashboards(newGrafana.getSampleDashboards());
        grafana.setDashboardName(newGrafana.getDashboardName());
        grafana.setConsoles(newGrafana.getConsoles());
        return repository.save(grafana);
      })
      .orElseGet(() -> {
        return repository.save(newGrafana);
      });
  }

  @DeleteMapping("/grafana/{id}")
  void deleteGrafana(@PathVariable Long id) {
    repository.deleteById(id);
  }
}