$(function() {
    if (window.location == window.parent.location) {
        //direct invocation, redirect to container
        window.location.replace(indexUrl + '#' + helpId);
    } else {
        window.parent.postMessage({action: "select", helpId: helpId}, '*');
    }

    $(document).keydown(function(event) {
        var keycode = event.which;
        switch (keycode) {
            case 115: //F4
                window.parent.postMessage({action: "focus"}, '*');
                event.preventDefault();
                break;
            case 74:  //j
                nav("left");
                break;
            case 75:  //k
                nav("right");
                break;
            case 85:  //u
                nav("up");
                break;
            case 68:  //d
                nav("down");
                break;
        }
    });

    function nav(id) {
        $('.nav-' + id).first().each(function() {this.click();})
    }

    var hash = location.hash;
    if (hash) {
        document.documentElement.scrollTop = document.body.scrollTop = parseInt(hash.substr(1));
    }
});

function showEnlarged(element) {
    var maxWidth = parseInt(element.style.maxWidth, 10);
    if (maxWidth != element.clientWidth) {
        window.parent.postMessage({action: "showImage", helpId: helpId, src: element.src, scrollTop: document.documentElement.scrollTop || document.body.scrollTop}, '*');
    }
}

function initSvg(element, width, height) {
    if(navigator.userAgent.indexOf('MSIE') !== -1  || navigator.appVersion.indexOf('Trident/') > 0) {
        element.setAttribute("width", width);
        element.setAttribute("height", height);
    }
}
