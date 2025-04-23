package com.example.demo;

public class UID {
    public static void main(String[] args) {
        String uid = "{\"id\": 1,\"name\": \"test-token\",\"key\": \"glsa_CXj73SPWraz7no2soxu06JwDkwJU9YfE_ee3e174f\"}";
        System.out.println(uid);
        String[] uidA = uid.split(",");
        System.out.println(uidA[2]);
        uidA = uidA[2].split("\"");
        System.out.println(uidA[3]);
        // uidA = uidA[2].split("\"", 2);
        // System.out.println(uidA[1]);
        // uidA = uidA[1].split("\"", 2);
        // System.out.println(uidA[0]);
        // String grafanaLink =  "http://localhost:64148/d-solo/" + uidA[0] + "/ndp-2-0-dashboard?orgId=2&from=1745245982698&to=1745267582698&timezone=browser&refresh=25s&panelId=10&__feature.dashboardSceneSolo";
        // System.out.println(grafanaLink);
    }
}
