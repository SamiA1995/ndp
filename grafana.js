function grafanaOptions(e) {
    if(grafanaButton.checked) {
        var menuBoxDiv = document.createElement('div');
        menuBoxDiv.setAttribute("id", "menuBox");

        var menuHeaderDiv = document.createElement('div');
        menuHeaderDiv.setAttribute("id", "menuHeader");
        var newText = document.createTextNode("Grafana options:");
        menuHeaderDiv.appendChild(newText);
        menuBoxDiv.appendChild(menuHeaderDiv);

        var menuChoicesPDiv = document.createElement('div');
        menuChoicesPDiv.setAttribute("id", "menuChoicesP");

        var paragraph = document.createElement('p'); 
        var strong = document.createElement('strong'); 
        newText = document.createTextNode("Replicas");
        strong.appendChild(newText);
        paragraph.appendChild(strong);
        menuChoicesPDiv.appendChild(paragraph);
        
        paragraph = document.createElement('p'); 
        var em = document.createElement('em'); 
        newText = document.createTextNode("Number of replicas to be created for this deployment.");
        em.appendChild(newText);
        paragraph.appendChild(em);
        menuChoicesPDiv.appendChild(paragraph);

        input = document.createElement('input'); 
        input.setAttribute("type", "text");
        input.setAttribute("id", "replicas");
        input.setAttribute("name", "replicas");
        menuChoicesPDiv.appendChild(input);
        br = document.createElement('br'); 
        menuChoicesPDiv.appendChild(br);

        paragraph = document.createElement('p'); 
        strong = document.createElement('strong'); 
        newText = document.createTextNode("Sample dashboards");
        strong.appendChild(newText);
        paragraph.appendChild(strong);
        menuChoicesPDiv.appendChild(paragraph);
        
        paragraph = document.createElement('p'); 
        em = document.createElement('em'); 
        newText = document.createTextNode("Number of sample dashboards to be created. Sample dashboards will contain random data provided from Grafana.");
        em.appendChild(newText);
        paragraph.appendChild(em);
        menuChoicesPDiv.appendChild(paragraph);

        input = document.createElement('input'); 
        input.setAttribute("type", "text");
        input.setAttribute("id", "sampleDashboards");
        input.setAttribute("name", "sampleDashboards");
        menuChoicesPDiv.appendChild(input);
        br = document.createElement('br'); 
        menuChoicesPDiv.appendChild(br);

        paragraph = document.createElement('p'); 
        strong = document.createElement('strong'); 
        newText = document.createTextNode("Dashboard name");
        strong.appendChild(newText);
        paragraph.appendChild(strong);
        menuChoicesPDiv.appendChild(paragraph);
        
        paragraph = document.createElement('p'); 
        em = document.createElement('em'); 
        newText = document.createTextNode("The name of the dashboard(s).");
        em.appendChild(newText);
        paragraph.appendChild(em);
        menuChoicesPDiv.appendChild(paragraph);

        input = document.createElement('input'); 
        input.setAttribute("type", "text");
        input.setAttribute("id", "dashboardName");
        input.setAttribute("name", "dashboardName");
        menuChoicesPDiv.appendChild(input);
        br = document.createElement('br'); 
        menuChoicesPDiv.appendChild(br);

        paragraph = document.createElement('p'); 
        strong = document.createElement('strong'); 
        newText = document.createTextNode("Consoles");
        strong.appendChild(newText);
        paragraph.appendChild(strong);
        menuChoicesPDiv.appendChild(paragraph);
        
        paragraph = document.createElement('p'); 
        em = document.createElement('em'); 
        newText = document.createTextNode("A default Grafana deployment.");
        em.appendChild(newText);
        paragraph.appendChild(em);
        menuChoicesPDiv.appendChild(paragraph);

        input = document.createElement('input'); 
        input.setAttribute("type", "text");
        input.setAttribute("id", "consoles");
        input.setAttribute("name", "consoles3");
        menuChoicesPDiv.appendChild(input);
        br = document.createElement('br'); 
        menuChoicesPDiv.appendChild(br);

        menuBoxDiv.appendChild(menuChoicesPDiv);
        mainForm.insertBefore(menuBoxDiv, mainForm.lastElementChild);
    } else {
        var grafanaOptionsElement = document.getElementById("mainForm").lastElementChild.previousSibling;
        mainForm.removeChild(grafanaOptionsElement);
    }
}

