function underline(e, state, underlineColor) {
    var item = e.target;
    if(underlineColor == "white") {
        if(state) {
            item.setAttribute("style", "border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: white;");
            cursorPointer(true);
        } else {
            item.removeAttribute("style", "border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: white;");
            cursorPointer(false);
        }
    } else {
        if(state) {
            item.setAttribute("style", "font-weight: 600;");
            cursorPointer(true);
        } else {
            item.removeAttribute("style", "font-weight: 600;");
            cursorPointer(false);
        }
    }
    
}

function cursorPointer(state) {
    if(state) {
        document.body.style.cursor = "pointer";
    } else {
        document.body.style.cursor = "default";
    }
}

var navbarItems = document.getElementsByClassName('navbarItem');

navbarItems[0].addEventListener('mouseover', function(e) {
    cursorPointer(true, "white")
}, false);

navbarItems[0].addEventListener('mouseout', function(e) {
    cursorPointer(false, "white")
}, false);

for(var i = 1; i < navbarItems.length; i++) {
    navbarItems[i].addEventListener('mouseover', function(e) {
        underline(e, true, "white");
    }, false);

    navbarItems[i].addEventListener('mouseout', function(e) {
        underline(e, false, "white");
    }, false);
}

var siteMenuItems = document.getElementsByClassName('siteMenuItem');

for(var i = 0; i < siteMenuItems.length; i++) {
    siteMenuItems[i].addEventListener('mouseover', function(e) {
        underline(e, true, "highlight");
    }, false);

    siteMenuItems[i].addEventListener('mouseout', function(e) {
        underline(e, false, "highlight");
    }, false);
}