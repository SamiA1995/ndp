package com.example.demo;

class GrafanaNotFoundException extends RuntimeException {

    GrafanaNotFoundException(Long id) {
      super("Could not find grafana " + id);
    }
  }