function createK8sDeployments(e) {
    e.preventDefault();
    var deployments = document.getElementById('replicas').value;
    var sampleDashboards = document.getElementById('sampleDashboards').value;
    var dashboardName = document.getElementById('dashboardName').value;
    var consoles = document.getElementById('consoles').value;
    var grafanaJson = '{"replicas":' + deployments + ', "sampleDashboards":' + sampleDashboards + ', "dashboardName":"' + dashboardName + '", "consoles":' + consoles + '}';
    console.log(deployments);
    console.log(sampleDashboards);
    console.log(dashboardName);
    console.log(consoles);
    console.log(grafanaJson);

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/k8sDeployment',
        data: grafanaJson,
        success: function(data) { createK8sDeploymentsOutput(data); },
        contentType: "application/json",
        dataType: 'json'
    });
}

function createGrafanaDeployments(e) {
    e.preventDefault();
    var deployments = document.getElementById('replicas').value;
    var sampleDashboards = document.getElementById('sampleDashboards').value;
    var dashboardName = document.getElementById('dashboardName').value;
    var consoles = document.getElementById('consoles').value;
    var grafanaJson = '{"replicas":' + deployments + ', "sampleDashboards":' + sampleDashboards + ', "dashboardName":"' + dashboardName + '", "consoles":' + consoles + '}';
    console.log(deployments);
    console.log(sampleDashboards);
    console.log(dashboardName);
    console.log(consoles);
    console.log(grafanaJson);

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/grafanaDeployment',
        data: grafanaJson,
        success: function(data) { createGrafanaDeploymentsOutput(data); },
        contentType: "application/json",
        dataType: 'json'
    });
}

function createK8sDeploymentsOutput(data) {
    console.log(data.dashboardName);

    var menuBoxDiv = document.createElement('div');
    menuBoxDiv.setAttribute("id", "menuBox");
    var menuHeaderDiv = document.createElement('div');
    menuHeaderDiv.setAttribute("id", "menuHeader");
    var newText = document.createTextNode("Manifest files:");
    menuHeaderDiv.appendChild(newText);
    
    var yamlOutput = document.createElement('p');
    yamlOutput.setAttribute("id", "monospace");
    var yamlOutputP = document.createTextNode(data.dashboardName);
    yamlOutput.appendChild(yamlOutputP);

    menuBoxDiv.appendChild(menuHeaderDiv);
    menuBoxDiv.appendChild(yamlOutput);
    iframes.appendChild(menuBoxDiv);
}

function createGrafanaDeploymentsOutput(data) {
    linksArray = data.dashboardName.split(",");

    for(var i = 0; i < linksArray.length-1; i++) {
        var iframe = document.createElement('iframe');
        iframe.setAttribute("src", linksArray[i]);
        iframes.insertBefore(iframe, iframes.lastElementChild);
    }

    for(var i = 0; i < data.consoles; i++) {
        var iframe = document.createElement('iframe');
        iframe.setAttribute("src", "http://localhost:" + linksArray[linksArray.length-1]);
        iframe.setAttribute("id", "grafanaConsoles");
        iframes.insertBefore(iframe, iframes.lastElementChild);
    }
}

var mainForm = document.getElementById('mainForm');
var iframes = document.getElementById('iframes');

var grafanaButton = document.getElementById('grafana');

grafanaButton.addEventListener('click', function(e) {
    grafanaOptions(e);
}, false);

var k8sSubmit = document.getElementById('k8sSubmit');

k8sSubmit.addEventListener('click', function(e) {
    createK8sDeployments(e);
}, false);

var grafanaSubmit = document.getElementById('grafanaSubmit');

grafanaSubmit.addEventListener('click', function(e) {
    createGrafanaDeployments(e);
}, false);