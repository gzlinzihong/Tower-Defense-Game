
$(function () {

    var inSync = false;
    var nextPosition = null;
    var ignoreNextSync = false;
    var firstMessage = true;

    var url = window.location.href;
    var base = url.substring(0, url.lastIndexOf('/') + 1);

    var menuElement = $("#menu");

    var $tree = $('#tree');
    $tree.tree({
        data: treeData,
        autoEscape: false,
        closedIcon: '&#xe000;',
        openedIcon: '&#xe001;',
        onCreateLi: function (node, $li) {
            if (node.children.length == 0 && node.getLevel() > 1) {
                var iconUrl = node.imageUrl ? node.imageUrl : 'images/help_topic_18@2x.png';
                $li.addClass("tree-icon");
                $li.css({
                    'background': 'url(' + iconUrl + ') no-repeat'
                });
            }
        }
    });

    $tree.bind('tree.select',
        function (event) {
            if (event.node) {
                var node = event.node;
                var contentElement = window.parent.document.getElementById('content');
                if (contentElement.src == 'about:blank' || !inSync) {
                    contentElement.src = "about:blank"; 
                    contentElement.src = base + node.href + (nextPosition ? '#' + nextPosition : '');
                    ignoreNextSync = true;
                }
                menuElement.addClass('hidden-small');
            }
        }
    );

    $tree.bind('tree.click',
        function(event) {
            if ($tree.tree('isNodeSelected', event.node)) {
                event.preventDefault();
                menuElement.addClass('hidden-small');
            }
        }
    );

    $(document).keydown(function(event) {
        if (event.which == 115) { // F4
            document.getElementById('content').contentWindow.focus();
            event.preventDefault();
        }
    });

    $(function () {
        var hash = location.hash;
        var helpId;
        var position = null;
        var load = true;
        if (hash) {
            var hashParts = hash.substr(1).split("&");
            helpId = hashParts[0];
            if (hashParts.length > 1) {
                position = parseInt(hashParts[1]);
                if (history) {
                    // remove position from history
                    history.replaceState(null, document.title, '#' + helpId);
                }
            }
            ignoreNextSync = true;
        } else {
            helpId = treeData[0].id;
            load = false;
        }
        syncMenu(helpId, load, position);
    });

    $(window).on('message', function(event) {
        var data = event.originalEvent.data;
        if (data.action == 'focus') {
            $('#content').blur();
            window.focus();
            $tree.focus();
        } else if (data.action == 'select') {
            var menuNode;
            if (!ignoreNextSync) {
                menuNode = syncMenu(data.helpId, false)
            } else {
                menuNode = getMenuNode(data.helpId)
            }
            if (menuNode) {
                document.title = baseTitle + " - " + menuNode.name;
            }
            ignoreNextSync = false;
            if (history) {
                if (firstMessage) { // do not replace initial url without hash
                    firstMessage = false;
                } else {
                    history.replaceState(null, document.title, '#' + data.helpId);
                }
            }
        } else if (data.action == 'showImage') {
            if (history) {
                // replace history again to save scroll position
                history.replaceState(null, document.title, '#' + data.helpId + '&' + data.scrollTop);
                window.location = data.src;
            }

        }
    });

    $tree.focus();

    var getMenuNode = function (helpId) {
        return $tree.tree('getNodeById', helpId);
    };

    function syncMenu(helpId, load, position) {
        inSync = !load;
        nextPosition = position;
        try {
            var node = getMenuNode(helpId);
            var currentSelection = $tree.tree('getSelectedNode');
            if (node != currentSelection) {
                $.each($tree.tree('getState').open_nodes, function(index, value) {
                    $tree.tree('closeNode', $tree.tree('getNodeById', value), false)
                });

                $tree.tree('selectNode', node);
                $tree.tree('scrollToNode', node);
            }
            return node;
        } finally {
            inSync = false;
            nextPosition = null;
        }
    }

    $('#menu-button').on("click", function() {
        menuElement.toggleClass('hidden-small');
    });

    var iOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
    if (!iOS) {
        $('.ios-scroll').removeClass("ios-scroll");
    }
});
