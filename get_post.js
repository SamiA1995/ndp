$.get( "http://localhost:8080/employees", function( data ) {
    $( "body" )
        .append( "Name: " + data[2].name )
        .append( "Time: " + data[2].role );
}, "json" );

$.ajax({
    type: 'POST',
    url: 'http://localhost:8080/employees',
    data: '{"name":"Gandalf", "role":"Wizard"}',
    success: function(data) { alert('data: ' + data); },
    contentType: "application/json",
    dataType: 'json'
});