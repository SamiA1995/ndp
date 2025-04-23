package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.checkerframework.checker.units.qual.g;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ConfigMap;
import io.kubernetes.client.openapi.models.V1ConfigMapEnvSource;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ContainerPort;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentSpec;
import io.kubernetes.client.openapi.models.V1EnvFromSource;
import io.kubernetes.client.openapi.models.V1LabelSelector;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1PodSpec;
import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServicePort;
import io.kubernetes.client.openapi.models.V1ServiceSpec;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Yaml;

@CrossOrigin(origins = "*")
@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public static String createGrafana(String dashboardName, int sampleDashboards, int consoles) {
		// GET EXAMPLE
		// RestClient restClient = RestClient.create();
		// String result = restClient.get() 
		// 	.uri("http://admin:admin@localhost:52130/api/orgs")
		// 	.header("Authorization", "Basic YWRtaW46YWRtaW4=")
		// 	.retrieve() 
		// 	.body(String.class); 
		// System.out.println(result);

		String port = "50863";
		String body = "{\"name\":\"apiorg\"}";
		RestClient restClient = RestClient.create();
		RestClient.ResponseSpec response = restClient.post() 
			.uri("http://admin:admin@localhost:" + port + "/api/orgs") 
			.header("Authorization", "Basic YWRtaW46YWRtaW4=")
			.contentType(APPLICATION_JSON) 
			.body(body) 
			.retrieve();
		System.out.println(response.body(String.class));

		response = restClient.post() 
			.uri("http://admin:admin@localhost:" + port + "/api/user/using/2") 
			.header("Authorization", "Basic YWRtaW46YWRtaW4=")
			.retrieve();
		System.out.println(response.body(String.class));

		body = "{\"name\":\"test\", \"role\": \"Admin\"}";
		response = restClient.post() 
			.uri("http://admin:admin@localhost:" + port + "/api/serviceaccounts") 
			.header("Authorization", "Basic YWRtaW46YWRtaW4=")
			.contentType(APPLICATION_JSON) 
			.body(body) 
			.retrieve();
		System.out.println(response.body(String.class));

		body = "{\"name\":\"test-token\"}";
		response = restClient.post() 
			.uri("http://admin:admin@localhost:" + port + "/api/serviceaccounts/2/tokens") 
			.header("Authorization", "Basic YWRtaW46YWRtaW4=")
			.contentType(APPLICATION_JSON) 
			.body(body) 
			.retrieve();
		String bearer = response.body(String.class);
		System.out.println(bearer);
        String[] bearerA = bearer.split(",");
        bearerA = bearerA[2].split("\"");
		String bearerToken = bearerA[3];
        System.out.println(bearerToken);

		for(int i = 0; i < sampleDashboards; i++) {
			String dashboardNameFinal = dashboardName + "_" + i;
			body = "{\r\n" + //
						"  \"dashboard\": {\r\n" + //
						"    \"id\": null,\r\n" + //
						"    \"title\":\"" + dashboardNameFinal + "\",\r\n" + //
						"    \"tags\": [ \"templated\" ],\r\n" + //
						"    \"timezone\": \"browser\",\r\n" + //
						"    \"rows\": [\r\n" + //
						"      {\r\n" + //
						"      }\r\n" + //
						"    ],\r\n" + //
						"    \"schemaVersion\": 6,\r\n" + //
						"    \"version\": 0\r\n" + //
						"  },\r\n" + //
						"  \"overwrite\": false\r\n" + //
						"}";
			response = restClient.post() 
				.uri("http://localhost:" + port + "/api/dashboards/db") 
				.header("Authorization", "Bearer " +  bearerToken)
				.contentType(APPLICATION_JSON) 
				.body(body) 
				.retrieve();
			System.out.println(response.body(String.class));
		}

		String grafanaLink = "";
		for(int i = 1; i < sampleDashboards+1; i++) {
			body = "{\r\n" + //
						"  \"dashboard\": {\r\n" + //
						"    \"id\": 1,\r\n" + //
						"    \"id\": " + i + ",\r\n" + //
						"\t\"panels\": [\r\n" + //
						"\t\t{\r\n" + //
						"\t\t  \"annotations\": {\r\n" + //
						"\t\t\t\"list\": [\r\n" + //
						"\t\t\t  {\r\n" + //
						"\t\t\t\t\"builtIn\": 1,\r\n" + //
						"\t\t\t\t\"datasource\": {\r\n" + //
						"\t\t\t\t  \"type\": \"grafana\",\r\n" + //
						"\t\t\t\t  \"uid\": \"-- Grafana --\"\r\n" + //
						"\t\t\t\t},\r\n" + //
						"\t\t\t\t\"enable\": true,\r\n" + //
						"\t\t\t\t\"hide\": true,\r\n" + //
						"\t\t\t\t\"iconColor\": \"rgba(0, 211, 255, 1)\",\r\n" + //
						"\t\t\t\t\"name\": \"Annotations & Alerts\",\r\n" + //
						"\t\t\t\t\"type\": \"dashboard\"\r\n" + //
						"\t\t\t  }\r\n" + //
						"\t\t\t]\r\n" + //
						"\t\t  },\r\n" + //
						"\t\t  \"editable\": true,\r\n" + //
						"\t\t  \"fiscalYearStartMonth\": 0,\r\n" + //
						"\t\t  \"graphTooltip\": 0,\r\n" + //
						"\t\t  \"id\": 10,\r\n" + //
						"\t\t  \"links\": [],\r\n" + //
						"\t\t  \"panels\": [],\r\n" + //
						"\t\t  \"preload\": false,\r\n" + //
						"\t\t  \"schemaVersion\": 40,\r\n" + //
						"\t\t  \"tags\": [],\r\n" + //
						"\t\t  \"templating\": {\r\n" + //
						"\t\t\t\"list\": []\r\n" + //
						"\t\t  },\r\n" + //
						"\t\t  \"time\": {\r\n" + //
						"\t\t\t\"from\": \"now-6h\",\r\n" + //
						"\t\t\t\"to\": \"now\"\r\n" + //
						"\t\t  },\r\n" + //
						"\t\t  \"timepicker\": {},\r\n" + //
						"\t\t  \"timezone\": \"browser\",\r\n" + //
						"\t\t  \"title\":\"" + dashboardName + "\",\r\n" + //
						"\t\t  \"version\": 2,\r\n" + //
						"\t\t  \"weekStart\": \"\"\r\n" + //
						"\t\t}\r\n" + //
						"\t],\r\n" + //
						"    \"title\":\"" + dashboardName + "\",\r\n" + //
						"    \"tags\": [ \"templated\" ],\r\n" + //
						"    \"timezone\": \"browser\",\r\n" + //
						"    \"schemaVersion\": 6,\r\n" + //
						"    \"version\": 1,\r\n" + //
						"    \"refresh\": \"25s\"\r\n" + //
						"  },\r\n" + //
						"  \"folderUid\": \"\",\r\n" + //
						"  \"message\": \"Made changes to test dashboard\",\r\n" + //
						"  \"overwrite\": false\r\n" + //
						"}";
			response = restClient.post() 
				.uri("http://@localhost:" + port + "/api/dashboards/db") 
				.header("Authorization", "Bearer " +  bearerToken)
				.contentType(APPLICATION_JSON) 
				.body(body) 
				.retrieve();

			String uid = response.body(String.class);
			String[] uidA = uid.split(",", 6);
			System.out.println(uidA[4]);
			uidA = uidA[4].split("\"", 3);
			System.out.println(uidA[2]);
			uidA = uidA[2].split("\"", 2);
			System.out.println(uidA[1]);
			uidA = uidA[1].split("\"", 2);
			System.out.println(uidA[0]);
			grafanaLink += "http://localhost:" + port + "/d-solo/" + uidA[0] + "/" + dashboardName + "?orgId=2&from=1745245982698&to=1745267582698&timezone=browser&refresh=25s&panelId=10&__feature.dashboardSceneSolo";
			grafanaLink += ",";
			System.out.println(grafanaLink);
		}
		grafanaLink += port;
		return grafanaLink;
	}

    public static String createDeployment(int replicas) throws IOException, ApiException {
		Map<String, String> label = new HashMap<String, String>();
		List<V1Container> containerList = new ArrayList<V1Container>();
		List<V1ContainerPort> containerPortList = new ArrayList<V1ContainerPort>();
		List<V1ServicePort> servicePortList = new ArrayList<V1ServicePort>();
		List<V1EnvFromSource> envFromSourceList = new ArrayList<V1EnvFromSource>();
		Map<String, String> configMapData = new HashMap<String, String>();

		V1Container container = new V1Container();
		V1ContainerPort containerPort = new V1ContainerPort();
		V1ServicePort servicePort = new V1ServicePort();
		V1EnvFromSource envFromSource = new V1EnvFromSource();
		V1ConfigMapEnvSource configMapEnvSource = new V1ConfigMapEnvSource();
		String deploymentName = "grafana-deployment";

		label.put("app", "spring");
		container.setName("spring");
		container.setImage("grafana/grafana");
		containerPort.setContainerPort(3000);
		containerPortList.add(containerPort);
		container.setPorts(containerPortList);
		configMapEnvSource.name("myconfigmap");
		envFromSource.configMapRef(configMapEnvSource);
		envFromSourceList.add(envFromSource);
		container.envFrom(envFromSourceList);
		configMapData.put("GF_AUTH_ANONYMOUS_ENABLED", "true");
		configMapData.put("GF_AUTH_ANONYMOUS_ORG_NAME", "apiorg");
		configMapData.put("GF_SECURITY_ALLOW_EMBEDDING", "true");
		containerList.add(container);
		servicePort.protocol("TCP");
		servicePort.port(3000);
		servicePortList.add(servicePort);

		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);

		V1ConfigMap configMap = new V1ConfigMap()
			.apiVersion("v1")
			.kind("ConfigMap")
			.metadata(new V1ObjectMeta()
				.name("myconfigmap"))
			.data(configMapData);
		System.out.println(Yaml.dump(configMap));

		CoreV1Api coreApi = new CoreV1Api();
		V1ConfigMap createResult3 = coreApi.createNamespacedConfigMap("default", configMap).execute();
		System.out.println(createResult3);

		V1Deployment deployment = new V1Deployment()
			.apiVersion("apps/v1")
			.kind("Deployment")
			.metadata(new V1ObjectMeta()
				.name(deploymentName)
				.labels(label))
			.spec(new V1DeploymentSpec()
				.replicas(replicas)
				.selector(new V1LabelSelector()
					.matchLabels(label))
				.template(new V1PodTemplateSpec()
					.metadata(new V1ObjectMeta()
						.labels(label))
					.spec(new V1PodSpec()
						.containers(containerList)))
						
				);
		System.out.println(Yaml.dump(deployment));

		// Deployment and StatefulSet is defined in apps/v1, so you should use AppsV1Api instead of
		// CoreV1API
		AppsV1Api api = new AppsV1Api();
		V1Deployment createResult = api.createNamespacedDeployment("default", deployment).execute();
		System.out.println(createResult);

		V1Service svc = new V1Service()
		.apiVersion("v1")
		.kind("Service")
		.metadata(new V1ObjectMeta()
			.name("my-service"))
		.spec(new V1ServiceSpec()
			.type("NodePort")
			.selector(label)
			.ports(servicePortList)
		);
		System.out.println(Yaml.dump(svc));

		V1Service createResult2 = coreApi.createNamespacedService("default", svc).execute();
		System.out.println(createResult2);

		return createResult + "\n" + "\n" + createResult2 + "\n" + "\n" + createResult3;
    }
}
